package com.example.test

import ApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitService {

    //GET 예제
    @GET("galleryList1")
    fun getUser(

        @Query("pageNo") pageNo : Int,
        @Query("numOfRows") numOfRows : Int,
        @Query("MobileOS") MobileOS : String,
        @Query("MobileApp") MobileApp : String,
        @Query("arrange") arrange : String,
        @Query("_type") _type : String,
        @Query("serviceKey") serviceKey : String

    ): Call<ApiResponse>
}