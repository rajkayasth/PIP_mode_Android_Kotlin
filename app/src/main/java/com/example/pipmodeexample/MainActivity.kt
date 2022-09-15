package com.example.pipmodeexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    lateinit var videoBtnOne: MaterialButton
    lateinit var videoBtnTwo: MaterialButton
    lateinit var videoBtnThree: MaterialButton

    private val videoOneUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
    private val videoTwoUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4"
    private val videoThreeUrl =
        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoBtnOne = findViewById(R.id.videoBtnOne)
        videoBtnTwo = findViewById(R.id.videoBtnTwo)
        videoBtnThree = findViewById(R.id.videoBtnThree)

        /**handle Click for First video Play*/
        videoBtnOne.setOnClickListener {
            playVideo(videoOneUrl)
        }
        /**handle Click for Second video Play*/
        videoBtnTwo.setOnClickListener {
            playVideo(videoTwoUrl)
        }
        /**handle Click for Three video Play*/
        videoBtnThree.setOnClickListener {
            playVideo(videoThreeUrl)
        }
    }

    private fun playVideo(url: String) {
        /**open PIP Activity To play Video with Url */
        val intent = Intent()
        intent.setClass(this, PIPActivity::class.java)
        intent.putExtra("videoUrl", url)
        startActivity(intent)

    }
}