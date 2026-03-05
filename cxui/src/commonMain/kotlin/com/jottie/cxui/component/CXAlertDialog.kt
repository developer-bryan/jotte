package com.jottie.cxui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.jottie.cxui.extension.ColumnExtension.Space
import com.jottie.cxui.theme.colors
import com.jottie.cxui.theme.shapes
import com.jottie.cxui.theme.sizes
import com.jottie.cxui.theme.typography
import org.jetbrains.compose.resources.DrawableResource

@Composable
fun CXAlertDialog(
    title: String,
    body: String,
    icon: DrawableResource? = null,
    positiveButtonContent: String,
    negativeButtonContent: String,
    onDismiss: () -> Unit,
    onPositiveButtonClick: () -> Unit,
    onNegativeButtonClick: () -> Unit,
) {

    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(),
        content = {
            Column(
                modifier = Modifier
                    .clip(shapes.alertDialogShape)
                    .background(colors.backgroundPrimary)
                    .padding(horizontal = sizes.medium)
                    .padding(vertical = sizes.medium),
                horizontalAlignment = Alignment.CenterHorizontally,
                content = {
                    icon?.let {
                        CXIcon(it)
                        Space { extraLarge }
                    }
                    DialogTitle(title)
                    Space { extraSmall }
                    DialogBody(body)
                    Space { extraLarge }
                    BinaryDialogButtons(
                        negativeButtonContent = negativeButtonContent,
                        positiveButtonContent = positiveButtonContent,
                        onNegativeClicked = { onNegativeButtonClick() },
                        onPositiveClicked = { onPositiveButtonClick() }
                    )
                }
            )
        }
    )
}

@Composable
fun DialogTitle(title: String) {
    CXText(
        text = title,
        style = typography.headerTwo,
        textAlign = TextAlign.Center
    )
}

@Composable
fun DialogBody(body: String) {
    CXText(
        text = body,
        style = typography.bodyOne,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BinaryDialogButtons(
    negativeButtonContent: String,
    positiveButtonContent: String,
    onPositiveClicked: () -> Unit,
    onNegativeClicked: () -> Unit
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(sizes.small),
        content = {
            CXButton(
                text = negativeButtonContent,
                modifier = Modifier.weight(1F),
                backgroundColor = Color.Transparent,
                contentColor = colors.contentPrimary,
                onClick = onNegativeClicked
            )
            CXButton(
                text = positiveButtonContent,
                modifier = Modifier.weight(1F),
                onClick = onPositiveClicked
            )
        }
    )
}
