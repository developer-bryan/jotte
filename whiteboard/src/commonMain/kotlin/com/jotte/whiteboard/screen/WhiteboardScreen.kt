package com.jotte.whiteboard.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.whiteboard.screen.layout.WhiteboardHeader

@Composable
fun WhiteboardScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize(),
        content = {

            WhiteboardHeader(onBackClicked = onBackClicked)

        }
    )

}
