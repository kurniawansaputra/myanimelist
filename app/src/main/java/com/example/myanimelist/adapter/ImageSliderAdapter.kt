package com.example.myanimelist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myanimelist.databinding.ItemRowImageSliderBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.ui.activity.DetailActivity

class ImageSliderAdapter(private val viewPager: ViewPager2, private val listImage: ArrayList<TopItem>): RecyclerView.Adapter<ImageSliderAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemRowImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listImage[position]) {
                Glide.with(itemView)
                    .load(imageUrl)
                    .centerCrop()
//                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(binding.ivSlider)
                if (position == listImage.size - 2) {
                    viewPager.post(run)
                }
                binding.ivSlider.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_ANIME, listImage[position])
                    ContextCompat.startActivity(itemView.context, intent, null)
                }
            }
        }
    }

    private val run = Runnable {
        listImage.addAll(listImage)
    }

    override fun getItemCount(): Int = listImage.size

    class ViewHolder (val binding: ItemRowImageSliderBinding): RecyclerView.ViewHolder(binding.root)
}