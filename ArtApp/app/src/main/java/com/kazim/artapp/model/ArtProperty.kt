package com.kazim.artapp.model

data class ArtProperty(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)