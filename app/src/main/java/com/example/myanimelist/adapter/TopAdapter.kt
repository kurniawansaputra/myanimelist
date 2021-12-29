package com.example.myanimelist.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myanimelist.R
import com.example.myanimelist.databinding.ItemBottomSheetBinding
import com.example.myanimelist.databinding.ItemRowCoverBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.ui.activity.DetailActivity
import com.google.android.material.bottomsheet.BottomSheetDialog

class TopAdapter(private val listTop: ArrayList<TopItem>): RecyclerView.Adapter<TopAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowCoverBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listTop[position]) {
                val score = score
                val startDate = startDate
                val endDate = endDate
                val imageUrl = imageUrl
                val episodes = episodes
                val type = type

                Glide.with(itemView)
                    .load(imageUrl)
                    .centerCrop()
//                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(binding.ivImage)

                binding.apply {
                    textScore.text = score.toString()
                    containerAnime.setOnLongClickListener {
                        Toast.makeText(itemView.context, title, Toast.LENGTH_SHORT)
                            .show()
                        return@setOnLongClickListener true
                    }
                    containerAnime.setOnClickListener {
                        val dialog = BottomSheetDialog(itemView.context, R.style.BottomSheetDialogTheme)
                        val bindingBottomSheet = ItemBottomSheetBinding.inflate(LayoutInflater.from(itemView.rootView.context))
                        dialog.setContentView(bindingBottomSheet.root)

                        Glide.with(itemView)
                            .load(imageUrl)
                            .centerCrop()
                            .into(bindingBottomSheet.ivCover)

                        bindingBottomSheet.apply {
                            containerBottomSheet.setOnClickListener {
                                val intent = Intent(itemView.context, DetailActivity::class.java)
                                intent.putExtra(DetailActivity.EXTRA_ANIME, listTop[position])
                                startActivity(itemView.context, intent, null)
                                dialog.dismiss()
                            }
                            buttonClose.setOnClickListener {
                                dialog.dismiss()
                            }
                            textType.text = type
                            textTitle.text = title
                            textScore.text = score.toString()
                            textStartToEnd.text = startDate + itemView.context.getString(R.string.to) + endDate
                            textEpisodes.text = episodes.toString() + itemView.context.getString(R.string.episode)
                        }

                        dialog.setCancelable(true)
                        dialog.show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = listTop.size

    class ViewHolder (val binding: ItemRowCoverBinding) : RecyclerView.ViewHolder(binding.root)
}