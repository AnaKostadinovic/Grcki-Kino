package com.anak.grckikino.models

import com.google.gson.annotations.SerializedName

data class ResultData(
    @SerializedName("content") val content: List<GameData>,
    @SerializedName("totalPages") val totalPages: Int,
    @SerializedName("totalElements") val totalElements: Int,
    @SerializedName("last") val isLast: Boolean,
    @SerializedName("numberOfElements") val numberOfElements: Int,
    @SerializedName("sort") val sort: List<Sort>,
    @SerializedName("first") val isFirst: Boolean,
    @SerializedName("size") val size: Int,
    @SerializedName("number") val number: Int
)

data class Sort(
    @SerializedName("direction") val direction: String,
    @SerializedName("property") val property: String,
    @SerializedName("ignoreCase") val ignoreCase: Boolean,
    @SerializedName("nullHandling") val nullHandling: String,
    @SerializedName("descending") val descending: Boolean,
    @SerializedName("ascending") val ascending: Boolean
)
