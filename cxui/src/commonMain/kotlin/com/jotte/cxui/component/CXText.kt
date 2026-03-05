package com.jotte.cxui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.typography

@Composable
fun CXText(
    text: String,
    style: TextStyle,
    alpha: Float = 1F,
    modifier: Modifier = Modifier,
    color: Color = colors.contentPrimary,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    softWrap: Boolean = true,
    onTextLayout: ((TextLayoutResult) -> Unit)? = null
) {

    Text(
        text = text,
        style = style,
        modifier = modifier,
        color = color.copy(alpha = alpha),
        maxLines = maxLines,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        onTextLayout = onTextLayout
    )
}