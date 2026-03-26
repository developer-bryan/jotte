package com.jotte.cxui.controller

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

class CXPagerController<T : PagerItem> {

    val pagerItems = mutableStateListOf<T>()
    var initialIndex: Int = 0

    val hasItems by derivedStateOf { pagerItems.isNotEmpty() }

    fun setItems(
        items: List<T>,
        initialIndex: Int = 0
    ) {
        this.initialIndex = initialIndex
        this.pagerItems.clear()
        this.pagerItems.addAll(items)
    }

    fun removeItemById(id: String) {
        pagerItems
            .find { it.id == id }
            ?.let(pagerItems::remove)
    }

    fun clear() {
        this.pagerItems.clear()
        this.initialIndex = 0
    }

}

@Composable
fun <T : PagerItem> rememberPagerController(key: Any?) = remember(key) { CXPagerController<T>() }

@Stable
interface PagerItem {
    val id: String
    val path: String
}