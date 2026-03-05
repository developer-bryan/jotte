package com.jottie.room.screen.layout

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jottie.cxui.Res
import com.jottie.cxui.component.CXText
import com.jottie.cxui.empty_note_room_body
import com.jottie.cxui.empty_note_room_title
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.animateLottieCompositionAsState
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun NotesEmptyLayout(
    modifier: Modifier = Modifier
) {

    val composition by rememberLottieComposition {
        val json = Res.readBytes("files/empty_notes.json").decodeToString()
        LottieCompositionSpec.JsonString(json)
    }
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 98.dp),
        contentAlignment = Alignment.Center,
        content = {
            Column(
                modifier = Modifier.width(280.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    Image(
                        painter = rememberLottiePainter(
                            composition = composition,
                            progress = { progress },
                        ),
                        modifier = modifier
                            .width(280.dp)
                            .height(175.dp),
                        contentDescription = "Lottie animation"
                    )
                    CXText(
                        text = stringResource(Res.string.empty_note_room_title),
                        style = typography.headerTwo,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(sizes.small))
                    CXText(
                        text = stringResource(Res.string.empty_note_room_body),
                        style = typography.bodyOne,
                        textAlign = TextAlign.Center
                    )
                }
            )
        }
    )

}