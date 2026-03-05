package com.jottie.editor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jottie.core.VirtualFile
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.cxui.soundeffect.SoundEffectsPlayer
import com.jottie.editor.model.event.EditorEvent
import com.jottie.editor.model.state.DraftAudioState
import com.jottie.editor.model.state.DraftContentState
import com.jottie.editor.model.state.DraftLinkState
import com.jottie.editor.model.state.DraftState
import com.jottie.editor.usecase.CreateNoteUseCase
import com.jottie.editor.usecase.UpdateNoteUseCase
import com.jottie.message.data.FullNote
import com.jottie.message.usecase.GetNoteUseCase
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

    private val linksSnapshot = HashSet<String>()

    private val snapshot = MutableStateFlow<FullNote?>(null)
    private val audio = MutableStateFlow<DraftAudioState?>(null)
    private val attachments = MutableStateFlow<ArrayList<VirtualFile>>(ArrayList())
    private val links = MutableStateFlow<ArrayList<DraftLinkState>>(ArrayList())

    private val mutableContentValue = MutableStateFlow("")
    val contentValue: Flow<String> = mutableContentValue

    val draft = combine(
        flow = audio,
        flow2 = attachments,
        flow3 = snapshot,
        flow4 = contentValue,
        flow5 = links,
        transform = { audio, attachments, note, contentValue, links ->

            val canSubmit = if (note == null) {
                contentValue != "" || audio != null || attachments.isNotEmpty() || links.isNotEmpty()
            } else {
                note.note.content?.value != contentValue ||
                        note.note.audio?.fileName != audio?.file?.fileName ||
                        note.note.audio?.duration != audio?.duration ||
                        note.note.audio?.title != audio?.title ||
                        attachments.any { attachment -> note.media?.none { it.fileName == attachment.fileName } == true } ||
                        links.any { !linksSnapshot.contains(it.toString()) } ||
                        links.size != note.links?.size
            }

            val draftContent = contentValue
                .takeIf { it.isNotEmpty() }
                ?.let { DraftContentState(value = it) }

            DraftState(
                roomId = roomId.value,
                noteId = noteId.value,
                isExistingNote = note != null,
                canSubmit = canSubmit,
                content = draftContent,
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
                val content = note.note.content?.let { DraftContentState(it.value) }
                val audio = note.note.audio?.let {
                    DraftAudioState(
                        file = VirtualFile(it.fileName, false),
                        duration = it.duration,
                        title = it.title
                    )
                }
                val attachments = note.media?.map { VirtualFile(it.fileName, false) }
                note.links?.forEach {
                    val link = DraftLinkState(
                        id = it.linkId,
                        link = it.link,
                        type = it.linkType
                    )
                    links.add(link)
                    linksSnapshot.add(link.toString())
                }

                this@EditorViewModel.snapshot.emit(note)
                this@EditorViewModel.mutableContentValue.emit(content?.value ?: "")
                this@EditorViewModel.audio.emit(audio)
                this@EditorViewModel.attachments.emit(attachments as ArrayList)
                this@EditorViewModel.links.emit(links)
            }
        }
    }

    fun removeAttachment(file: VirtualFile) = viewModelScope.launch {
        val currentAttachments = attachments.value
        val modifiedAttachments = currentAttachments.filter { it.fileName != file.fileName }

        attachments.emit(ArrayList(modifiedAttachments))
        soundEffectsPlayer.playSound(SoundEffect.SoundEffectRemoval)
    }

    fun removeAudio() = viewModelScope.launch { audio.emit(null) }

    // TODO: Check if we can remove?
    fun clearDraft() = viewModelScope.launch {
        snapshot.emit(null)
        mutableContentValue.emit("")
        audio.emit(null)
        attachments.emit(ArrayList())
        linksSnapshot.clear()
    }

    fun setContentValue(value: String) {
        viewModelScope.launch {
            mutableContentValue.emit(value)
        }
    }

    fun addAttachment(file: VirtualFile) = viewModelScope.launch {
        val newAttachmentsList = attachments.value + file
        attachments.emit(newAttachmentsList as ArrayList)
    }

    fun setAudioFile(file: VirtualFile, duration: Long) = viewModelScope.launch {
        val newAudio = audio.value?.copy(file, duration) ?: DraftAudioState(file, duration)
        audio.emit(newAudio)
    }

    fun setAudioTitle(title: String) = viewModelScope.launch {
        val newAudio = audio.value?.copy(title = title)
        audio.emit(newAudio)
    }

    fun addLink(link: DraftLinkState) = viewModelScope.launch {
        val newLinksList = links.value + link
        links.emit(newLinksList as ArrayList)
    }

    fun removeLink(link: DraftLinkState) = viewModelScope.launch {
        val newLinksList = links.value - link
        links.emit(newLinksList as ArrayList)
    }

    fun submit() = viewModelScope.launch(
        context = CoroutineExceptionHandler { _, error -> println(error) },
        block = {
            val draft = draft.first()

            if (draft.noteId == null) {
                createNoteUseCase(draft)
            } else {
                updateNoteUseCase(draft)
            }

            event.send(EditorEvent.OnDraftSubmitted)
            soundEffectsPlayer.playSound(SoundEffect.SoundEffectCreation)
            clearDraft()
        }
    )
}