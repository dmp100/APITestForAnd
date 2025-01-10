package com.example.test

import ApiResponse
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


//MainActivity.kt
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView) //리사이클러뷰 어덥터 연결 - 레트로핏보다 먼저 받아야함
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        // OkHttpClient 설정
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apis.data.go.kr/B551011/PhotoGalleryService1/")
            .client(client)  // OkHttpClient 설정 추가
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        val service = retrofit.create(RetrofitService::class.java);

        service.getUser(1, 10, "AND", "app", "A","json","KVLNantZhhbecolXsyBcwYgdnmPDo0poEXjIFRJLMG4adbgyavxDN9aKnwgKfeRsvG46veAKSktHS1e8mI/yKQ==" )?.enqueue(object : Callback<ApiResponse> {

            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if(response.isSuccessful) {
                    val result = response.body()
                    Log.d("YMC", "Response: $result")

                    result?.response?.body?.items?.item?.let { items ->
                        Log.d("YMC", "Items size: ${items.size}")
                        val adapter = ItemAdapterAdapter(items)
                        recyclerView.adapter = adapter
                    } ?: run {
                        Log.d("YMC", "No items found")
                    }
                } else {
                    Log.d("YMC", "Response not successful: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("YMC", "onFailure 에러: " + t.message.toString())
                Log.d("YMC", "Failed URL: " + call.request().url)
            }
        })



    }

}