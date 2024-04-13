package com.diagnal.diagnaltask.contenttest

import android.content.Context
import com.diagnal.diagnaltask.data.model.Content
import com.diagnal.diagnaltask.views.adapters.ContentAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ContentAdapterTest {


    @Mock
    private lateinit var mockContext: Context

    private lateinit var adapter: ContentAdapter

    @Before
    fun setUp() {
        // Initialize the adapter with an empty list
        MockitoAnnotations.initMocks(this)
        adapter = ContentAdapter(ArrayList(), mockContext) {}
    }

    @Test
    fun `test initial state`() {
        // Verify that the adapter has initially no items
        assertEquals(0, adapter.itemCount)
    }

    @Test
    fun `test updateList`() {
        // Create a new list of content items
        val newList = ArrayList<Content>().apply {
            add(Content("1", "Item 1"))
            add(Content("2", "Item 2"))
        }

        // Call the updateList method with the new list
        adapter.updateList(newList, search = false, false)

        // Verify that the adapter contains the correct number of items after the update
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun `test filter`() {
        // Create a list of content items
        val newList = ArrayList<Content>().apply {
            add(Content("Family post", "Item 1"))
            add(Content("MadMax", "Item 2"))
            add(Content("Avengers", "Item 3"))
        }

        // Call the updateList method with the list
        adapter.updateList(newList, search = false, notify = false)

        // Filter the adapter
        adapter.filter("Fami")

        // Verify that the adapter contains the correct number of items after filtering
        assertEquals(1, adapter.itemCount)
    }
}

