package com.jotte.app.model.popup

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.jotte.cxui.Res
import com.jotte.cxui.component.PopupAction
import com.jotte.cxui.delete_room
import com.jotte.cxui.icon_text
import com.jotte.cxui.icon_trash
import com.jotte.cxui.rename_room
import com.jotte.cxui.theme.colors
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal sealed class DrawerPopupAction(
    override val icon: DrawableResource,
    override val label: StringResource,
    override val contentColorProvider: @Composable (() -> Color) = { colors.contentPrimary }
) : PopupAction(icon, label) {

    data object Rename : DrawerPopupAction(
        icon = Res.drawable.icon_text,
        label = Res.string.rename_room
    )

    data object Delete: DrawerPopupAction(
        icon = Res.drawable.icon_trash,
        label = Res.string.delete_room,
        contentColorProvider = { colors.negativeColor }
    )


}