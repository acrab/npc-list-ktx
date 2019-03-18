package com.example.android.roomwordnavigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

/**
 * A utility function that blocks until a value is observed on the given LiveData instance, then returns that value.
 * Note, this will only
 */
@Throws(InterruptedException::class)
fun <T> LiveData<T>.observedValue(): T? {
    var data: T? = null
    val latch = CountDownLatch(1)

    this.observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            removeObserver(this)
        }
    })

    latch.await(1, TimeUnit.SECONDS)

    return data
}

fun <T> LiveData<T>.afterObserve(execute: (T?) -> Unit) {
    val latch = CountDownLatch(1)
    var data: T? = null

    this.observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            data = t
            latch.countDown()
            removeObserver(this)
        }
    })

    latch.await(1, TimeUnit.SECONDS)

    execute(data)
}