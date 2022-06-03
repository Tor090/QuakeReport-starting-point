package com.example.quakereport_starting_point

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.quakereport_starting_point.data.Earthquake
import com.example.quakereport_starting_point.data.Feature
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class EarthquakeAdapter(val earthquakes : Earthquake) : BaseAdapter() {

    private val LOCATION_SEPARATOR = " of "

    override fun getCount(): Int {
        return earthquakes.features.size
    }
    override fun getItem(position: Int): Feature {
        return earthquakes.features[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val listItemView = convertView ?: LayoutInflater.from(parent.context)
            .inflate(R.layout.earthquaqe_item_list, parent, false)

        val currentEarthquake = getItem(position)

        val magnitudeView = listItemView.findViewById<TextView>(R.id.magnitude)

        val magnitude = formatMagnitude(currentEarthquake.properties.mag)
        magnitudeView.text = magnitude

        val magnitudeCircle = magnitudeView.background as GradientDrawable

        val magnitudeColor = getMagnitudeColor(listItemView.context, currentEarthquake.properties.mag)
        magnitudeCircle.setColor(magnitudeColor)

        val earthquakeLocation = currentEarthquake.properties.place

        val primaryLocation: String
        val locationOffset: String

        if (earthquakeLocation.contains(LOCATION_SEPARATOR)) {
            val parts: List<String> = earthquakeLocation.split(LOCATION_SEPARATOR)

            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        }else{
            locationOffset = listItemView.context.getString(R.string.near_the);
            primaryLocation = earthquakeLocation;
        }

        val primaryLocationView = listItemView.findViewById<TextView>(R.id.primary_location)
        primaryLocationView.text = primaryLocation

        val locationOffsetView = listItemView.findViewById<TextView>(R.id.location_offset)
        locationOffsetView.text = locationOffset

        val dateObject = Date(currentEarthquake.properties.time)

        val dateView = listItemView.findViewById<TextView>(R.id.date)
        dateView.text = formatDate(dateObject)

        val timeView = listItemView.findViewById<TextView>(R.id.time)
        timeView.text = formatTime(dateObject)

        return listItemView
    }


    private fun formatMagnitude(magnitude: Double): String {
        val magnitudeFormat = DecimalFormat("0.0")
        return magnitudeFormat.format(magnitude)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(dateObject: Date): String {
        val dateFormat = SimpleDateFormat("LLL dd, yyyy")
        return dateFormat.format(dateObject)
    }

    @SuppressLint("SimpleDateFormat")
    private fun formatTime(dateObject: Date): String? {
        val timeFormat = SimpleDateFormat("h:mm a")
        return timeFormat.format(dateObject)
    }

    private fun getMagnitudeColor(context : Context, magnitude: Double): Int {
        val magnitudeColorResourceId: Int
        val magnitudeFloor = Math.floor(magnitude).toInt()
        when (magnitudeFloor) {
            0, 1 -> magnitudeColorResourceId = R.color.magnitude1
            2 -> magnitudeColorResourceId = R.color.magnitude2
            3 -> magnitudeColorResourceId = R.color.magnitude3
            4 -> magnitudeColorResourceId = R.color.magnitude4
            5 -> magnitudeColorResourceId = R.color.magnitude5
            6 -> magnitudeColorResourceId = R.color.magnitude6
            7 -> magnitudeColorResourceId = R.color.magnitude7
            8 -> magnitudeColorResourceId = R.color.magnitude8
            9 -> magnitudeColorResourceId = R.color.magnitude9
            else -> magnitudeColorResourceId = R.color.magnitude10plus
        }
        return ContextCompat.getColor(context, magnitudeColorResourceId)
    }
}