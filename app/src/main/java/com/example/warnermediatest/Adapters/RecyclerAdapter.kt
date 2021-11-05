package com.example.warnermediatest.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.example.warnermediatest.Fragments.ImageListFragmentDirections
import com.example.warnermediatest.ImageSize
import com.example.warnermediatest.JavaWarnerApplication
import com.example.warnermediatest.Networking.Model.Photo
import com.example.warnermediatest.Networking.Model.url
import com.example.warnermediatest.Networking.Request
import com.example.warnermediatest.R
import com.example.warnermediatest.WarnerApplication

class RecyclerAdapter(): ListAdapter<Photo, RecyclerAdapter.ViewHolder>(DiffCalc()) {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var photo: Photo? = null
        private var imageSize = ImageSize.LARGE_CROPPED_THUMBNAIL

        fun bind(photo: Photo){
            itemView.setOnClickListener(this)
            this.photo = photo
            val text: TextView = itemView.findViewById(R.id.cell_text)
            text.text = photo.title

            val image: NetworkImageView = itemView.findViewById(R.id.cell_image)

            val imageLoader = JavaWarnerApplication.getAppContext().let { Request.getInstance(it).imageLoader }
            if (imageLoader != null) {

                val imageListener = ImageLoader.getImageListener(image, R.drawable.outline_image_24,
                    R.drawable.outline_broken_image_24
                )

                imageLoader.get(photo.url(imageSize), imageListener)
            }
            image.setImageUrl(photo.url(imageSize), imageLoader)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                val action = ImageListFragmentDirections.actionImageListFragmentToViewImageFragment(photo!!.url(imageSize))
                v.findNavController().navigate(action)
            }
        }

    }

    class DiffCalc: DiffUtil.ItemCallback<Photo>(){
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val cell = LayoutInflater.from(parent.context).inflate(R.layout.image_list_cell, parent, false)
        return ViewHolder(cell)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}