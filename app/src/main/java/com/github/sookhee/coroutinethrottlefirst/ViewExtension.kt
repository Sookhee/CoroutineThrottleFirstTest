package com.github.sookhee.coroutinethrottlefirst

import android.view.View
import com.github.sookhee.coroutinethrottlefirst.MainActivity.Companion.THROTTLE_DURATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

/**
 *  ViewExtension.kt
 *
 *  Created by Minji Jeong on 2022/02/28
 *  Copyright © 2022 Shinhan Bank. All rights reserved.
 */

fun View.setClickEvent(
    uiScope: CoroutineScope,
    windowDuration: Long = THROTTLE_DURATION,
    onClick: () -> Unit,
) {
    clicks()
        .throttleFirst(windowDuration)
        .onEach { onClick.invoke() }
        .launchIn(uiScope)
}
