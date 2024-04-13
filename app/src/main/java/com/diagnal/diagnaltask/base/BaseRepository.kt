package com.diagnal.diagnaltask.base

import com.diagnal.diagnaltask.data.network.Resource
import com.diagnal.diagnaltask.utils.logE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class BaseRepository {
    suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response = call.invoke()
                if (response.code() == 200 || response.code() == 202) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            return@withContext Resource.success(body)
                        }
                        return@withContext Resource.error("Unable to get data BODY : NULL")
                    }
                    return@withContext Resource.error(" ${response.code()} ${response.message()}")
                }
                return@withContext Resource.error(response.message(),response.body())
            } catch (e: Exception) {
                logE("BR Error: ",e.message)
                Resource.error("BR Error: " + e.message)
            }
        }
    }


}