package com.example.myanimelist.ui.activity

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.myanimelist.R
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

        setSupportActionBar(binding.toolbar)

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
            containerAction.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.action, Toast.LENGTH_SHORT).show()
            }
            containerAdventure.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.adventure, Toast.LENGTH_SHORT).show()
            }
            containerComedy.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.comedy, Toast.LENGTH_SHORT).show()
            }
            containerMystery.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.mystery, Toast.LENGTH_SHORT).show()
            }
            containerDrama.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.drama, Toast.LENGTH_SHORT).show()
            }
            containerEcchi.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.ecchi, Toast.LENGTH_SHORT).show()
            }
            containerFantasy.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.fantasy, Toast.LENGTH_SHORT).show()
            }
            containerMore.setOnClickListener {
                Toast.makeText(this@MainActivity, R.string.more, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingPopular.containerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loadingAiring.containerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loadingUpcoming.containerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.loadingMovie.containerLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }
}