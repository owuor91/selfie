package dev.owuor91.selfie.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.owuor91.selfie.repository.SelfieRepository
import kotlinx.coroutines.launch
import java.io.File

class SelfieViewModel : ViewModel() {
  val selfieRepo = SelfieRepository()
  
  fun postSelfie(uri: Uri, caption: String) {
    viewModelScope.launch {
      selfieRepo.postSelfie(uri, caption)
    }
  }
}