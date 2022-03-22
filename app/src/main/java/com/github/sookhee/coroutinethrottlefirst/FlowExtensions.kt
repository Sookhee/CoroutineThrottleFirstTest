package com.github.sookhee.coroutinethrottlefirst

import android.view.View
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow

/**
 *  FlowExtensions.kt
 *
 *  Created by Minji Jeong on 2022/03/22
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

fun View.clicks(): Flow<Unit> = callbackFlow {
    setOnClickListener {
        this.trySend(Unit)
    }
    awaitClose { setOnClickListener(null) }
}

fun <T> Flow<T>.throttleFirst(windowDuration: Long): Flow<T> = flow {
    var lastTime = 0L
    collect { upstream ->
        val currentTime = System.currentTimeMillis()
        val mayEmit = currentTime - lastTime > windowDuration
        if (mayEmit) {
            lastTime = currentTime
            emit(upstream)
        }
    }
}
