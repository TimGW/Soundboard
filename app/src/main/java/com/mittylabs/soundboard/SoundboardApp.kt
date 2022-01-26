package com.mittylabs.soundboard

import android.app.Application
import com.google.firebase.FirebaseApp

class SoundboardApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
    }
}
