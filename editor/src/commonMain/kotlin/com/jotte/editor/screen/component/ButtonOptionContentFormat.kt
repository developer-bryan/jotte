package com.jotte.editor.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.cxui.typography.CXFont

@Composable
internal fun ButtonOptionContentFormat(
    option: Char,
    onClickLabel: String,
    modifier: Modifier = Modifier,
    isToggled: Boolean = false,
    onClick: () -> Unit
) {

    val background = if (isToggled) colors.accentColor else Color.Transparent
    val labelColor = if (isToggled) colors.onAccentColor else colors.contentPrimary

    val labelStyle =
        typography.headerOne.copy(fontFamily = FontFamily(CXFont.Normal))

    Box(
        modifier =
            modifier
                .size(sizes.interactableHeightSmall)
                .background(background, CircleShape)
                .clickable(
                    onClickLabel = onClickLabel,
                    onClick = onClick
                ),
        contentAlignment = Alignment.Center,
        content = {
            CXText(
                text = option.toString(),
                color = labelColor,
                style = labelStyle
            )
        }
    )

}