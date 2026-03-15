package com.jotte.settings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.jotte.cxui.theme.CXThemeBox
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.settings.model.data.SettingOption
import com.jotte.settings.model.state.SettingSheet
import com.jotte.settings.model.state.SettingsState
import com.jotte.settings.screen.layout.SettingsHeader
import com.jotte.settings.screen.layout.SettingsList
import com.jotte.settings.screen.sheet.AppearanceSettingsSheet
import com.jotte.settings.screen.sheet.SoundEffectsSettingsSheet
import com.jotte.settings.viewmodel.SettingsViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {

    val scope = rememberCoroutineScope()
    val settings = remember { SettingOption.asList() }
    val sheetState = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    val viewModel: SettingsViewModel = koinViewModel<SettingsViewModel>()

    var settingSheet by remember { mutableStateOf<SettingSheet?>(null) }
    val settingsState by viewModel.settingsState.collectAsState(SettingsState())

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetBackgroundColor = colors.backgroundPrimary,
        sheetShape = shapes.roundedSheetShape,
        sheetContent = {
            when (settingSheet) {
                SettingSheet.AppearanceSheet -> AppearanceSettingsSheet(
                    appAppearance = settingsState.appearance,
                    onClick = viewModel::updateAppAppearance
                )
                SettingSheet.SoundEffectSheet -> SoundEffectsSettingsSheet(
                    soundEffectsEnabled = settingsState.soundEffectsEnabled,
                    onClick = viewModel::updateSoundEffects
                )
                else -> {}
            }
        },
        content = {
            CXThemeBox {
                Column(
                    modifier = modifier,
                    content = {

                        SettingsHeader(onBackClicked = onBackClicked)

                        SettingsList(
                            settings = settings,
                            state = settingsState,
                            modifier = Modifier.padding(top = sizes.small),
                            onSettingClicked = {
                                settingSheet = when (it) {
                                    SettingOption.Appearance -> SettingSheet.AppearanceSheet
                                    SettingOption.SoundEffects -> SettingSheet.SoundEffectSheet
                                }
                                scope.launch { sheetState.show() }
                            }
                        )

                    }
                )
            }
        }
    )

}