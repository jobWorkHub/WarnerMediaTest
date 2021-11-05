package com.example.warnermediatest

enum class ImageSize(val constant: String, val pxSize: Int) {
    SMALL_CROPPED_THUMBNAIL("s", 75),
    LARGE_CROPPED_THUMBNAIL("q", 150),
    UNCROPPED_THUMBNAIL("t", 100),
    SMALL_ONE("m", 240),
    SMALL_TWO("n", 320),
    SMALL_THREE("w", 400),
    MEDIUM_ONE("", 500),
    MEDIUM_TWO("z", 640),
    MEDIUM_THREE("c", 800),
    LARGE_ONE("b", 1024),
    LARGE_TWO("h", 1600)
}