package com.jottie.camera.model

interface Zoomable {
    fun getZoom(): Float
    fun getZoomRange(): ClosedRange<Float>
}