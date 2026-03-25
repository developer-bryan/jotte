package com.jotte.cxui.extension

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.jotte.cxui.size.CXSize
import com.jotte.cxui.theme.sizes

object ColumnExtension {
    @Composable
    @NonRestartableComposable
    fun Space(dimen: CXSize.() -> Dp) {
        Spacer(modifier = Modifier.height(dimen(sizes)))
    }

    @Composable
    fun ColumnScope.FillSpace() {
        Spacer(modifier = Modifier.weight(1F))
    }

    fun LazyListScope.lazySpace(spaceBlock: CXSize.() -> Dp) {
        item {
            Spacer(modifier = Modifier.height(spaceBlock(sizes)))
        }
    }

}
