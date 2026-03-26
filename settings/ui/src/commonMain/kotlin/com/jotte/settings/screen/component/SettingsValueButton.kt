package com.jotte.settings.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jotte.cxui.component.CXText
import com.jotte.cxui.modifier.buildModifier
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography

@Composable
internal fun SettingsValueButton(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    isSelectedValue: Boolean = false,
    onClick: () -> Unit
) {

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clip(shapes.roundedSettingsValueButtonShape)
                .clickable(
                    onClickLabel = "",
                    onClick = onClick
                ).buildModifier {
                    if (isSelectedValue) {
                        this.background(colors.contentPrimaryLowAlpha)
                    } else {
                        this
                    }
                }.padding(sizes.small),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {

            CXText(
                text = title,
                style = typography.headerFour
            )

            CXText(
                text = description,
                style = typography.bodyOne
            )

        }
    )

}