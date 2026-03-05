package com.jotte.cxui.typography

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import com.jotte.cxui.Res
import com.jotte.cxui.google_sans_flex
import org.jetbrains.compose.resources.Font

object CXFont {

    val Normal: Font
        @Composable get() = Font(resource = Res.font.google_sans_flex, weight = FontWeight.Normal)

    val Medium: Font
        @Composable get() = Font(resource = Res.font.google_sans_flex, weight = FontWeight.Medium)

    val SemiBold: Font
        @Composable get() = Font(resource = Res.font.google_sans_flex, weight = FontWeight.SemiBold)

    val Black: Font
        @Composable get() = Font(resource = Res.font.google_sans_flex, weight = FontWeight.Black)
}