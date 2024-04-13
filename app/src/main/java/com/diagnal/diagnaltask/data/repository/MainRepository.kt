package com.diagnal.diagnaltask.data.repository

import android.content.Context
import com.diagnal.diagnaltask.DiagnalApplication
import com.diagnal.diagnaltask.base.BaseRepository
import com.diagnal.diagnaltask.data.model.MyResponse
import com.diagnal.diagnaltask.data.network.ApiInterface
import com.diagnal.diagnaltask.data.network.JsonLoader
import com.google.gson.Gson
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val apiInterface: ApiInterface) :
    BaseRepository() {

    suspend fun callApiLogin(pageNum: String) =
        safeApiCall {
            val context: Context = DiagnalApplication.getInstance().applicationContext
            val jsonLoader = JsonLoader(context) // 'this' is your context
            val json = jsonLoader.loadJsonFromAsset("page${pageNum}.json")

            val myResponse = Gson().fromJson(json,MyResponse::class.java)
            Response.success(myResponse)
        }
}