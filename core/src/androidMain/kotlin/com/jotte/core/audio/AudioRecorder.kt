@file:Suppress("ConstructorParameterNaming")

package com.jotte.core.audio

import android.media.MediaRecorder
import com.jotte.core.cacheFile
import com.jotte.core.datetime.CoroutineTimer
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.path
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

private const val HZ = 44100

// TODO: Add Metering
actual class AudioRecorder actual constructor(_timer: CoroutineTimer) {

    actual val timer: CoroutineTimer = _timer
    private val _isRecording = MutableStateFlow(false)
    actual val isRecording: Flow<Boolean> = _isRecording

    private var recorder: MediaRecorder? = null
    private var outputFile: PlatformFile? = null

    actual fun beginRecording() {
        val outputDestinationFile = getOutputFile()
        this.outputFile = outputDestinationFile

        if (recorder == null) {
            recorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setAudioSamplingRate(HZ)
                setOnErrorListener { _, _, _ -> cancelRecording() }
            }
        }

        recorder?.setOutputFile(outputDestinationFile.path)
        recorder?.prepare()
        recorder?.start()
        timer.start()
        _isRecording.tryEmit(true)
    }


    actual fun finishRecording(): Result<PlatformFile> {
        return runCatching {
            recorder?.stop()
            recorder?.release()
            timer.stop()
            _isRecording.tryEmit(false)
            val output = outputFile
            checkNotNull(output)
            output
        }
    }

    actual fun cancelRecording() {
        runCatching {
            recorder?.stop()
            recorder?.release()
            timer.stop()
            _isRecording.tryEmit(false)
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun getOutputFile(): PlatformFile {
        val fileName = "${Clock.System.now().toEpochMilliseconds()}.mp4"
        return FileKit.cacheFile(fileName)
    }

}
