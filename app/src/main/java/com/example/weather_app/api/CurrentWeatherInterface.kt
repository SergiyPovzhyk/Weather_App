package com.example.weather_app.api

import com.example.weather_app.const.API_KEY
import com.example.weather_app.model.Data
import com.example.weather_app.model.ForecastWeatherDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrentWeatherInterface {

    @Headers (API_KEY)
    @GET("current?lang=uk")

    fun getCurrentWeather(
        @Query ("lat") lat:Float,
        @Query ("lon") lon:Float
    ) :Call<ForecastWeatherDto>
}

interface ForecastWeatherInterface{

    @Headers(API_KEY)
    @GET("forecast/hourly?lang=uk&hour=96")

    fun getForecastWeather(
        @Query("lat") lat:Float,
        @Query("lon") lon:Float,
    ) :Call<ForecastWeatherDto>
}