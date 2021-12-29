package com.example.myanimelist.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.myanimelist.adapter.TopGridAdapter
import com.example.myanimelist.databinding.ActivityTopAllBinding
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.viewModel.MainViewModel

class TopAllActivity : AppCompatActivity() {
    private lateinit var top: String
    private lateinit var mainViewModel: MainViewModel

    private lateinit var binding: ActivityTopAllBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTopAllBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setToolbar()
        setList()
    }

    private fun setToolbar() {
        top = intent.getStringExtra(EXTRA_TOP).toString()

        binding.toolbar.title = top
        binding.toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun setList() {
        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]
        mainViewModel.isLoading.observe(this, {
            showLoading(it)
        })

        if (top == "Now Playing") {
            mainViewModel.listAiring.observe(this, {
                val topGridAdapter = TopGridAdapter(it as ArrayList<TopItem>)
                binding.rvTopAll.adapter = topGridAdapter
                binding.rvTopAll.setHasFixedSize(true)
            })
        } else if (top == "Popular") {
            mainViewModel.listPopular.observe(this, {
                val topGridAdapter = TopGridAdapter(it as ArrayList<TopItem>)
                binding.rvTopAll.adapter = topGridAdapter
                binding.rvTopAll.setHasFixedSize(true)
            })
        } else if (top == "Coming Soon") {
            mainViewModel.listUpcoming.observe(this, {
                val topGridAdapter = TopGridAdapter(it as ArrayList<TopItem>)
                binding.rvTopAll.adapter = topGridAdapter
                binding.rvTopAll.setHasFixedSize(true)
            })
        } else if (top == "Movie") {
            mainViewModel.listMovie.observe(this, {
                val topGridAdapter = TopGridAdapter(it as ArrayList<TopItem>)
                binding.rvTopAll.adapter = topGridAdapter
                binding.rvTopAll.setHasFixedSize(true)
            })
        }

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        const val EXTRA_TOP = "extra_top"
    }
}