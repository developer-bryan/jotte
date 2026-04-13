package com.jotte.editor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jotte.core.storageFile
import com.jotte.cxui.soundeffect.SoundEffect
import com.jotte.cxui.soundeffect.SoundEffectsPlayer
import com.jotte.data.persistence.data.FullNote
import com.jotte.data.usecase.GetNoteUseCase
import com.jotte.editor.model.event.EditorEvent
import com.jotte.editor.model.state.DraftAudioState
import com.jotte.editor.model.state.DraftLinkState
import com.jotte.editor.model.state.DraftState
import com.jotte.editor.usecase.CreateNoteUseCase
import com.jotte.editor.usecase.UpdateNoteUseCase
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlin.jvm.JvmInline

@JvmInline
internal value class RoomId(val value: Long)

@JvmInline
internal value class NoteId(val value: Long?)

internal class EditorViewModel(
    private val getNoteUseCase: GetNoteUseCase,
    private val createNoteUseCase: CreateNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val soundEffectsPlayer: SoundEffectsPlayer,
    private val roomId: RoomId,
    private val noteId: NoteId
) : ViewModel() {

    val event = Channel<EditorEvent>(onBufferOverflow = BufferOverflow.SUSPEND)

    // Data Snapshots for existing notes
    private val _snapshot = MutableStateFlow<FullNote?>(null)
    val snapshot: Flow<FullNote?> = _snapshot

    private val linksSnapshot = HashSet<String>()

    // Value deltas
    private val audio = MutableStateFlow<DraftAudioState?>(null)
    private val attachments = MutableStateFlow<ArrayList<PlatformFile>>(ArrayList())
    private val links = MutableStateFlow<ArrayList<DraftLinkState>>(ArrayList())
    private val contentValue = MutableStateFlow("")

    val draft =
        combine(
            flow = audio,
            flow2 = attachments,
            flow3 = _snapshot,
            flow4 = contentValue,
            flow5 = links,
            transform = { audio, attachments, note, contentValue, links ->

                val canSubmit =
                    if (note == null) {
                        contentValue != "" || audio != null || attachments.isNotEmpty() || links.isNotEmpty()
                    } else {
                        note.note.content?.value != contentValue ||
                            note.note.audio?.fileName != audio?.file?.name ||
                            note.note.audio?.duration != audio?.duration ||
                            note.note.audio?.title != audio?.title ||
                            attachments.any { attachment ->
                                note.media?.none { it.fileName == attachment.name } == true
                            } ||
                            links.any { !linksSnapshot.contains(it.toString()) } ||
                            links.size != note.links?.size
                    }

                DraftState(
                    roomId = roomId.value,
                    noteId = noteId.value,
                    canSubmit = canSubmit,
                    audio = audio,
                    media = attachments,
                    links = links
                )
            }
        )

    init {
        noteId.value?.let { id ->
            viewModelScope.launch {
                val note = getNoteUseCase(id)
                val links = ArrayList<DraftLinkState>()
                val content = note.note.content
                val audio =
                    note.note.audio?.let {
                        DraftAudioState(
                            file = FileKit.storageFile(it.fileName),
                            duration = it.duration,
                            title = it.title
                        )
                    }
                val attachments = note.media?.map { FileKit.storageFile(it.fileName) }
                note.links?.forEach {
                    val link =
                        DraftLinkState(
                            id = it.linkId,
                            link = it.link,
                            type = it.linkType
                        )
                    links.add(link)
                    linksSnapshot.add(link.toString())
                }

                this@EditorViewModel._snapshot.emit(note)
                this@EditorViewModel.contentValue.emit(content?.value ?: "")
                this@EditorViewModel.audio.emit(audio)
                this@EditorViewModel.attachments.emit(attachments as ArrayList)
                this@EditorViewModel.links.emit(links)
            }
        }
    }

    fun removeAttachment(file: PlatformFile) =
        viewModelScope.launch {
            val currentAttachments = attachments.value
            val modifiedAttachments = currentAttachments.filter { it.name != file.name }

            attachments.emit(ArrayList(modifiedAttachments))
            soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
        }

    fun removeAudio() = viewModelScope.launch { audio.emit(null) }

    // TODO: Check if we can remove?
    fun clearDraft() =
        viewModelScope.launch {
            _snapshot.emit(null)
            contentValue.emit("")
            audio.emit(null)
            attachments.emit(ArrayList())
            linksSnapshot.clear()
        }

    fun setContentValue(value: String) {
        viewModelScope.launch {
            contentValue.emit(value)
        }
    }

    fun addAttachment(file: PlatformFile) =
        viewModelScope.launch {
            val newAttachmentsList = attachments.value + file
            attachments.emit(newAttachmentsList as ArrayList)
        }

    fun addAudioFile(
        file: PlatformFile,
        duration: Long
    ) = viewModelScope.launch {
        val newAudio =
            audio.value?.copy(
                file = file,
                duration = duration
            ) ?: DraftAudioState(file, duration)
        audio.emit(newAudio)
    }

    fun setAudioTitle(title: String) =
        viewModelScope.launch {
            val newAudio = audio.value?.copy(title = title)
            audio.emit(newAudio)
        }

    fun addLink(link: DraftLinkState) =
        viewModelScope.launch {
            val newLinksList = links.value + link
            links.emit(newLinksList as ArrayList)
        }

    fun removeLink(link: DraftLinkState) =
        viewModelScope.launch {
            val newLinksList = links.value - link
            links.emit(newLinksList as ArrayList)
        }

    fun submit() =
        viewModelScope.launch(
            context = CoroutineExceptionHandler { _, error -> println(error) },
            block = {
                val draft = draft.first()

                if (draft.noteId == null) {
                    createNoteUseCase(draft, contentValue.value)
                } else {
                    updateNoteUseCase(draft, contentValue.value)
                }

                event.send(EditorEvent.OnDraftSubmitted)
                soundEffectsPlayer.playSound(SoundEffect.SoundEffectCreation)
                clearDraft()
            }
        )
}