@file:OptIn(ExperimentalForeignApi::class, ExperimentalTime::class)

package com.jotte.core.audio

import com.jotte.core.VirtualFile
import com.jotte.core.datetime.CoroutineTimer
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import platform.AVFAudio.AVAudioQuality
import platform.AVFAudio.AVAudioRecorder
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryOptionDefaultToSpeaker
import platform.AVFAudio.AVAudioSessionCategoryPlayAndRecord
import platform.AVFAudio.AVEncoderAudioQualityKey
import platform.AVFAudio.AVFormatIDKey
import platform.AVFAudio.AVNumberOfChannelsKey
import platform.AVFAudio.AVSampleRateKey
import platform.AVFAudio.setActive
import platform.CoreAudioTypes.kAudioFormatMPEG4AAC
import platform.Foundation.NSError
import kotlin.Any
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

actual class AudioRecorder actual constructor(_timer: CoroutineTimer) {

    actual val timer = _timer
    private val _isRecording = MutableStateFlow(false)
    actual val isRecording: Flow<Boolean> = _isRecording

    actual val hasActiveRecording: Flow<Boolean> = combine(
        flow = isRecording,
        flow2 = timer.rawElapsed,
        transform = { isRecording, duration ->
            isRecording && duration > 0
        }
    )

    private var recorder: AVAudioRecorder? = null
    private var destination: VirtualFile? = null

    actual fun beginRecording() {
        val settings = mapOf<Any?, Any>(
            AVFormatIDKey to kAudioFormatMPEG4AAC,
            AVSampleRateKey to 44100,
            AVNumberOfChannelsKey to 1,
            AVEncoderAudioQualityKey to AVAudioQuality.MAX_VALUE
        )

        memScoped {
            val audioSession = AVAudioSession.sharedInstance()
            val categoryErrorPtr = alloc<ObjCObjectVar<NSError?>>()
            val activateErrorPtr = alloc<ObjCObjectVar<NSError?>>()

            audioSession.setCategory(
                category = AVAudioSessionCategoryPlayAndRecord,
                withOptions = AVAudioSessionCategoryOptionDefaultToSpeaker,
                error = categoryErrorPtr.ptr
            )

            audioSession.setActive(true, activateErrorPtr.ptr)
        }

        destination = VirtualFile(getFileName(), true)
        val nsurl = destination!!.asFile().nsUrl
        recorder = AVAudioRecorder(nsurl, settings, null)

        if (recorder?.prepareToRecord() == true && recorder?.record() == true) {
            timer.start()
            _isRecording.tryEmit(true)
        } else {
            // handle failures
        }
    }

    actual fun pauseRecording() {
        recorder?.pause()
        timer.stop()
    }

    actual fun resumeRecording() {
        recorder?.record()
        timer.start()
    }

    actual fun stopRecording(): Result<VirtualFile> {
        return runCatching {
            recorder?.stop()
            val output = destination
            checkNotNull(output)
            output
        }
    }

    private fun getFileName(): String {
        val millis = Clock.System.now().toEpochMilliseconds()
        return "$millis.mp4"
    }

}