package com.jotte.app.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXButton
import com.jotte.cxui.component.CXText
import com.jotte.cxui.create_my_first_room
import com.jotte.cxui.empty_rooms_body
import com.jotte.cxui.empty_rooms_title
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource

@Suppress("MagicNumber")
@Composable
internal fun EmptyRoomsScreen(
    onCreateRoomClicked: () -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.backgroundPrimary)
            .padding(horizontal = sizes.regular),
        contentAlignment = Alignment.Center,
        content = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.45F),
                content = {
                    Column(
                        modifier = Modifier.align(Alignment.TopCenter),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        content = {
                            CXText(
                                text = stringResource(Res.string.empty_rooms_title),
                                textAlign = TextAlign.Center,
                                style = typography.headerOne
                            )
                            Spacer(Modifier.height(sizes.small))
                            CXText(
                                text = stringResource(Res.string.empty_rooms_body),
                                textAlign = TextAlign.Center,
                                style = typography.bodyOne
                            )
                        }
                    )

                    CXButton(
                        text = stringResource(Res.string.create_my_first_room),
                        modifier = Modifier.align(Alignment.BottomCenter),
                        onClick = onCreateRoomClicked
                    )
                }
            )
        }
    )

}