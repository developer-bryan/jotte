package com.jotte.editor.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButtonIcon
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXText
import com.jotte.cxui.icon_email
import com.jotte.cxui.icon_link
import com.jotte.cxui.icon_phone
import com.jotte.cxui.icon_remove
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.data.persistence.data.LinkDto
import com.jotte.editor.model.state.DraftLinkState

@Composable
internal fun DraftLinkComponent(
    link: DraftLinkState,
    modifier: Modifier = Modifier,
    onRemoveClicked: () -> Unit
) {

    val icon =
        when (link.type) {
            LinkDto.LinkType.Url -> Res.drawable.icon_link
            LinkDto.LinkType.Phone -> Res.drawable.icon_phone
            LinkDto.LinkType.Email -> Res.drawable.icon_email
        }

    Row(
        modifier =
            modifier
                .widthIn(min = 200.dp)
                .background(colors.backgroundSecondary)
                .padding(horizontal = sizes.regular)
                .padding(vertical = sizes.small),
        verticalAlignment = Alignment.CenterVertically,
        content = {
            CXIcon(icon)
            Spacer(Modifier.width(sizes.extraSmall))
            CXText(
                text = link.link,
                color = colors.linkColor,
                style = typography.link
            )
            CXButtonIcon(
                icon = Res.drawable.icon_remove,
                backgroundColor = colors.backgroundSecondary,
                onClick = onRemoveClicked
            )
        }
    )

}
