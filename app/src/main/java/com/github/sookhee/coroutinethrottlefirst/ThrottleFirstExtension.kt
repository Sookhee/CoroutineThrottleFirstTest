package com.github.sookhee.coroutinethrottlefirst

import android.util.Log
import android.view.View
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow


/**
 *  ThrottleFirstExtension.kt
 *
 *  Created by Minji Jeong on 2022/02/28
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

@FlowPreview
@ExperimentalCoroutinesApi
fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        Log.i("[TAG]", "click, but ignore")
        this.trySend(Unit).isSuccess
    }
    awaitClose { setOnClickListener(null) }
}

@FlowPreview
@ExperimentalCoroutinesApi
fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastEmissionTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastEmissionTime > windowDuration
        if (mayEmit)
        {
            lastEmissionTime = currentTime
            emit(upstream)
        }
    }
}
