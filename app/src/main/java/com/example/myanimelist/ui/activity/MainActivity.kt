package com.example.myanimelist.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myanimelist.adapter.ImageSliderAdapter
import com.example.myanimelist.adapter.TopAdapter
import com.example.myanimelist.databinding.ActivityMainBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var imageSliderAdapter: ImageSliderAdapter
    private lateinit var imageSliderHandler: Handler
    private lateinit var imageSliderRun: Runnable
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setPopular()
        setAiring()
        setUpcoming()
        setMovie()
        setListener()
    }

    private fun setPopular() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        mainViewModel.isVisible.observe(this, {
            showContainer(it)
        })
        mainViewModel.listPopular.observe(this, {
            imageSliderAdapter = ImageSliderAdapter(binding.viewPager, it as ArrayList<TopItem>)
            binding.viewPager.adapter = imageSliderAdapter
            binding.viewPager.clipToPadding = false
            binding.viewPager.clipChildren = false
            binding.viewPager.offscreenPageLimit = 3
            binding.viewPager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

            val pageTransformer = CompositePageTransformer()
            pageTransformer.addTransformer(MarginPageTransformer(16))
//            pageTransformer.addTransformer { page, position ->
//                val r: Float = 1 - abs(position)
//                page.scaleY = (0.85 + r * 0.15f).toFloat()
//            }
            binding.viewPager.setPageTransformer(pageTransformer)
            imageSliderHandler = Handler(Looper.getMainLooper())
            imageSliderRun = Runnable {
                binding.viewPager.currentItem = binding.viewPager.currentItem + 1
            }
            binding.viewPager.registerOnPageChangeCallback(
                object : ViewPager2.OnPageChangeCallback() {
                    override fun onPageSelected(position: Int) {
                        super.onPageSelected(position)
                        imageSliderHandler.removeCallbacks(imageSliderRun)
                        imageSliderHandler.postDelayed(imageSliderRun, 5000)
                    }
                }
            )
        })
    }

    private fun setAiring() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        mainViewModel.isVisible.observe(this, {
            showContainer(it)
        })
        mainViewModel.listAiring.observe(this, {
            val topAdapter = TopAdapter(it as ArrayList<TopItem>)
            binding.rvAiring.adapter = topAdapter
            binding.rvAiring.setHasFixedSize(true)
        })
    }

    private fun setUpcoming() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        mainViewModel.isVisible.observe(this, {
            showContainer(it)
        })
        mainViewModel.listUpcoming.observe(this, {
            val topAdapter = TopAdapter(it as ArrayList<TopItem>)
            binding.rvUpcoming.adapter = topAdapter
            binding.rvUpcoming.setHasFixedSize(true)
        })
    }

    private fun setMovie() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })
        mainViewModel.isVisible.observe(this, {
            showContainer(it)
        })
        mainViewModel.listMovie.observe(this, {
            val topAdapter = TopAdapter(it as ArrayList<TopItem>)
            binding.rvMovie.adapter = topAdapter
            binding.rvMovie.setHasFixedSize(true)
        })
    }

    private fun setListener() {
        binding.apply {
            containerSeeAllAiring.setOnClickListener {
                val intent = Intent(this@MainActivity, TopAllActivity::class.java)
                intent.putExtra(TopAllActivity.EXTRA_TOP, "Popular")
                startActivity(intent)
            }
            containerSeeAllUpcoming.setOnClickListener {
                val intent = Intent(this@MainActivity, TopAllActivity::class.java)
                intent.putExtra(TopAllActivity.EXTRA_TOP, "Coming Soon")
                startActivity(intent)
            }
            containerSeeAllMovie.setOnClickListener {
                val intent = Intent(this@MainActivity, TopAllActivity::class.java)
                intent.putExtra(TopAllActivity.EXTRA_TOP, "Movie")
                startActivity(intent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showContainer(isVisible: Boolean) {
        binding.containerSeeAllAiring.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.containerSeeAllMovie.visibility = if (isVisible) View.VISIBLE else View.GONE
        binding.containerSeeAllUpcoming.visibility = if (isVisible) View.VISIBLE else View.GONE
    }
}