package dev.owuor91.selfie

import android.app.Application
import android.content.Context

class SelfieApp : Application() {
  companion object {
    lateinit var appContext: Context
  }
  
  override fun onCreate() {
    super.onCreate()
    appContext = applicationContext
  }
}