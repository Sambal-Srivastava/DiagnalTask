package com.diagnal.diagnaltask.data.model

import android.text.SpannableString
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Content(
    @SerializedName("name")
    @Expose
    var name: String? = null,

    @SerializedName("poster-image")
    @Expose
    var posterImage: String? = null,

//    @Expose
//    var highlightedName: SpannableString? = null // New member for storing highlighted name
)