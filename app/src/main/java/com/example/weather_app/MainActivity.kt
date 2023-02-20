package com.example.weather_app

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weather_app.adapter.WeatherAdapter
import com.example.weather_app.model.Data
import com.example.weather_app.model.ForecastWeatherDto
import com.example.weather_app.model.Weather
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel>()
    private val weatherAdapter = WeatherAdapter()
    private var dayOfWeek :TextView? = null
    private var temp_current:TextView? = null
    private var wind:TextView? = null
    private var cloudy:TextView? = null
    private var tempByFeel:TextView? = null
    private var imageCloudy: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        observeUpdates()
    }
    private fun initUI() {
        dayOfWeek = findViewById(R.id.tv_day_of_week)
        temp_current =findViewById(R.id.tv_temp_current)
        wind = findViewById(R.id.tv_wind)
        cloudy = findViewById(R.id.tv_cloudy)
        tempByFeel = findViewById(R.id.tv_temp_by_feel)
        imageCloudy = findViewById(R.id.iv_partly_cloudy)
        initRecyclerView()
    }


    private fun observeUpdates() {
        viewModel.currentWeather.observe(this){
            renderWeather(it)
        }
        viewModel.forecastWeather.observe(this, androidx.lifecycle.Observer {
            val dataList = it.data
            weatherAdapter.setWeatherData(dataList)
            weatherAdapter.notifyDataSetChanged()
        })
    }

    private fun renderWeather(weather: ForecastWeatherDto?) {
        val data = weather!!.data

        temp_current?.text = weather!!.data[0].temp.roundToInt().toString()
        val windSpeed = (data[0].windSpd * 3.6).roundToInt().toString()
        wind?.text = "Вітер ${windSpeed} км/год, ${weather!!.data[0].windCdirFull}"
        tempByFeel?.text = "Відчувається як ${weather!!.data[0].appTemp.toInt()}°"
        cloudy?.text = weather!!.data[0].weather.description
        dayOfWeek?.text = getDayOfWeek()
        imageCloudy.let {
            Glide.with(this)
                .load("https://www.weatherbit.io/static/img/icons/${data[0].weather.icon}.png")
                .into(imageCloudy!!)
        }
    }

    private fun getDayOfWeek(): String {
        val time = Calendar.getInstance().time
        val formatter = SimpleDateFormat(" dd-MM-yyyy")
        return formatter.format(time)


    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = weatherAdapter
        weatherAdapter.notifyDataSetChanged()
        }

}
