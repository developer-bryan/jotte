package com.jotte.whiteboard.model.data

import androidx.compose.ui.graphics.Color
import com.jotte.cxui.color.Pallete

internal sealed class PaintColor(open val color: Color) {
    data object Fuego : PaintColor(Pallete.Fuego)

    data object SprinkleOrange : PaintColor(Pallete.SprinkleOrange)

    data object SkyBlue : PaintColor(Pallete.SkyBlue)

    data object White : PaintColor(Pallete.White)

    data object MilkWhite : PaintColor(Pallete.MilkWhite)

    data object Black : PaintColor(Pallete.Black)

    data object LightBlack : PaintColor(Pallete.LightBlack)

    data object GrapeSoda : PaintColor(Pallete.GrapeSoda)

    data object GreenBean : PaintColor(Pallete.GreenBean)

    data class Custom(override val color: Color) : PaintColor(color)

    companion object {
        fun toList() =
            listOf(
                White,
                MilkWhite,
                Black,
                LightBlack,
                GrapeSoda,
                SkyBlue,
                GreenBean,
                Fuego,
                SprinkleOrange,
            )

        fun fromColor(color: Color): PaintColor =
            when (color) {
                Pallete.Fuego -> Fuego
                Pallete.SprinkleOrange -> SprinkleOrange
                Pallete.SkyBlue -> SkyBlue
                Pallete.White -> White
                Pallete.MilkWhite -> MilkWhite
                Pallete.Black -> Black
                Pallete.LightBlack -> LightBlack
                Pallete.GrapeSoda -> GrapeSoda
                Pallete.GreenBean -> GreenBean
                else -> Custom(color)
            }

    }

}