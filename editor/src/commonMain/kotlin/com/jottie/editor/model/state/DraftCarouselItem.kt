package com.jottie.editor.model.state

import com.jottie.core.VirtualFile
import com.jottie.cxui.component.CarouselItem

class DraftCarouselItem(val file: VirtualFile): CarouselItem {
    override val id: String = file.fileName
    override val path: String = file.asVirtualPath()
}