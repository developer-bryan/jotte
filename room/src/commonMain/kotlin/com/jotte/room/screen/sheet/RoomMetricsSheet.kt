package com.jotte.room.screen.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.jotte.cxui.component.CXText
import com.jotte.cxui.theme.sizes
import com.jotte.cxui.theme.typography
import com.jotte.room.model.state.RoomMetricsState

@Composable
internal fun RoomMetricsSheet(
    metrics: RoomMetricsState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier =
            modifier
                .padding(top = sizes.medium)
                .padding(bottom = sizes.huge)
                .padding(horizontal = sizes.regular),
        content = {

            CXText(
                text = "total notes",
                style = typography.bodyTwo
            )
            Spacer(Modifier.height(sizes.extraSmall))
            CXText(
                text = metrics.totalNotes.toString(),
                style = typography.headerFour
            )

            Spacer(Modifier.height(sizes.medium))

            CXText(
                text = "created on",
                style = typography.bodyTwo
            )
            Spacer(Modifier.height(sizes.extraSmall))
            CXText(
                text = metrics.createdOn,
                style = typography.headerFour
            )

            Spacer(Modifier.height(sizes.medium))

            CXText(
                text = "last modified on",
                style = typography.bodyTwo
            )
            Spacer(Modifier.height(sizes.extraSmall))
            CXText(
                text = metrics.modifiedOn,
                style = typography.headerFour
            )

        }
    )

}