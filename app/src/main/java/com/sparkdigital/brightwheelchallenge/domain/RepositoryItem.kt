package com.sparkdigital.brightwheelchallenge.domain

data class RepositoryItem(
    val name: String,
    val owner: String,
    val stars: Int,
    var topContributor: String = ""
)
