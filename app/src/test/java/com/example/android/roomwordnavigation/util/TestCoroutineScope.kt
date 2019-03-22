package com.example.android.roomwordnavigation.util

import kotlinx.coroutines.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
fun testCoroutineScope() = CoroutineScope(Job() + Dispatchers.Unconfined)

class TestCoroutineScopeRule : TestWatcher(){

    lateinit var scope: CoroutineScope

    @ExperimentalCoroutinesApi
    override fun starting(description: Description?) {
        super.starting(description)
        scope = testCoroutineScope()
    }

    @ExperimentalCoroutinesApi
    override fun finished(description: Description?) {
        super.finished(description)
        scope.cancel()
    }
}