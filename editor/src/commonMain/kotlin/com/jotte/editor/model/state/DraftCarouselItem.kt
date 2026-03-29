package com.jotte.editor.model.state

import com.jotte.cxui.component.CarouselItem
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.path

class DraftCarouselItem(val file: PlatformFile) : CarouselItem {
    override val id: String = file.name
    override val path: String = file.path
}