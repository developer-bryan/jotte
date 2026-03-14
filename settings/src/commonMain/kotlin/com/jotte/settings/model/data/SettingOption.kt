package com.jotte.settings.model.data

import com.jotte.cxui.Res
import com.jotte.cxui.appearance
import com.jotte.cxui.icon_appearance
import com.jotte.cxui.icon_sound_effects
import com.jotte.cxui.sound_effects
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

internal sealed class SettingOption(
    val label: StringResource,
    val icon: DrawableResource
) {

    data object Appearance: SettingOption(
        label = Res.string.appearance,
        icon = Res.drawable.icon_appearance
    )

    data object SoundEffects: SettingOption(
        label = Res.string.sound_effects,
        icon = Res.drawable.icon_sound_effects
    )

    companion object {
        fun asList(): List<SettingOption> {
            return listOf(
                Appearance,
                SoundEffects
            )
        }
    }

}