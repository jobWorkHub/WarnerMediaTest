package com.example.warnermediatest.Networking

import android.content.Context
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.example.warnermediatest.Networking.Model.Photo
import com.example.warnermediatest.Networking.Model.PhotoResults
import com.google.gson.Gson

class NetworkManager {

    companion object {

        private val API_KEY = "1508443e49213ff84d566777dc211f2a"
        private val BASE_URL = "https://www.flickr.com/services/rest/"

        fun getPhotos(term: String, context: Context, onComplete: (ArrayList<String>) -> Void){

            val url = "https://www.flickr.com/services/rest/?method=flickr.photos.search&api_key=$API_KEY&text=$term&media=photos&format=json"

            val request = StringRequest(com.android.volley.Request.Method.GET, url, { response ->
                val gson = Gson()
                val parsedResponse = response.removePrefix("jsonFlickrApi(").removeSuffix(")")
                val photoDetails = gson.fromJson(parsedResponse, PhotoResults::class.java)

                var urls = ArrayList<String>()
                for (photo in photoDetails.photos.photo) {
                    urls.add(makePhotoURL(photo))
                }

                onComplete(urls)

                 }, {
                    print("error")
            })

            Request.getInstance(context.applicationContext).addToRequestQueue(request)
        }

        fun makePhotoURL(photo: Photo): String{
            val baseUrl = "https://live.staticflickr.com/"
            val resource = "${photo.server}/${photo.id}_${photo.secret}.jpg"

            return baseUrl + resource
        }
    }

}