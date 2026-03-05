package com.jottie.editor.model.state

import com.jottie.message.data.LinkDto

internal data class DraftLinkState(
    val id: String? = null,
    val link: String,
    val type: LinkDto.LinkType
)