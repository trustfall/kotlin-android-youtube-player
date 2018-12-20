package com.example.trustfall.youtubeplayer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPlaySingle.setOnClickListener(this)
        btnStandAlone.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val intent = when (v?.id) {
            // :: <- pass a reference of a class (class literal)
            R.id.btnPlaySingle -> Intent(this, YoutubeActivity::class.java)
            R.id.btnStandAlone -> Intent(this, StandaloneActivity::class.java)
            else -> throw IllegalArgumentException("Undefined button clicked.")
        }

        startActivity(intent)
    }
}
