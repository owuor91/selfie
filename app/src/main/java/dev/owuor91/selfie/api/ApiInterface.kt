package dev.owuor91.selfie.api

import dev.owuor91.selfie.models.SelfieResponse
import okhttp3.MultipartBody
import retrofit2.Response

import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiInterface {
  
  @Multipart
  @POST("/selfie")
  suspend fun postSelfie(
    @Part image: MultipartBody.Part,
    @Part caption: MultipartBody.Part,
    @Part id: MultipartBody.Part
  ): Response<SelfieResponse>
}