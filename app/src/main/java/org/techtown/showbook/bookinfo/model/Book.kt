package org.techtown.showbook.bookinfo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    @SerializedName("itemId") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("description")val description:String,
    @SerializedName("author") val author:String,
    @SerializedName("priceSales") val price:String,
    @SerializedName("publisher") val publisher:String,
    @SerializedName("coverSmallUrl")val coverSmallUrl:String

    ): Parcelable