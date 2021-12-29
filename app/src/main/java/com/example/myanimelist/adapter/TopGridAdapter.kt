package com.example.myanimelist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myanimelist.databinding.ItemRowCoverGridBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.ui.activity.DetailActivity

class TopGridAdapter(private val listTop: ArrayList<TopItem>): RecyclerView.Adapter<TopGridAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowCoverGridBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listTop[position]) {
                val score = score
                val imageUrl = imageUrl

                Glide.with(itemView)
                    .load(imageUrl)
                    .centerCrop()
                    .into(binding.ivImage)

                binding.apply {
                    textScore.text = score.toString()
                    containerAnime.setOnLongClickListener {
                        Toast.makeText(itemView.context, title, Toast.LENGTH_SHORT)
                            .show()
                        return@setOnLongClickListener true
                    }
                    containerAnime.setOnClickListener {
                        val intent = Intent(itemView.context, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_ANIME, listTop[position])
                        ContextCompat.startActivity(itemView.context, intent, null)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = listTop.size

    class ViewHolder (val binding: ItemRowCoverGridBinding) : RecyclerView.ViewHolder(binding.root)
}