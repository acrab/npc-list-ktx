package com.example.android.roomwordnavigation.util

import androidx.lifecycle.LiveData
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@Throws(InterruptedException::class)
fun <T> LiveData<T>.observedValue(): T? {
    var data : T? = null
    val latch = CountDownLatch(1)

    this.observeForever{
        data = it
        latch.countDown()
    }

    latch.await(1, TimeUnit.SECONDS)

    return data
}

fun <T> LiveData<T>.afterObserve(execute:(T?)->Unit) {
    val latch = CountDownLatch(1)
    var data : T? = null

    this.observeForever{
        data = it
        latch.countDown()
    }

    latch.await(1, TimeUnit.SECONDS)

    execute(data)
}