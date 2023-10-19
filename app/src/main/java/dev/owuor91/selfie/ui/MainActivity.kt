package dev.owuor91.selfie.ui

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia.SingleMimeType
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import dev.owuor91.selfie.BuildConfig
import dev.owuor91.selfie.databinding.ActivityMainBinding
import dev.owuor91.selfie.viewmodel.SelfieViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
  
  lateinit var binding: ActivityMainBinding
  val storage = Firebase.storage
  lateinit var pickVideo: ActivityResultLauncher<PickVisualMediaRequest>
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    
    
    pickVideo = registerForActivityResult(PickVisualMedia()){uri->
      uploadVideoToFirebase(uri!!)
    }
  }
  
  override fun onResume() {
    super.onResume()
    
    binding.btnVidUpload.setOnClickListener {
      val mimeType = "video/*"
      pickVideo.launch(PickVisualMediaRequest(SingleMimeType(mimeType)))
    }
  }
  
  
  fun uploadVideoToFirebase(uri: Uri){
    val storageRef = storage.reference
    val videosRef = storageRef.child("videos")
    val newVideoRef = videosRef.child(uri.lastPathSegment!!)
    val uploadTask = newVideoRef.putFile(uri)
  
    val urlTask = uploadTask.continueWithTask { task ->
      if (!task.isSuccessful) {
        task.exception?.let {
          Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
        }
      }
      videosRef.downloadUrl
    }.addOnCompleteListener { task ->
      if (task.isSuccessful) {
        val downloadUri = task.result
        Toast.makeText(this, downloadUri.toString(), Toast.LENGTH_LONG).show()
      } else {
        Toast.makeText(this, task.exception.toString(), Toast.LENGTH_LONG).show()
      }
    }
    
  }
  
  
}