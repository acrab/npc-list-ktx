package com.android.example.roomwordnavigation.testing

/**
 * This annotation allows us to open some classes for mocking purposes while they are final in
 * release builds.
 */
@Target(AnnotationTarget.CLASS)
annotation class OpenForTesting