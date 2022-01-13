package com.example.myanimelist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myanimelist.databinding.ItemRowGenreBinding
import com.example.myanimelist.model.GenresItem

class GenreAdapter(private val listGenre: ArrayList<GenresItem>): RecyclerView.Adapter<GenreAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRowGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(listGenre[position]) {
                val name = name

                binding.textGenre.text = name
            }
        }
    }

    override fun getItemCount(): Int = listGenre.size

    class ViewHolder (val binding: ItemRowGenreBinding) : RecyclerView.ViewHolder(binding.root)
}