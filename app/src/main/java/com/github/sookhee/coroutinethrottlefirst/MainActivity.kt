package com.github.sookhee.coroutinethrottlefirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val text = findViewById<TextView>(R.id.tvCount)
        val button = findViewById<Button>(R.id.btnThrottleFirst)
        button.setClickEvent(lifecycleScope) {
            Log.i("[TAG]", "setClickEvent: ${System.currentTimeMillis()}")
        }
    }

    companion object {
        const val THROTTLE_DURATION = 1000L
    }
}
