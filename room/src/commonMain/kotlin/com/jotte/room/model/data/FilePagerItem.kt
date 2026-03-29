package com.jotte.room.model.data

import com.jotte.core.storageFile
import com.jotte.cxui.controller.PagerItem
import com.jotte.data.persistence.data.MediaDto
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.path

class FilePagerItem(val file: MediaDto) : PagerItem {
    override val id: String = file.mediaId
    override val path: String = FileKit.storageFile(file.fileName).path
}