package com.harshlabs.randomdoggenerator.presentation.recently_generated_dogs_screen

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.harshlabs.randomdoggenerator.databinding.ImageRecyclerViewItemBinding

class DogImageAdapter : ListAdapter<Bitmap, DogImageViewHolder>( DogImageDiffUtilCallback() ){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogImageViewHolder {
        return DogImageViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: DogImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DogImageViewHolder(private val binding: ImageRecyclerViewItemBinding) :
    ViewHolder(binding.root) {
        fun bind(bitmap: Bitmap) {
            binding.imageViewItem.setImageBitmap(bitmap)
        }

    companion object {
        fun from(parent: ViewGroup) : DogImageViewHolder {
            val layoutInflator = LayoutInflater.from(parent.context)
            val binding = ImageRecyclerViewItemBinding.inflate(layoutInflator, parent, false)
            return DogImageViewHolder(binding)
        }
    }
}

class DogImageDiffUtilCallback: DiffUtil.ItemCallback<Bitmap>() {
    override fun areItemsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Bitmap, newItem: Bitmap): Boolean {
        return oldItem.sameAs(newItem)
    }
}