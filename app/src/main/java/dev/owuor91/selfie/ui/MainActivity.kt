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
import dev.owuor91.selfie.BuildConfig
import dev.owuor91.selfie.databinding.ActivityMainBinding
import dev.owuor91.selfie.viewmodel.SelfieViewModel
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : AppCompatActivity() {
  val selfieViewModel: SelfieViewModel by viewModels()
  lateinit var binding: ActivityMainBinding
  lateinit var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>
  
  
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    pickMedia = registerForActivityResult(PickVisualMedia()){uri->
      if(uri != null){
        selfieViewModel.postSelfie(uri, "Watagwan")
      }
    }
  }
  
  override fun onResume() {
    super.onResume()
    binding.tvHello.setOnClickListener {
      val mimeType = "image/jpeg"
      pickMedia.launch(PickVisualMediaRequest(SingleMimeType(mimeType)))
    }
  }
  
  
  
}