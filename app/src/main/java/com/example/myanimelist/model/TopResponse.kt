package com.example.myanimelist.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class TopResponse(

    @field:SerializedName("top")
    val top: List<TopItem?>? = null,

    @field:SerializedName("request_hash")
    val requestHash: String? = null,

    @field:SerializedName("request_cached")
    val requestCached: Boolean? = null,

    @field:SerializedName("request_cache_expiry")
    val requestCacheExpiry: Int? = null
)

@Parcelize
data class TopItem(

    @field:SerializedName("end_date")
    val endDate: String? = null,

    @field:SerializedName("score")
    val score: Double? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("members")
    val members: Int? = null,

    @field:SerializedName("rank")
    val rank: Int? = null,

    @field:SerializedName("mal_id")
    val malId: Int? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("url")
    val url: String? = null,

    @field:SerializedName("episodes")
    val episodes: Int? = null,

    @field:SerializedName("start_date")
    val startDate: String? = null
) : Parcelable