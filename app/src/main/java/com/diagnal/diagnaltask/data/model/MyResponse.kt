package com.diagnal.diagnaltask.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MyResponse(
    @SerializedName("page") @Expose
    var pageDto: PageDto
)