package com.example.quakereport_starting_point.data


import com.google.gson.annotations.SerializedName

data class Earthquake(
    val bbox: List<Double>,
    val features: List<Feature>,
    val metadata: Metadata,
    val type: String
)