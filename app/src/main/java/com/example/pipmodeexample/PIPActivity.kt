package com.example.pipmodeexample

import android.app.PictureInPictureParams
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Rational
import android.view.View
import android.widget.ImageButton
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class PIPActivity : AppCompatActivity() {

    private var videoUri: Uri? = null

    private var pictureInPictureParamsBuilder: PictureInPictureParams.Builder? = null

    private var actionBar: androidx.appcompat.app.ActionBar? = null

    private lateinit var pipBtn: ImageButton

    private val tAG = "PIP_ACTIVITY"
    private lateinit var videoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pipactivity)

        actionBar = supportActionBar
        pipBtn = findViewById(R.id.pipBtn)
        videoView = findViewById(R.id.videoView)

        setVideo(intent)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInPictureParamsBuilder = PictureInPictureParams.Builder()
        }


        pipBtn.setOnClickListener {
            pictureInPictureMode()
        }
    }

    private fun setVideo(intent: Intent?) {
        val videoURL = intent!!.getStringExtra("videoUrl")
        Log.d(tAG, "setVideoView: $videoURL")

        //Media controller for Control Video
        val mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)

        videoUri = Uri.parse(videoURL)

        //set controls for video
        videoView.setMediaController(mediaController)

        //set Video URL to Video
        videoView.setVideoURI(videoUri)

        //add video prepare Listener
        videoView.setOnPreparedListener { mp ->
            Log.d(tAG, "setVideoView: Video Prepared For Playing..... ")
            mp.start()

        }
    }

    private fun pictureInPictureMode() {
        //require android O+
        Log.d(tAG, "pictureInPictureMode: Try to Enter in PIP mode")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(tAG, "pictureInPictureMode: Supports PIP mode")

            //setup PIP mode HEIGHT WIDTH

            val aspectRatio = Rational(videoView.width, videoView.height)
            pictureInPictureParamsBuilder!!.setAspectRatio(aspectRatio).build()
            enterPictureInPictureMode(pictureInPictureParamsBuilder!!.build())
        } else {
            Log.d(tAG, "pictureInPictureMode: Not Supports PIP mode")
            Toast.makeText(this, "Not Supports PIP mode", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        //when user click home button, if not in pip mode ,enter in pip mode , requires android N and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d(tAG, "onUserLeaveHint: was not in PIP")
            pictureInPictureMode()
        } else {
            Log.d(tAG, "onUserLeaveHint:Already in PIP mode")
        }
    }

    override fun onPictureInPictureModeChanged(
        isInPictureInPictureMode: Boolean,
        newConfig: Configuration
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
            if (isInPictureInPictureMode)
                Log.d(tAG, "onPictureInPictureModeChanged: Enter PIP")
            pipBtn.visibility = View.GONE
            actionBar!!.hide()
        } else {
            Log.d(tAG, "onPictureInPictureModeChanged: Exited from PIP")
            pipBtn.visibility = View.VISIBLE
            actionBar!!.show()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        //when 1st video playing and entered in pip mode ,clicked on 2nd video play 2nd video
        Log.d(tAG, "onNewIntent: Playing New Video")
        setVideo(intent)
    }

    override fun onStop() {
        super.onStop()
        if (videoView.isPlaying) {
            videoView.stopPlayback()
        }
    }
}