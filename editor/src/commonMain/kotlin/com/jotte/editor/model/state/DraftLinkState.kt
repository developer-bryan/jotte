package com.jotte.editor.model.state

import com.jotte.data.persistence.data.LinkDto

internal data class DraftLinkState(
    val id: String? = null,
    val link: String,
    val type: LinkDto.LinkType
)