package com.jottie.room.screen.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXIcon
import com.jottie.cxui.component.CXText
import com.jottie.cxui.icon_email
import com.jottie.cxui.icon_link
import com.jottie.cxui.icon_phone
import com.jottie.cxui.theme.CXTheme
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import com.jottie.message.data.LinkDto
import com.jottie.room.model.state.NoteState

@Composable
internal fun NoteLinkComponent(
    link: NoteState.LinkState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val icon = when (link.type) {
        LinkDto.LinkType.Url -> Res.drawable.icon_link
        LinkDto.LinkType.Phone -> Res.drawable.icon_phone
        LinkDto.LinkType.Email -> Res.drawable.icon_email
    }

    Row(
        modifier = modifier
            .widthIn(min = 200.dp)
            .background(colors.backgroundSecondary)
            .clickable(
                onClickLabel = "open link",
                onClick = onClick
            )
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
        }
    )

}

@Composable
@Preview
private fun Preview() {
    CXTheme {
        Column(
            verticalArrangement = Arrangement.spacedBy(sizes.large),
            content = {
                NoteLinkComponent(
                    modifier = Modifier.background(colors.backgroundPrimary),
                    link = NoteState.LinkState(
                        id = "",
                        type = LinkDto.LinkType.Url,
                        link = "www.jottienotes.app"
                    ),
                    onClick = {}
                )
                NoteLinkComponent(
                    modifier = Modifier.background(colors.backgroundPrimary),
                    link = NoteState.LinkState(
                        id = "",
                        type = LinkDto.LinkType.Phone,
                        link = "8582302231"
                    ),
                    onClick = {}
                )
                NoteLinkComponent(
                    modifier = Modifier.background(colors.backgroundPrimary),
                    link = NoteState.LinkState(
                        id = "",
                        type = LinkDto.LinkType.Email,
                        link = "bryan.mills@jottie.app"
                    ),
                    onClick = {}
                )
            }
        )
    }
}