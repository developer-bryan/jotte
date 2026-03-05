package com.jotte.editor.model.state

import com.jotte.core.VirtualFile
import com.jotte.cxui.component.CarouselItem

class DraftCarouselItem(val file: VirtualFile): CarouselItem {
    override val id: String = file.fileName
    override val path: String = file.asVirtualPath()
}