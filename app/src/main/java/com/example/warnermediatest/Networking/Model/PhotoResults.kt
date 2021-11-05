package com.example.warnermediatest.Networking.Model

import com.example.warnermediatest.ImageSize

data class PhotoResults(
    val photos: Photos,
    val stat: String
)

data class Photos(
    val page: Int,
    val pages: Int,
    val perpage: Int,
    val total: Int,
    val photo: ArrayList<Photo>
)

data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String,
    val ispublic: Int,
    val isfriend: Int,
    val isfamily: Int
)

fun Photo.url(size: ImageSize): String {
    val baseUrl = "https://live.staticflickr.com/"

    var resource: String
    if (size.pxSize == 500){
        resource = "${this.server}/${this.id}_${this.secret}.jpg"
    }
    else {
        resource = "${this.server}/${this.id}_${this.secret}_${size.constant}.jpg"
    }

    return baseUrl + resource
}

fun Photos.urls(size: ImageSize): ArrayList<String> {
    val result = ArrayList<String>()
    for (photo in this.photo) {
        result.add(photo.url(size))
    }

    return result
}