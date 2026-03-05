package com.jottie.app.screen.component

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.jottie.app.model.popup.DrawerPopupAction
import com.jottie.cxui.Res
import com.jottie.cxui.color.CXLightColors
import com.jottie.cxui.component.CXActionPopup
import com.jottie.cxui.component.CXButtonIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.composition.LocalColor
import com.jottie.cxui.composition.LocalSoundEffectPlayer
import com.jottie.cxui.icon_more_dots
import com.jottie.cxui.soundeffect.SoundEffect
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.density
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography

@Composable
internal fun DrawerListItem(
    roomName: String,
    modifiedOn: String,
    isSelected: Boolean = false,
    onClick: () -> Unit,
    onDeleteClicked: () -> Unit,
    onRenameClicked: () -> Unit
) {

    val colors = colors
    val selectedRectPadding = with(density) { sizes.tiny.toPx() }

    val activeRoomCornerRadius = with(density) { 12.dp.toPx() }

    var contextMenuVisible by remember { mutableStateOf(false) }

    val soundEffectPlayer = LocalSoundEffectPlayer.current

    LaunchedEffect(contextMenuVisible) {
        if (contextMenuVisible) {
            soundEffectPlayer?.playSound(SoundEffect.SoundEffectLongPress)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClickLabel = "select this room",
                onLongClickLabel = "open action menu",
                onClick = onClick,
                onLongClick = { contextMenuVisible = true }
            )
            .drawBehind {
                if (isSelected) {
                    this.drawRoundRect(
                        color = colors.accentColor,
                        size = Size(
                            width = this.size.width - (selectedRectPadding.times(2)),
                            height = this.size.height
                        ),
                        topLeft = Offset(
                            x = selectedRectPadding,
                            y = 0f
                        ),
                        cornerRadius = CornerRadius(
                            activeRoomCornerRadius,
                            activeRoomCornerRadius
                        )
                    )
                }
            }
            .padding(vertical = sizes.small)
            .padding(horizontal = sizes.regular),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        content = {
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(sizes.tiny),
                content = {
                    CXText(
                        text = roomName,
                        style = typography.bodyOne.copy(
                            fontWeight = FontWeight.Black
                        )
                    )
                    CXText(
                        text = modifiedOn,
                        color = colors.contentPrimaryReducedAlpha,
                        style = typography.bodyTwo
                    )
                }
            )
            Column {
                CXButtonIcon(
                    icon = Res.drawable.icon_more_dots,
                    onClick = { contextMenuVisible = true }
                )
                CompositionLocalProvider(LocalColor provides CXLightColors()) {
                    if (contextMenuVisible) {
                        CXActionPopup(
                            onDismissRequest = { contextMenuVisible = false },
                            actions = listOf(
                                DrawerPopupAction.Rename,
                                DrawerPopupAction.Delete
                            ),
                            onActionClicked = {
                                when (it) {
                                    DrawerPopupAction.Rename -> onRenameClicked()
                                    DrawerPopupAction.Delete -> onDeleteClicked()
                                }
                                contextMenuVisible = false
                            }
                        )
                    }
                }
            }
        }
    )
}