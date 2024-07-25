package com.bmti.gridlayout

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.VideoView
import android.net.Uri
import android.widget.Button


class HomeActivity : AppCompatActivity() {
    // Deklarasi Variable

    private lateinit var videoView: VideoView
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        videoView = findViewById(R.id.videoView)
        playButton = findViewById(R.id.play)

        val videoUri: Uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.video)
        videoView.setVideoURI(videoUri)
        playButton.setOnClickListener {
            videoView.start()
        }
    }
}