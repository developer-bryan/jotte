@file:OptIn(ExperimentalMaterial3Api::class)

package com.jotte.editor.screen.component

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jotte.cxui.Res
import com.jotte.cxui.component.CXText
import com.jotte.cxui.draft_hint
import com.jotte.cxui.theme.typography
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DraftContentComponent(
    state: RichTextState,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester,
) {

    LaunchedEffect(Unit) { focusRequester.requestFocus() }

    RichTextEditor(
        state = state,
        modifier = Modifier.fillMaxWidth(),
        textStyle = typography.bodyOne,
        colors =
            RichTextEditorDefaults.richTextEditorColors(
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
        contentPadding = PaddingValues(0.dp),
        placeholder = {
            CXText(
                text = stringResource(Res.string.draft_hint),
                alpha = 0.85F,
                style = typography.bodyOne
            )
        }
    )
}