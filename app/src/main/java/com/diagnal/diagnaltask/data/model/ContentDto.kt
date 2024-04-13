package com.diagnal.diagnaltask.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ContentDto (@SerializedName("content")
                         @Expose
                         var content: List<Content>? = null)