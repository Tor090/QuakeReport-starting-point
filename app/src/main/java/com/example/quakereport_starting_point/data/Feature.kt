package com.example.quakereport_starting_point.data


import com.google.gson.annotations.SerializedName

data class Feature(
    val geometry: Geometry,
    val id: String,
    val properties: Properties,
    val type: String
)