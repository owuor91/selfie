package dev.owuor91.selfie.repository

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import dev.owuor91.selfie.SelfieApp
import dev.owuor91.selfie.api.ApiClient
import dev.owuor91.selfie.api.ApiInterface
import dev.owuor91.selfie.models.SelfieResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

class SelfieRepository {
  private val apiInterface = ApiClient.buildClient(ApiInterface::class.java)
  
  suspend fun postSelfie(uri: Uri, caption: String): Response<SelfieResponse> {
    return withContext(Dispatchers.IO) {
      val imageFile = getFileFromUri(uri, SelfieApp.appContext)
      val imgRequestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), imageFile)
      
      val captionReq = MultipartBody.Part.createFormData("caption", caption)
      val imgReq = MultipartBody.Part.createFormData("image", imageFile.name, imgRequestBody)
      val id = MultipartBody.Part.createFormData("id", UUID.randomUUID().toString())
      
      apiInterface.postSelfie(imgReq, captionReq, id)
    }
  }
  
  fun getFileFromUri(uri: Uri, context: Context): File {
    val inputStream = context.contentResolver.openInputStream(uri)
    val file = File(context.filesDir, queryName(context.contentResolver, uri))
    val outPutStream = FileOutputStream(file)
    inputStream!!.copyTo(outPutStream)
    inputStream.close()
    outPutStream.close()
    return File(file.path)
  }
  
  private fun queryName(resolver: ContentResolver, uri: Uri): String {
    val cursor = resolver.query(uri, null, null, null, null)!!
    val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
    cursor.moveToFirst()
    val name = cursor.getString(nameIndex)
    cursor.close()
    return name
  }
  
}