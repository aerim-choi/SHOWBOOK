package org.techtown.showbook.bookinfo.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book (
    @SerializedName("itemId") var id: Long,
    @SerializedName("title") var title: String,
    @SerializedName("description")var description:String,
    @SerializedName("author") var author:String,
    @SerializedName("priceSales") var price:String,
    @SerializedName("publisher") var publisher:String,
    @SerializedName("coverSmallUrl")var coverSmallUrl:String,
    @SerializedName("coverLargeUrl")var coverLargeUrl:String
    ): Parcelable