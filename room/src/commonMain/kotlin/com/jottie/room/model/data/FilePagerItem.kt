package com.jottie.room.model.data

import com.jottie.cxui.controller.PagerItem
import com.jottie.editor.model.state.asPlatformFile
import com.jottie.message.data.MediaDto
import io.github.vinceglb.filekit.path

class FilePagerItem(val file: MediaDto): PagerItem {

    override val id: String = file.mediaId
    override val path: String = file.asPlatformFile().path
}