package com.example.weather_app

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weather_app.api.CurrentWeatherInterface
import com.example.weather_app.api.ForecastWeatherInterface
import com.example.weather_app.const.BASE_URL
import com.example.weather_app.model.Data
import com.example.weather_app.model.ForecastWeatherDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel:ViewModel() {

    private val _currentWeather = MutableLiveData<ForecastWeatherDto>()
    val currentWeather:LiveData<ForecastWeatherDto> = _currentWeather

    private val _forecastWeather = MutableLiveData<ForecastWeatherDto>()
    val forecastWeather:LiveData<ForecastWeatherDto> = _forecastWeather

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    init {
        getCurrentWeather()
        getForecastWeather()
    }

    private fun getCurrentWeather() {
        val currentWeatherService = retrofit.create(CurrentWeatherInterface::class.java)

        currentWeatherService.getCurrentWeather(lat = 49.4459142f,lon = 32.0594088f)
            .enqueue(object :Callback<ForecastWeatherDto>{
                override fun onResponse(
                    call: Call<ForecastWeatherDto>,
                    response: Response<ForecastWeatherDto>
                ) {
                    _currentWeather.value = response.body()
                }

                override fun onFailure(call: Call<ForecastWeatherDto>, t: Throwable) {
                    println(t.localizedMessage)
                }

            })
    }

    private fun getForecastWeather(){
        val forecastWeatherService = retrofit.create(ForecastWeatherInterface::class.java)
        forecastWeatherService.getForecastWeather(lat = 49.4459142f,lon = 32.0594088f)
            .enqueue(object :Callback<ForecastWeatherDto>{
                override fun onResponse(call: Call<ForecastWeatherDto>, response: Response<ForecastWeatherDto>) {
                    _forecastWeather.postValue(response.body())
                }

                override fun onFailure(call: Call<ForecastWeatherDto>, t: Throwable) {
                    println(t.localizedMessage)
                }

            })
    }


}