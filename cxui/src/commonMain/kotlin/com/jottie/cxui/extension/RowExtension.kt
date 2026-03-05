package com.jottie.cxui.extension

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jottie.cxui.size.CXSize
import com.jottie.cxui.theme.sizes

object RowExtension {
    @Composable
    @NonRestartableComposable
    fun Space(dimen: CXSize.() -> Dp) {
        Spacer(modifier = Modifier.width(dimen(sizes)))
    }

    @Composable
    @NonRestartableComposable
    fun RowScope.Space(dimen: CXSize.() -> Dp) {
        Spacer(modifier = Modifier.width(dimen(sizes)))
    }

    @Composable
    fun RowScope.FillSpace() {
        Spacer(modifier = Modifier.weight(1F))
    }

}
