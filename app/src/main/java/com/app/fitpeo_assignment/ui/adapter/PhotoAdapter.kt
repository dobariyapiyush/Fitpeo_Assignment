package com.app.fitpeo_assignment.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.fitpeo_assignment.R
import com.app.fitpeo_assignment.ui.activity.DetailActivity
import com.app.fitpeo_assignment.databinding.ItemPhotoBinding
import com.app.fitpeo_assignment.network.core.PHOTO_TITLE
import com.app.fitpeo_assignment.network.core.PHOTO_URL
import com.app.fitpeo_assignment.network.model.PhotoData
import com.facebook.shimmer.ShimmerFrameLayout
import com.squareup.picasso.Picasso

class PhotoAdapter : ListAdapter<PhotoData, RecyclerView.ViewHolder>(DiffCallback) {

    companion object {
        private const val VIEW_TYPE_SHIMMER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    var showShimmer = true

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == VIEW_TYPE_SHIMMER) {
            val shimmerView = inflater.inflate(R.layout.item_shimmer, parent, false)
            ShimmerViewHolder(shimmerView)
        } else {
            val binding = ItemPhotoBinding.inflate(inflater, parent, false)
            PhotoViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PhotoViewHolder) {
            val photo = getItem(position)
            holder.bind(photo)
        } else if (holder is ShimmerViewHolder) {
            holder.shimmerLayout.startShimmer()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (showShimmer) VIEW_TYPE_SHIMMER else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int {
        return if (showShimmer) 10 else super.getItemCount()
    }

    inner class ShimmerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shimmerLayout: ShimmerFrameLayout = itemView.findViewById(R.id.shimmer_layout)
    }

    inner class PhotoViewHolder(private val binding: ItemPhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val photo = getItem(position)
                    navigateToDetailActivity(photo)
                }
            }
        }

        private fun navigateToDetailActivity(photo: PhotoData) {
            val context = binding.root.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(PHOTO_TITLE, photo.title)
                putExtra(PHOTO_URL, photo.url)
            }
            context.startActivity(intent)
        }

        fun bind(photo: PhotoData) {
            binding.photo = photo
            val imageView = binding.imageView
            Picasso.get()
                .load(photo.thumbnailUrl)
                .placeholder(R.drawable.image_placeholder)
                .error(R.drawable.image_error)
                .into(imageView)
            binding.executePendingBindings()
        }
    }

    object DiffCallback : DiffUtil.ItemCallback<PhotoData>() {
        override fun areItemsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: PhotoData, newItem: PhotoData): Boolean {
            return oldItem == newItem
        }
    }
}

