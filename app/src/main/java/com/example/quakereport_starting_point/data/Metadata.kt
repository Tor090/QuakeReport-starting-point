package com.example.quakereport_starting_point.data


import com.google.gson.annotations.SerializedName

data class Metadata(
    val api: String,
    val count: Int,
    val generated: Long,
    val limit: Int,
    val offset: Int,
    val status: Int,
    val title: String,
    val url: String
)