package com.htueko.apod.ui.adapter

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.htueko.apod.data.model.Apod
import com.htueko.apod.databinding.ItemApodBinding

typealias ClickListener = (Apod) -> Unit

class ApodAdapter(private val clickListener: ClickListener) :
    RecyclerView.Adapter<ApodAdapter.ApodViewHolder>() {

    private var data = mutableListOf<Apod>()

    fun updateData(newData: List<Apod>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApodViewHolder {
        val binding = ItemApodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApodViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApodViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount() = data.size

    inner class ApodViewHolder(private val binding: ItemApodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(apod: Apod) {
            binding.apply {
                tvTitle.text = apod.title
                Glide.with(itemView)
                    .load(apod.url)
                    .transition(withCrossFade())
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progress.apply {
                                isVisible = true
                                elevation = 1f
                            }
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            binding.progress.apply {
                                isVisible = false
                                elevation = 0f
                            }
                            return false
                        }
                    })
                    .into(imvPoster)
                itemView.setOnClickListener {
                    clickListener(apod)
                }
            }
        }
    }

}