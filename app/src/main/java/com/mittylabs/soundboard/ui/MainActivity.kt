package com.mittylabs.soundboard.ui

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.storage.StorageReference
import com.mittylabs.soundboard.ColorUtil
import com.mittylabs.soundboard.MainViewModel
import com.mittylabs.soundboard.ui.theme.SoundboardTheme
import androidx.compose.ui.graphics.Color as ComposeColor

@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent { SoundboardTheme { Board() } }
    }

    @Composable
    fun Board(viewModel: MainViewModel = viewModel()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background,
        ) {
            SoundList(viewModel.sounds)
        }
    }

    @Composable
    fun SoundList(sounds: SnapshotStateList<StorageReference>) {
        LazyVerticalGrid(
            cells = GridCells.Adaptive(120.dp),
            contentPadding = PaddingValues(8.dp),
        ) {
            items(sounds) { sound ->
                SoundCard(sound)
            }
        }
    }

    @Composable
    fun SoundCard(sound: StorageReference, viewModel: MainViewModel = viewModel()) {
        val player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            viewModel.preparePlayer(this, sound)
        }

        Card(
            modifier = Modifier
                .padding(4.dp)
                .aspectRatio(1f),
            backgroundColor = ComposeColor(ColorUtil.generateRandomColor()),
            onClick = { player.start() }
        ) {
            Text(
                text = sound.name
                    .replace("_", " ")
                    .replace(".mp3", ""),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentHeight(),
            )
        }
    }
}
