package com.jottie.cxui.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.jottie.cxui.controller.CXToastController
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CXToast(
    state: CXToastController,
    modifier: Modifier = Modifier
) {

    val customTransitionSpec: AnimatedContentTransitionScope<StringResource?>.() ->
    ContentTransform = {
        (slideInVertically { -it } togetherWith slideOutVertically { -it })
            .using(SizeTransform(clip = false))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .padding(top = sizes.huge),
        contentAlignment = Alignment.TopCenter
    ) {
        AnimatedContent(
            targetState = state.msgRes,
            transitionSpec = customTransitionSpec,
            contentAlignment = Alignment.TopCenter,
            content = { msgRes ->

                if (msgRes != null) {
                    Box(
                        modifier = modifier
                            .padding(horizontal = sizes.medium)
                            .fillMaxWidth()
                            .clip(shapes.toastShape)
                            .background(colors.contentPrimary)
                            .padding(horizontal = sizes.regular)
                            .padding(vertical = sizes.small),
                        contentAlignment = Alignment.Center,
                        content = {
                            CXText(
                                text = stringResource(msgRes),
                                style = typography.bodyOne,
                                color = colors.backgroundPrimary
                            )
                        }
                    )
                }
            }
        )
    }
}