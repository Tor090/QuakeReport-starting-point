package com.example.quakereport_starting_point

import com.example.quakereport_starting_point.data.Earthquake
import retrofit2.Call
import retrofit2.http.GET

interface EarthquakeService {
    @GET("fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10")
    fun getAffectedEventList() : Call<Earthquake>
}