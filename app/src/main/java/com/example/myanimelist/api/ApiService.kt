package com.example.myanimelist.api

import com.example.myanimelist.model.DetailResponse
import com.example.myanimelist.model.TopResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("top/anime/1/bypopularity")
    fun getListPopular(
    ): Call<TopResponse>

    @GET("top/anime/1/airing")
    fun getListAiring(
    ): Call<TopResponse>

    @GET("top/anime/1/movie")
    fun getListMovie(
    ): Call<TopResponse>

    @GET("top/anime/1/upcoming")
    fun getListUpcoming(
    ): Call<TopResponse>

    @GET("top/anime/1/tv")
    fun getListTv(
    ): Call<TopResponse>

    @GET("anime/{id}")
    fun getDetail(
        @Path("id") id: Int
    ): Call<DetailResponse>
}