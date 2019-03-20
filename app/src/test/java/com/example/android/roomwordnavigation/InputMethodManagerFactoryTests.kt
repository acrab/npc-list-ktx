@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation

import android.content.Context
import android.view.inputmethod.InputMethodManager
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(InputMethodManagerFactoryTests.When_The_Input_Manager_Is_Requested::class)
class InputMethodManagerFactoryTests {
    class When_The_Input_Manager_Is_Requested {

        private lateinit var subject: InputMethodManagerFactory
        private lateinit var context: Context
        private lateinit var service: InputMethodManager
        @Before
        fun setup() {
            subject = InputMethodManagerFactory()
            service = mock()
            context = mock {
                on { getSystemService(Context.INPUT_METHOD_SERVICE) } doReturn service
            }
        }

        @Test
        fun It_Will_Be_Requested_From_The_Context() {
            subject.get(context)

            verify(context).getSystemService(Context.INPUT_METHOD_SERVICE)
        }

        @Test
        fun It_Will_Be_Returned() {
            val x = subject.get(context)
            assertEquals(service, x)
        }
    }
}