package com.diagnal.diagnaltask.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PageDto(
    @SerializedName("title")
    @Expose
    var title: String? = null,

    @SerializedName("total-content-items")
    @Expose
    var totalContentItems: String? = null,

    @SerializedName("page-num")
    @Expose
    var pageNum: String? = null,

    @SerializedName("page-size")
    @Expose
    var pageSize: String? = null,

    @SerializedName("content-items")
    @Expose
    var contentDto: ContentDto? = null,
)