package com.jotte.camera.model

interface Zoomable {
    fun getZoom(): Float

    fun getZoomRange(): ClosedRange<Float>
}