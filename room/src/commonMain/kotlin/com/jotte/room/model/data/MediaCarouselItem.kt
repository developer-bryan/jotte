package com.jotte.room.model.data

import com.jotte.core.storageFile
import com.jotte.cxui.component.CarouselItem
import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.path

class MediaCarouselItem(fileName: String, mediaId: String): CarouselItem {
    override val id: String = mediaId
    override val path: String = FileKit.storageFile(fileName).path
}