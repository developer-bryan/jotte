package com.jotte.editor.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jotte.cxui.Res
import com.jotte.cxui.color.Pallete
import com.jotte.cxui.text_color_selector_click_label
import com.jotte.cxui.theme.CXTheme
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.iconSizes
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ButtonTextColor(
    modifier: Modifier = Modifier,
    color: Color,
    onClick: () -> Unit
) {

    Box(
        modifier =
            modifier
                .size(iconSizes.regular)
                .border(
                    width = 2.dp,
                    color = colors.contentPrimary,
                    shape = CircleShape
                ).background(color)
                .clickable(
                    onClickLabel = stringResource(Res.string.text_color_selector_click_label),
                    onClick = onClick
                )
    )

}

@Composable
@Preview
private fun Preview() {

    CXTheme {
        ButtonTextColor(
            modifier = Modifier,
            color = Pallete.Fuego,
            onClick = {}
        )
    }

}