package com.kira.videoplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    VideoPlayerScreen()
                }
            }
        }
    }
}

@Composable
fun VideoPlayerScreen() {
    val context = androidx.compose.ui.platform.LocalContext.current

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            // Replace this URL with your own IPTV stream link
            val videoUri = Uri.parse("https://example.com/live/playlist.m3u8")
            setMediaItem(MediaItem.fromUri(videoUri))
            prepare()
        }
    }

    DisposableEffect(Unit) {
        onDispose { exoPlayer.release() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Developer: kira.art2003",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}
