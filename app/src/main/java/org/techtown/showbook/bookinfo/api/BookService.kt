package org.techtown.showbook.bookinfo.api;

import org.techtown.showbook.bookinfo.model.BestSellerDto
import org.techtown.showbook.bookinfo.model.SearchBookDto
import retrofit2.Call
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BookService {
    @GET("/api/search.api?output=json")
    fun getBooksByName(
        @Query("key") apiKey:String,
        @Query("query") keyword:String
    ):Call<SearchBookDto>

    @GET("/api/bestSeller.api?output=json&categoryId=125")
    fun getBestSellerBooks(
        @Query("key") apiKey:String
    ): Call<BestSellerDto>
}
