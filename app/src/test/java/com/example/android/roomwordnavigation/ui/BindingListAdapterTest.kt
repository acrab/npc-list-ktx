@file:Suppress("ClassName", "TestFunctionName")

package com.example.android.roomwordnavigation.ui

import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(BindingListAdapterTest.When_Data_Is_Provided::class)
class BindingListAdapterTest {
    class When_Data_Is_Provided {
        @Test
        fun It_Is_Passed_To_The_Binding() {

            val mockAdapter = mock<RecyclerView.Adapter<RecyclerView.ViewHolder>>(extraInterfaces = arrayOf(BindingListAdapter::class))

            @Suppress("UNCHECKED_CAST") //Works fine...
            val mockBindingAdapter = mockAdapter as BindingListAdapter<Any>

            val mockRecycler = mock<RecyclerView> {
                on { adapter } doReturn mockAdapter
            }

            val data = mock<List<Any>>()
            setData(mockRecycler, data)

            verify(mockBindingAdapter).submitList(data)
        }
    }
}