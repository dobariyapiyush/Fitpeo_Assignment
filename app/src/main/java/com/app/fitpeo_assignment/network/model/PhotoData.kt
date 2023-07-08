package com.app.fitpeo_assignment.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PhotoData(

    @SerializedName("albumId")
    @Expose
    var albumId: Int,

    @SerializedName("id")
    @Expose
    var id: Int,

    @SerializedName("title")
    @Expose
    var title: String,

    @SerializedName("url")
    @Expose
    var url: String,

    @SerializedName("thumbnailUrl")
    @Expose
    var thumbnailUrl: String
)
