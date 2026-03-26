package com.jotte.message.usecase

import com.jotte.message.data.FullNote

/**
 * Given our table follows SIT architecture,
 * we must ensure data integrity when removing components of a note.
 *
 * The goal is to ensure we're not orphaning a note by removing its only piece of data
 * but preserving the record itself.
 *
 * For example,
 * a note with content only. (null or empty attachments and null audio).
 * If we delete the content under this scenario we must delete the entire note.
 */
class CheckShouldDeleteNoteUseCase {

    fun shouldDeleteOnContentRemoval(note: FullNote): Boolean = note.media.isNullOrEmpty() && note.note.audio == null

    fun shouldDeleteOnMediaRemoval(
        note: FullNote,
        fileId: String
    ): Boolean =
        note.note.content == null &&
            with(note.media) {
                this == null || (this.size == 1 && fileId == this.first().mediaId)
            }

    fun shouldDeleteOnAudioRemoval(note: FullNote): Boolean = note.note.content == null && note.media.isNullOrEmpty()

}