package com.example.warnermediatest.Networking

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.warnermediatest.Networking.Model.ImageCell
import com.example.warnermediatest.Networking.Model.Photo
import com.example.warnermediatest.Networking.Model.PhotoResults
import com.example.warnermediatest.WarnerApplication
import com.google.gson.Gson

class NetworkManager {

    companion object {

        private val API_KEY = "1508443e49213ff84d566777dc211f2a"
        private val BASE_URL = "https://www.flickr.com/services/rest/"

        fun getPhotos(term: String, onComplete: (PhotoResults) -> Unit){

            val url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$API_KEY&text=$term&media=photos&format=json"

            val request = StringRequest(com.android.volley.Request.Method.GET, url, { response ->
                val gson = Gson()
                val parsedResponse = response.removePrefix("jsonFlickrApi(").removeSuffix(")")
                val photoDetails = gson.fromJson(parsedResponse, PhotoResults::class.java)

                onComplete(photoDetails)

                 }, {
                    print("error")
            })
            WarnerApplication.getContext()?.let { Request.getInstance(it).addToRequestQueue(request) }
        }
    }

}