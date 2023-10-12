package com.uco.stlapp.models

import com.google.gson.annotations.SerializedName

data class ArticleResponse(
    @SerializedName("id") var id: Int,
    @SerializedName("ref") var ref: String = "",
    @SerializedName("name") var name: String = "",
    @SerializedName("status") var status: String = "",
    @SerializedName("quantity") var quantity: Int,
    @SerializedName("photo") var photo: String?,
)