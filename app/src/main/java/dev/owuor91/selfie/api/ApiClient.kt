package dev.owuor91.selfie.api

import okhttp3.OkHttp
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit.SECONDS

object ApiClient {
  val okHttpClient = OkHttpClient.Builder()
    .connectTimeout(30, SECONDS)
    .writeTimeout(60, SECONDS)
    .readTimeout(30, SECONDS)
    .build()
  
  var retrofit = Retrofit.Builder()
    .baseUrl("https://d180-41-80-112-58.ngrok-free.app")
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .build()
  
  fun <T> buildClient(apiInterface: Class<T>): T{
    return retrofit.create(apiInterface)
  }
}