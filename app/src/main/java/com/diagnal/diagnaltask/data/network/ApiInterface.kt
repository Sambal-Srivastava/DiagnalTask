package com.diagnal.diagnaltask.data.network

import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {

    @GET("page{no}.json")
    suspend fun callApiLogin(@Path("no") no: String): Response<com.diagnal.diagnaltask.data.model.MyResponse>

}