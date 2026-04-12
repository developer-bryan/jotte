package com.jotte.editor.model.state

import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

internal sealed class ContentSpan(val spanStyle: SpanStyle) {

    data object Header: ContentSpan(
        spanStyle = SpanStyle(
            fontWeight = FontWeight.Black,
            fontSize = 18.sp
        )
    )

    data object Bold: ContentSpan(
        spanStyle = SpanStyle(
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    )

    data object Normal: ContentSpan(
        spanStyle = SpanStyle(
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        )
    )

}