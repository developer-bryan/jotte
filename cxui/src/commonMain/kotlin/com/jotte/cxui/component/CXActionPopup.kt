package com.jotte.cxui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CXActionPopup(
    alignment: Alignment = Alignment.TopEnd,
    onDismissRequest: () -> Unit,
    actions: List<PopupAction>,
    onActionClicked: (action: PopupAction) -> Unit
) {

    var visibilityFlag by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        visibilityFlag = true
    }

    Popup(
        alignment = alignment,
        onDismissRequest = onDismissRequest,
        properties = PopupProperties(focusable = true),
        content = {
            AnimatedVisibility(
                visible = visibilityFlag,
                enter = fadeIn() + scaleIn(initialScale = 0.9f),
                exit = fadeOut() + scaleOut(targetScale = .9f),
                content = {
                    Column(
                        modifier = Modifier
                            .padding(sizes.small)
                            .width(226.dp)
                            .shadow(
                                elevation = 4.dp,
                                shape = shapes.alertDialogShape,
                                clip = true
                            )
                            .background(
                                color = colors.backgroundSecondary,
                                shape = shapes.alertDialogShape
                            )
                            .padding(vertical = sizes.extraSmall),
                        content = {

                            actions.forEach { action ->
                                CXButtonOption(
                                    label = stringResource(action.label),
                                    icon = action.icon,
                                    contentTint = action.contentColorProvider(),
                                    onClick = { onActionClicked(action) }
                                )
                            }

                        }
                    )
                }
            )
        }
    )
}

open class PopupAction(
    open val icon: DrawableResource,
    open val label: StringResource,
    open val contentColorProvider: @Composable () -> Color = { colors.contentPrimary }
)