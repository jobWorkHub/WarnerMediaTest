package com.example.warnermediatest.Networking.Model

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