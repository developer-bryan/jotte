package com.jotte.cxui.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXAlertDialog
import com.jotte.cxui.no
import com.jotte.cxui.yes
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

class DialogController<T>() {

    internal var isVisible by mutableStateOf(false)
    internal var extras by mutableStateOf<T?>(null)

    fun show(extras: T? = null) {
        isVisible = true
        this.extras = extras
    }

    fun hide() {
        isVisible = false
        extras = null
    }

}

@Composable
fun <T> rememberDialogController(
    title: StringResource,
    body: StringResource,
    positiveButton: StringResource = Res.string.yes,
    negativebutton: StringResource = Res.string.no,
    onPositiveButtonClick: (result: T?) -> Unit,
    onNegativeButtonClick: (() -> Unit)? = null,
    content: @Composable DialogController<T>.() -> Unit = {
        CXAlertDialog(
            title = stringResource(title),
            body = stringResource(body),
            positiveButtonContent = stringResource(positiveButton),
            negativeButtonContent = stringResource(negativebutton),
            onPositiveButtonClick = {
                onPositiveButtonClick(extras)
                hide()
            },
            onNegativeButtonClick = {
                onNegativeButtonClick?.invoke()
                hide()
            },
            onDismiss = this::hide
        )
    }
): DialogController<T> {

    val controller = remember { DialogController<T>() }

    if (controller.isVisible) {
        content(controller)
    }

    return controller
}

@Composable
fun <T> rememberDialogController(
    content: @Composable DialogController<T>.(extras: T?) -> Unit = {}
): DialogController<T> {

    val controller = remember { DialogController<T>() }

    if (controller.isVisible) {
        controller.content(controller.extras)
    }

    return controller
}