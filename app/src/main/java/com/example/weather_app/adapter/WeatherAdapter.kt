package com.example.weather_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_app.R
import com.example.weather_app.model.Data
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class WeatherAdapter(): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
     var listData = mutableListOf<Data>()
    fun setWeatherData(listData:List<Data>){
        this.listData = listData.toMutableList()
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_weather,parent,false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        val hours = getDate(listData[position].timestampLocal)
        holder.hour.text = hours
        holder.temperature.text = listData[position].temp.roundToInt().toString()
        holder.imageCloudy.let {
            Glide.with(it)
                .load("https://www.weatherbit.io/static/img/icons/${listData[position].weather.icon}.png")
                .into(it)
        }



    }

    override fun getItemCount(): Int {
        return listData.size
    }

    inner class WeatherViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val imageCloudy = itemView.findViewById<ImageView>(R.id.iv_rv_cloudly)
        val hour = itemView.findViewById<TextView>(R.id.tv_rv_hour)
        val temperature = itemView.findViewById<TextView>(R.id.tv_rv_temp)

    }
    private fun getDate(dateStr: String) : String {
        /** DEBUG dateStr = '2006-04-16T04:00:00Z' **/
        //"yyyy-MM-dd'T'HH:mm:ss"
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
        val mDate = formatter.parse(dateStr) // this never ends while debugging
        val calendar = Calendar.getInstance()
        calendar.time = mDate
        val hour = calendar.get(Calendar.HOUR_OF_DAY).toInt()
        val minutes = calendar.get(Calendar.MINUTE)
        val result = "${(hour)}:0${minutes}"
        return result
    }
}