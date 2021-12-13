package com.sparkdigital.brightwheelchallenge.repository.network

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("full_name")
    val fullName: String,
    @SerializedName("stargazers_count")
    val stargazersCount: Int,
)