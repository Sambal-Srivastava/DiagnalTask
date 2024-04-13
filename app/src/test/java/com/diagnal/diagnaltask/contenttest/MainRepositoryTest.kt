package com.diagnal.diagnaltask.contenttest

import android.content.Context
import com.diagnal.diagnaltask.data.model.MyResponse
import com.diagnal.diagnaltask.data.network.ApiInterface
import com.diagnal.diagnaltask.data.network.JsonLoader
import com.diagnal.diagnaltask.data.repository.MainRepository
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response
import java.io.InputStream

class MainRepositoryTest {

    @Mock
    private lateinit var mockApiInterface: ApiInterface

    @Mock
    private lateinit var mockContext: Context

    private lateinit var repository: MainRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        repository = MainRepository(mockApiInterface)

        // Stub the behavior of the Context object
        `when`(mockContext.assets.open(anyString())).thenReturn(Mockito.mock(InputStream::class.java))
    }

    @Test
    fun `test callApiLogin`() {
        // Mock the JSON data loading
//        val json = "{ \"page\": { \"title\": \"Test Title\" } }"
        val jsonLoader = JsonLoader(mockContext)
        val json = jsonLoader.loadJsonFromAsset("page1.json")

        // Mock the MainResponse object
//        val expectedResponse = MainResponse(Page(title = "Test Title"))
        val expectedResponse = Gson().fromJson(json, MyResponse::class.java)

        // Mock the API call to return Response<MainResponse>
        val response = Response.success(expectedResponse)
        runBlocking {
            `when`(mockApiInterface.callApiLogin("1")).thenReturn(response)
        }

        // Call the repository method
        val actualResponse = runBlocking { repository.callApiLogin("1") }

        // Verify the result
        assertEquals(response, actualResponse)
    }
}












