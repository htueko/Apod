package com.htueko.apod.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Apod(
    val copyright: String,
    val date: String,
    val explanation: String,
    @SerializedName("hdurl")
    val hdUrl: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("service_version")
    val serviceVersion: String,
    val title: String,
    val url: String
) : Parcelable