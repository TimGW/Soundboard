package com.mittylabs.soundboard

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    private val storage = FirebaseStorage.getInstance()
    val sounds = mutableStateListOf<Sound>()

    init {
        fetchBoard()
    }

    private fun fetchBoard() {
        viewModelScope.launch {
            val listAll = storage.reference.listAll().await()
            sounds.clear()

            listAll.items.forEach {
                sounds.add(withContext(Dispatchers.IO) {
                    Sound(
                        name = it.name
                            .replace("_", " ")
                            .replace(".mp3", ""),
                        uri = it.downloadUrl.await()
                    )
                })
            }
        }
    }
}