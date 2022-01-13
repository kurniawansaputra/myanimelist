package com.example.myanimelist.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myanimelist.api.ApiConfig
import com.example.myanimelist.model.TopItem
import com.example.myanimelist.model.TopResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {
    private val _listPopular = MutableLiveData<List<TopItem>>()
    val listPopular: LiveData<List<TopItem>> = _listPopular

    private val _listAiring = MutableLiveData<List<TopItem>>()
    val listAiring: LiveData<List<TopItem>> = _listAiring

    private val _listMovie = MutableLiveData<List<TopItem>>()
    val listMovie: LiveData<List<TopItem>> = _listMovie

    private val _listUpcoming = MutableLiveData<List<TopItem>>()
    val listUpcoming: LiveData<List<TopItem>> = _listUpcoming

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getPopular()
        getAiring()
        getUpcoming()
        getMovie()
    }

    private fun getPopular() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListPopular()
        client.enqueue(object : Callback<TopResponse> {
            override fun onResponse(call: Call<TopResponse>, response: Response<TopResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listPopular.value = response.body()?.top as List<TopItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getAiring() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListAiring()
        client.enqueue(object : Callback<TopResponse> {
            override fun onResponse(call: Call<TopResponse>, response: Response<TopResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listAiring.value = response.body()?.top as List<TopItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getMovie() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListMovie()
        client.enqueue(object : Callback<TopResponse> {
            override fun onResponse(call: Call<TopResponse>, response: Response<TopResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listMovie.value = response.body()?.top as List<TopItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    private fun getUpcoming() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListUpcoming()
        client.enqueue(object : Callback<TopResponse> {
            override fun onResponse(call: Call<TopResponse>, response: Response<TopResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listUpcoming.value = response.body()?.top as List<TopItem>?
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<TopResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object{
        private const val TAG = "MainViewModel"
    }
}