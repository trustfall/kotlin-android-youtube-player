package com.example.trustfall.youtubeplayer

import android.content.Intent
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "GEw0ePZUMHA"
const val YOUTUBE_PLAYLIST = "PLk1Sqn_f33KuU_aJDvMPPAy_SoxXTt_ub"


class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val TAG = "YoutubeActivity"
    private val DIALOG_REQUEST_CODE = 1
    val playerView by lazy { YouTubePlayerView(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // cast from view to constraint layout
        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

//        val button1 = Button(this)
//        button1.layoutParams = ConstraintLayout.LayoutParams(600, 180)
//        button1.text = "Button added"
//        layout.addView(button1)


        playerView.layoutParams = ConstraintLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)

    }


    override fun onInitializationSuccess(
        provider: YouTubePlayer.Provider?,
        youtubePlayer: YouTubePlayer?, wasRestored: Boolean
    ) {
        Log.d(TAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(TAG, "onInitializationSuccess: youtubePlayer is ${youtubePlayer?.javaClass}")
        Toast.makeText(
            this,
            "Initialized Youtube player successfully.", Toast.LENGTH_SHORT
        ).show()

        youtubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youtubePlayer?.setPlaybackEventListener(playbackEventListener)

        if (!wasRestored) {
            youtubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            youtubePlayer?.play()
        }
    }

    override fun onInitializationFailure(
        provider: YouTubePlayer.Provider?,
        youtubeInitResult: YouTubeInitializationResult?
    ) {
        if (youtubeInitResult?.isUserRecoverableError == true) {
            youtubeInitResult.getErrorDialog(this, DIALOG_REQUEST_CODE).show()
        } else {
            val errorMessage = "There was an error initializing the YouTube player ($youtubeInitResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
        }
    }

    private val playbackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(
                this@YoutubeActivity,
                "Good, video is playing OK", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onStopped() {
            Toast.makeText(
                this@YoutubeActivity,
                "Video has stopped!", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onPaused() {
            Toast.makeText(
                this@YoutubeActivity,
                "Video has paused", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(
                this@YoutubeActivity,
                "Click ad now, make the video creator rich!", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(
                this@YoutubeActivity,
                "Video has started!", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(
                this@YoutubeActivity,
                "Video has ended!", Toast.LENGTH_SHORT
            ).show()
        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d(TAG, "onActivityResult called with response code $resultCode for request$requestCode")
        if (requestCode == DIALOG_REQUEST_CODE) {
            Log.d(TAG, intent?.toString())
            Log.d(TAG, intent?.extras.toString())
            playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
        }
    }
}
