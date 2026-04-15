package com.jotte.editor.screen.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import com.jotte.core.link.model.AppLink
import com.jotte.core.link.model.AppLinkScheme
import com.jotte.core.link.usecase.ValidateAppLinkUseCase
import com.jotte.cxui.Res
import com.jotte.cxui.add
import com.jotte.cxui.component.CXButton
import com.jotte.cxui.component.CXButtonOption
import com.jotte.cxui.component.CXIcon
import com.jotte.cxui.component.CXInputForm
import com.jotte.cxui.component.CXText
import com.jotte.cxui.controller.DialogController
import com.jotte.cxui.extension.ColumnExtension.Space
import com.jotte.cxui.icon_chevron_down
import com.jotte.cxui.icon_email
import com.jotte.cxui.icon_link
import com.jotte.cxui.icon_phone
import com.jotte.cxui.link_dialog_email_value_placeholder
import com.jotte.cxui.link_dialog_phone_value_placeholder
import com.jotte.cxui.link_dialog_title
import com.jotte.cxui.link_dialog_url_value_placeholder
import com.jotte.cxui.theme.colors
import com.jotte.cxui.theme.shapes
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
internal fun DialogController<Nothing>.CreateAppLinkDialog(
    modifier: Modifier = Modifier,
    validator: ValidateAppLinkUseCase = koinInject(),
    onLinkCreated: (link: AppLink) -> Unit,
) {

    var name by rememberSaveable { mutableStateOf<String?>(null) }
    var scheme by rememberSaveable { mutableStateOf<AppLinkScheme>(AppLinkScheme.Web) }
    var value by rememberSaveable { mutableStateOf("") }

    var appLink by remember(scheme, value) {
        val link =
            AppLink(
                name = name,
                link = value,
                scheme = scheme
            )

        mutableStateOf(link)
    }

    val valueIconResource by remember {
        derivedStateOf {
            when (scheme) {
                AppLinkScheme.Web -> Res.drawable.icon_link
                AppLinkScheme.Phone -> Res.drawable.icon_phone
                AppLinkScheme.Email -> Res.drawable.icon_email
            }
        }
    }

    val valuePlaceholderResource by remember {
        derivedStateOf {
            when (scheme) {
                AppLinkScheme.Web -> Res.string.link_dialog_url_value_placeholder
                AppLinkScheme.Phone -> Res.string.link_dialog_phone_value_placeholder
                AppLinkScheme.Email -> Res.string.link_dialog_email_value_placeholder
            }
        }
    }

    val saveEnabled by remember(appLink) {
        derivedStateOf { validator(appLink) }
    }

    LaunchedEffect(scheme) {
        value = ""
        name = null
    }

    Dialog(
        onDismissRequest = this::hide,
        properties = DialogProperties(),
        content = {

            Column(
                modifier =
                    modifier
                        .wrapContentHeight()
                        .background(colors.backgroundPrimary, shapes.alertDialogShape)
                        .padding(
                            top = sizes.medium,
                            start = sizes.regular,
                            end = sizes.regular,
                            bottom = sizes.large
                        ),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {

                    CXText(
                        text = stringResource(Res.string.link_dialog_title),
                        style = typography.headerOne
                    )

                    Space { medium }

                    SchemeSelector(
                        currentScheme = scheme,
                        onSchemeSelected = { scheme = it }
                    )

                    Space { small }

                    CXInputForm(
                        value = value,
                        icon = valueIconResource,
                        placeholder = stringResource(valuePlaceholderResource),
                        onValueChanged = { value = it }
                    )

                    Space { huge }

                    CXButton(
                        enabled = saveEnabled,
                        text = stringResource(Res.string.add),
                        onClick = {
                            onLinkCreated(appLink)
                        }
                    )
                }
            )

        }
    )
}

@Composable
private fun SchemeSelector(
    currentScheme: AppLinkScheme,
    modifier: Modifier = Modifier,
    onSchemeSelected: (scheme: AppLinkScheme) -> Unit
) {

    var menuVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth(),
        content = {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(sizes.interactableHeight)
                        .background(colors.backgroundSecondary, CircleShape)
                        .clickable(
                            onClick = { menuVisible = !menuVisible }
                        ).padding(horizontal = sizes.regular),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                content = {
                    CXText(
                        text = currentScheme.scheme,
                        style = typography.bodyOne
                    )
                    CXIcon(Res.drawable.icon_chevron_down)
                }
            )
            DropdownMenu(
                expanded = menuVisible,
                modifier =
                    Modifier
                        .widthIn(min = 220.dp)
                        .background(colors.backgroundPrimary),
                onDismissRequest = { menuVisible = false },
                properties = PopupProperties(focusable = true),
                content = {
                    Column {
                        CXButtonOption(
                            label = AppLinkScheme.Web.scheme,
                            onClick = {
                                menuVisible = false
                                onSchemeSelected(AppLinkScheme.Web)
                            }
                        )
                        CXButtonOption(
                            label = AppLinkScheme.Phone.scheme,
                            onClick = {
                                menuVisible = false
                                onSchemeSelected(AppLinkScheme.Phone)
                            }
                        )
                        CXButtonOption(
                            label = AppLinkScheme.Email.scheme,
                            onClick = {
                                menuVisible = false
                                onSchemeSelected(AppLinkScheme.Email)
                            }
                        )
                    }
                }
            )
        }
    )

}