package com.example.myanimelist.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myanimelist.adapter.GenreAdapter
import com.example.myanimelist.databinding.ActivityDetailBinding
import com.example.myanimelist.model.GenresItem
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.viewModel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    private var id: Int = 0
    private lateinit var title: String
    private lateinit var imagesUrl: String
    private lateinit var episodes: String
    private lateinit var duration: String
    private lateinit var score: String
    private lateinit var synopsis: String
    private lateinit var trailerUrl: String
    private lateinit var genreList: ArrayList<GenresItem>
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
        swipeRefresh()
    }

    private fun setToolbar() {
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setDetail() {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]
        detailViewModel.isLoading.observe(this, {
            setLoading(it)
        })
        detailViewModel.isRefresh.observe(this, {
            setRefresh(it)
        })
        detailViewModel.getDetail(id)
        detailViewModel.detail.observe(this, {
            title = it.title.toString()
            imagesUrl = it.imageUrl.toString()
            episodes = it.episodes.toString() + " Episodes "
            duration = "· " + it.duration.toString()
            score = it.score.toString()
            synopsis = it.synopsis.toString()
            trailerUrl = it.trailerUrl.toString()
            genreList = it.genres as ArrayList<GenresItem>

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

            Glide.with(this)
                .load(imagesUrl)
                .centerCrop()
                .into(binding.ivTrailer)

            binding.textTitle.text = title
            binding.textEpisodes.text = episodes
            binding.textScore.text = score
            binding.textDuration.text = duration
            binding.textSynopsis.text = synopsis
            binding.cardTrailer.setOnClickListener {
                val uri = Uri.parse("$trailerUrl")
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)
            }
            binding.textTitleTrailer.text = title

            val genreAdapter = GenreAdapter(genreList)
            binding.rvGenre.adapter = genreAdapter
            binding.rvGenre.setHasFixedSize(true)
        })
    }

    private fun setLoading(isLoading: Boolean) {
        binding.loadingDetail.containerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setRefresh(isRefresh: Boolean) {
        binding.swipeRefresh.isRefreshing = isRefresh
    }

    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            detailViewModel.getDetail(id)
        }
    }

    companion object {
        const val EXTRA_ANIME = "extra_anime"
    }
}