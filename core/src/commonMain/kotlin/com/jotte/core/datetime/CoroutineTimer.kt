@file:OptIn(ExperimentalTime::class)

package com.jotte.core.datetime

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class CoroutineTimer(
    private val scope: CoroutineScope,
    private val conversionStrategy: suspend (Long) -> String = { it.toString() }
) {

    private var job: Job? = null
    private var startTime = 0L

    private val _elapsedTime = MutableStateFlow(0L)

    val rawElapsed: StateFlow<Long> = _elapsedTime
    val convertedElapsed = rawElapsed.map(conversionStrategy::invoke)

    fun start(intervalMs: Long = 100) {
        if (job?.isActive == true) return

        startTime = Clock.System.now().toEpochMilliseconds() - _elapsedTime.value

        job = scope.launch {
            while (isActive) {
                _elapsedTime.value = Clock.System.now().toEpochMilliseconds() - startTime
                delay(intervalMs)
            }
        }
    }

    fun stop() {
        job?.cancel()
        job = null
    }

}