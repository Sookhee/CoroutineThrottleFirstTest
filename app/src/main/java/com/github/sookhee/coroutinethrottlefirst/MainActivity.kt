package com.github.sookhee.coroutinethrottlefirst

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainActivity : AppCompatActivity() {
    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button1 = findViewById<Button>(R.id.btnTest1)
        val button2 = findViewById<Button>(R.id.btnTest2)
        val button3 = findViewById<Button>(R.id.btnTest3)

        button1.onClick {
            Log.i("[TAG]", "CLICK")
            delay(500) // wait half a second
        }

        button2.clicks().throttleFirst(1000)
            .onEach { Log.i("[TAG]", "throttleFirst") }
            .launchIn(GlobalScope)

        button3.setClicks(5000) {
            Log.i("[TAG]", "setClicks: ${System.currentTimeMillis()}")
        }
    }

    private fun View.onClick(action: suspend (View) -> Unit) {
        // launch one actor
        val event = GlobalScope.actor<View>(Dispatchers.Main) {
            for (event in channel) action(event)
        }

        setOnClickListener {
            event.trySend(it).isSuccess
        }
    }

    companion object {
        const val THROTTLE_DURATION = 1000L
    }
}
