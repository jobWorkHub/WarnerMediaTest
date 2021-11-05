package com.example.warnermediatest

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.example.warnermediatest.Networking.Model.ImageCell
import com.example.warnermediatest.Networking.Request

class RecyclerAdapter(): ListAdapter<ImageCell, RecyclerAdapter.ViewHolder>(DiffCalc()) {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var imageCell: ImageCell? = null

        fun bind(imageCell: ImageCell){
            itemView.setOnClickListener(this)
            this.imageCell = imageCell
            val text: TextView = itemView.findViewById(R.id.cell_text)
            text.text = imageCell.title

            val image: NetworkImageView = itemView.findViewById(R.id.cell_image)

            val imageLoader = WarnerApplication.getContext()?.let { Request.getInstance(it).imageLoader }
            if (imageLoader != null) {

                val imageListener = ImageLoader.getImageListener(image, R.drawable.outline_image_24,
                    R.drawable.outline_broken_image_24)

                imageLoader.get(imageCell.url, imageListener)
            }
            image.setImageUrl(imageCell.url, imageLoader)
        }

        override fun onClick(v: View?) {
            if (v != null) {
                val action = ImageListFragmentDirections.actionImageListFragmentToViewImageFragment(imageCell!!.url)
                v.findNavController().navigate(action)
            }
        }

    }

    class DiffCalc: DiffUtil.ItemCallback<ImageCell>(){
        override fun areItemsTheSame(oldItem: ImageCell, newItem: ImageCell): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImageCell, newItem: ImageCell): Boolean {
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