package com.example.myanimelist.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myanimelist.databinding.ActivityDetailBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.viewModel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private var id: Int = 0
    private lateinit var title: String
    private lateinit var imagesUrl: String
    private lateinit var episodes: String
    private lateinit var duration: String
    private lateinit var score: String
    private lateinit var detailViewModel: DetailViewModel

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val anime = intent.getParcelableExtra<TopItem>(EXTRA_ANIME) as TopItem
        id = anime.malId!!

        setToolbar()
        setDetail()
    }

    private fun setToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setDetail() {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.getDetail(id)
        detailViewModel.detail.observe(this, {
            title = it.title.toString()
            imagesUrl = it.imageUrl.toString()
            episodes = it.episodes.toString() + " Episodes "
            duration = "· " + it.duration.toString()
            score = it.score.toString()


//            Glide.with(this)
//                .load(imagesUrl)
//                .centerCrop()
//                .placeholder(R.drawable.ic_image_placeholder)
//                .apply(bitmapTransform(BlurTransformation(25, 1)))
//                .into(binding.ivBackground)

            Glide.with(this)
                .load(imagesUrl)
                .centerCrop()
                .into(binding.ivCover)

            binding.textTitle.text = title
            binding.textEpisodes.text = episodes
            binding.textScore.text = score
            binding.textDuration.text = duration
        })
    }

    companion object {
        const val EXTRA_ANIME = "extra_anime"
    }
}