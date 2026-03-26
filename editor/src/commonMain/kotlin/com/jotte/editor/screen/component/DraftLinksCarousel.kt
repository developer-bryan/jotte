package com.jotte.editor.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.jotte.cxui.theme.shapes
import com.jotte.editor.model.state.DraftLinkState

@Composable
internal fun DraftLinksCarousel(
    modifier: Modifier = Modifier,
    links: List<DraftLinkState>,
    onRemoveLinkClicked: (link: DraftLinkState) -> Unit
) {

    LazyRow(
        modifier = modifier,
        state = rememberLazyListState(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        content = {

            itemsIndexed(
                items = links,
                itemContent = { index, link ->
                    val shape =
                        when (index) {
                            0 if links.size > 1 -> shapes.mediaPreviewHeadShape
                            0 -> shapes.mediaPreviewShape
                            links.lastIndex -> shapes.mediaPreviewTailShape
                            else -> shapes.flatShape
                        }

                    DraftLinkComponent(
                        link = link,
                        modifier = Modifier.clip(shape),
                        onRemoveClicked = { onRemoveLinkClicked(link) }
                    )
                }
            )
        }
    )

}