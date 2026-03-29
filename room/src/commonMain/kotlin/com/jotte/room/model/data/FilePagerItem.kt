package com.jotte.room.model.data

import com.jotte.cxui.controller.PagerItem
import com.jotte.data.persistence.data.MediaDto
import com.jotte.editor.model.state.asPlatformFile
import io.github.vinceglb.filekit.path

class FilePagerItem(val file: MediaDto) : PagerItem {
    override val id: String = file.mediaId
    override val path: String = file.asPlatformFile().path
}