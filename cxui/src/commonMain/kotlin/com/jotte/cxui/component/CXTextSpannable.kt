package com.jotte.cxui.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle

@Composable
fun CXTextSpannable(
    base: String,
    modifier: Modifier = Modifier,
    targets: Array<CXSpanTarget>,
    textStyle: TextStyle,
    spanStyle: SpanStyle,
    onClick: ((tag: String) -> Unit)? = null
) {

    val annotatedString = with(AnnotatedString.Builder(base)) {
        targets.forEach { spanTarget ->
            val start = base.indexOf(spanTarget.target)
            val end = start + spanTarget.target.length

            addStyle(spanStyle, start, end)
            if (spanTarget.isClickable) {
                addLink(
                    clickable = LinkAnnotation.Clickable(
                        tag = spanTarget.tag,
                        styles = TextLinkStyles(spanStyle),
                        linkInteractionListener = { onClick?.invoke(spanTarget.tag) }
                    ),
                    start = start,
                    end = end
                )
            }
        }

        toAnnotatedString()
    }

    Text(
        text = annotatedString,
        modifier = modifier,
        style = textStyle
    )
}

@Stable
data class CXSpanTarget(
    val target: String,
    val tag: String,
    val isClickable: Boolean = false
)