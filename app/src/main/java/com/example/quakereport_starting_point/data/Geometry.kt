package com.example.quakereport_starting_point.data


import com.google.gson.annotations.SerializedName

data class Geometry(
    val coordinates: List<Double>,
    val type: String
)