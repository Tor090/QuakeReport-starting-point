package com.example.quakereport_starting_point


import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.quakereport_starting_point.data.Earthquake
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class EarthquakeActivity : AppCompatActivity() {

    val LOG_TAG = EarthquakeActivity::class.java.name

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.earthquake_activity)

        loadCountries()
        // Create a fake list of earthquake locations.


    }

    private fun loadCountries(){
        //initiate the service
        val loadingIndicator: View = findViewById(R.id.loading_indicator)

        val destinationService = ServiceBuilder.buildService(EarthquakeService::class.java)
        val requestCall = destinationService.getAffectedEventList()
        //make network call asynchronously
        requestCall.enqueue(object : Callback<Earthquake> {
            override fun onResponse(call: Call<Earthquake>, response: Response<Earthquake>) {
                Log.d("Response", "onResponse: ${response.body()}")
                if (response.isSuccessful){
                    val localEarthquake = response.body()!!
                    Log.d("Response", "countrylist size : ${localEarthquake}")
                    loadingIndicator.setVisibility(View.INVISIBLE)
                    updateUi(localEarthquake)
                }else{
                    Toast.makeText(this@EarthquakeActivity, "Something went wrong ${response.message()}", Toast.LENGTH_LONG).show()
                    loadingIndicator.setVisibility(View.GONE)

                }
            }
            override fun onFailure(call: Call<Earthquake>, t: Throwable) {
                Toast.makeText(this@EarthquakeActivity, "Trouble $t", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun updateUi(earthquakes : Earthquake){
        val earthquakeListView = findViewById<ListView>(R.id.list)

        val adapter = EarthquakeAdapter(earthquakes)

        earthquakeListView.adapter = adapter

        earthquakeListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, i, _ ->
                val currentEarthquake = adapter.getItem(i)
                val earthquakeUri: Uri = Uri.parse(currentEarthquake.properties.url)

                val websiteIntent = Intent(Intent.ACTION_VIEW, earthquakeUri)
                startActivity(websiteIntent)
            }
    }
}