package com.jottie.cxui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.jottie.cxui.Res
import com.jottie.cxui.icon_link
import com.jottie.cxui.input_form_onclick_accessibility
import com.jottie.cxui.input_form_placeholder
import com.jottie.cxui.theme.CXTheme
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.iconSizes
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CXInputForm(
    value: String,
    modifier: Modifier = Modifier,
    placeholder: String = stringResource(Res.string.input_form_placeholder),
    icon: DrawableResource? = null,
    requestFocusOnLaunch: Boolean = false,
    enabled: Boolean = true,
    maxCharacters: Int = Int.MAX_VALUE,
    focusRequester: FocusRequester = remember { FocusRequester() },
    onValueChanged: (newName: String) -> Unit
) {

    var textFieldValue by remember(value) {
        mutableStateOf(
            TextFieldValue(
                text = value,
                selection = TextRange(value.length)
            )
        )
    }

    LaunchedEffect(Unit) {
        if (requestFocusOnLaunch) {
            focusRequester.requestFocus()
        }
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(
                min = sizes.interactableHeight,
                max = sizes.interactableHeight * 3
            )
            .clip(shapes.roundedInputFormShape)
            .clickable(
                enabled = enabled,
                onClickLabel = stringResource(Res.string.input_form_onclick_accessibility),
                onClick = focusRequester::requestFocus
            )
            .background(colors.backgroundSecondary)
            .padding(horizontal = sizes.regular)
            .padding(vertical = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.extraSmall),
        content = {
            icon?.let {
                CXIcon(
                    icon = icon,
                    size = iconSizes.small
                )
            }
            BasicTextField(
                value = textFieldValue,
                modifier = Modifier.focusRequester(focusRequester),
                onValueChange = {
                    if (it.text.length <= maxCharacters) {
                        textFieldValue = it
                        onValueChanged(it.text)
                    }
                },
                cursorBrush = SolidColor(colors.contentPrimary),
                textStyle = typography.bodyOne.copy(color = colors.contentPrimary),
                decorationBox = { innerTextField ->
                    if (textFieldValue.text.isEmpty()) {
                        CXText(
                            text = placeholder,
                            color = colors.contentPrimary.copy(alpha = 0.85F),
                            style = typography.bodyOne
                        )
                    }
                    innerTextField()
                },
            )
        }
    )
}

@Composable
@Preview
private fun Preview() {
    var value by remember { mutableStateOf("say anything") }

    CXTheme(isDarkMode = false) {
        CXInputForm(
            value = value,
            icon = Res.drawable.icon_link,
            onValueChanged = { value = it }
        )
    }
}