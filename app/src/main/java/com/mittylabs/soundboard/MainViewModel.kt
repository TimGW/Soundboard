package com.mittylabs.soundboard

import android.media.MediaPlayer
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainViewModel : ViewModel() {
    val sounds = mutableStateListOf<StorageReference>()
    private val storage = FirebaseStorage.getInstance()

    init {
        fetchBoard()
    }

    private fun fetchBoard() {
        viewModelScope.launch {
            sounds.clear()
            sounds.addAll(storage.reference.listAll().await().items)
        }
    }

    fun preparePlayer(player: MediaPlayer, sound: StorageReference) {
        viewModelScope.launch(Dispatchers.IO) {
            player.setDataSource(sound.downloadUrl.await().toString())
            player.prepareAsync()
        }
    }
}
