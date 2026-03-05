package com.jotte.audioplayer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.audio.AudioPlayer
import com.jotte.core.storageFile
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.message.data.NoteDto
import com.jotte.message.repository.NoteRepository
import com.jotte.message.usecase.DeleteAudioUseCase
import com.jotte.audioplayer.model.state.AudioScreenState
import io.github.vinceglb.filekit.FileKit
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal class AudioNoteViewModel(
    private val repository: NoteRepository,
    private val deleteAudioUseCase: DeleteAudioUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer
) : ViewModel() {

    val player = AudioPlayer(viewModelScope)

    private val note = MutableStateFlow<NoteDto?>(null)
    private val _state = MutableStateFlow<AudioScreenState>(AudioScreenState.Nothing)
    val state: Flow<AudioScreenState> = _state

    fun loadAudioNote(audioId: String) = viewModelScope.launch(
        context = CoroutineExceptionHandler { _, exception ->
            println(exception)
            _state.tryEmit(AudioScreenState.Error)
        },
        block = {
            val note = repository.queryNoteByAudioId(audioId)
            val audio = note?.audio

            checkNotNull(note)
            checkNotNull(audio)

            val file = FileKit.storageFile(audio.fileName)

            checkNotNull(file)

            player.setupPlayer(file)

            val audioState = AudioScreenState.Success(
                title = audio.title,
                duration = audio.duration,
                file = file
            )

            this@AudioNoteViewModel.note.emit(note)
            _state.emit(audioState)
        }
    )

    fun deleteAudioNote() {
        viewModelScope.launch {
            deleteAudioUseCase(note.value!!.audio!!.audioId)
                .onSuccess { soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval) }
                .onFailure { /* emit event */ }
        }
    }

    fun play() {
        player.play()
    }

    fun pause() {
        player.pause()
    }

    override fun onCleared() {
        super.onCleared()
        player.dispose()
    }

}