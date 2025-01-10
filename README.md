# APITestForAnd
# 관광공사 PhotoGallery API 프로젝트

## 📌 프로젝트 개요
한국관광공사의 PhotoGallery API를 활용하여 관광 사진들을 RecyclerView로 표시하는 안드로이드 앱입니다.

https://www.data.go.kr/data/15101914/openapi.do#/API%20%EB%AA%A9%EB%A1%9D/galleryList1

https://minchanyoun.tistory.com/44

https://jutole.tistory.com/9

https://velog.io/@ilil1/OkHttp-%EC%99%80-Retrofit

https://velog.io/@jiwon3378/Android-Glide-%EB%A1%9C-%EC%9D%B4%EB%AF%B8%EC%A7%80-Load%ED%95%98%EA%B8%B0

https://meongj-devlog.tistory.com/199

## 📝 구현 순서 및 방법

### 1. 레이아웃 구현 (XML)
#### 1.1 메인 레이아웃 (activity_main.xml)
```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout>
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"
        android:orientation="vertical"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

#### 1.2 아이템 레이아웃 (item.xml)
```xml
<androidx.constraintlayout.widget.ConstraintLayout>
    <ImageView
        android:id="@+id/imageView"
        android:layout_width="match_parent"
        android:layout_height="200dp"
        android:scaleType="centerCrop"/>
    <TextView
        android:id="@+id/textView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
</androidx.constraintlayout.widget.ConstraintLayout>
```

### 2. ItemAdapter 구현
```kotlin
class ItemAdapterAdapter(val profileList: List<Item>) : 
    RecyclerView.Adapter<ItemAdapterAdapter.Holder>() {
    
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = profileList[position]
        holder.title.text = item.galTitle
        
        // Glide로 이미지 로딩
        Glide.with(holder.itemView.context)
            .load(item.galWebImageUrl)
            .into(holder.image)
    }
}
```

### 3. API 모델 클래스 구현
```kotlin
// API 요청 모델
data class ApiRequest(
    val numOfRows: Int?,     // 한 페이지 결과 수
    val pageNo: Int?,        // 페이지 번호
    val MobileOS: String,    // OS 구분
    val MobileApp: String,   // 앱 이름
    val arrange: String?,    // 정렬 기준
    val _type: String?,      // 응답 형식
    val serviceKey: String   // 인증키
)

// API 응답 모델
data class ApiResponse(
    val response: Response
)

data class Item(
    val galContentId: String,
    val galTitle: String,
    val galWebImageUrl: String
)
```

### 4. Retrofit & OkHttp 설정
```kotlin
// OkHttp 클라이언트 설정
val client = OkHttpClient.Builder()
    .addInterceptor(logging)
    .connectTimeout(15, TimeUnit.SECONDS)
    .readTimeout(15, TimeUnit.SECONDS)
    .build()

// Retrofit 인터페이스
interface RetrofitService {
    @GET("galleryList1")
    fun getUser(
        @Query("pageNo") pageNo: Int,
        @Query("numOfRows") numOfRows: Int,
        @Query("MobileOS") MobileOS: String,
        @Query("MobileApp") MobileApp: String,
        @Query("arrange") arrange: String,
        @Query("_type") _type: String,
        @Query("serviceKey") serviceKey: String
    ): Call<ApiResponse>
}
```

### 5. MainActivity에서 구현
```kotlin
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // RecyclerView 설정
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        
        // API 호출 및 데이터 처리
        service.getUser(/*파라미터*/).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(...) {
                if(response.isSuccessful) {
                    val adapter = ItemAdapterAdapter(items)
                    recyclerView.adapter = adapter
                }
            }
        })
    }
}
```

## 🛠 필요한 라이브러리 설정

### build.gradle
```gradle
dependencies {
    // RecyclerView
    implementation("com.google.android.material:material:1.9.0")
    
    // Retrofit2 & OkHttp3
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    
    // Glide
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
}
```

### AndroidManifest.xml
```xml
<uses-permission android:name="android.permission.INTERNET"/>
<application
    android:networkSecurityConfig="@xml/network_security_config"
    ...>
```

## 📋 API 응답 형식
```json
{
  "response": {
    "header": {
      "resultCode": "0000",
      "resultMsg": "OK"
    },
    "body": {
      "items": {
        "item": [
          {
            "galContentId": "3463303",
            "galTitle": "해남 오시아노 관광단지",
            "galWebImageUrl": "http://tong.visitkorea.or.kr/..."
          }
        ]
      },
      "numOfRows": 10,
      "pageNo": 1,
      "totalCount": 5794
    }
  }
}
```

## ⚠️ 주의사항
1. HTTP 통신을 위한 네트워크 보안 설정 필수
2. Glide 이미지 로딩 시 메모리 관리 고려
3. API 키는 보안상 별도 관리 필요
4. 응답 데이터의 null 처리 구현

## 🔄 에러 코드
- 10: 잘못된 요청 파라미터
- 11: 필수 요청 파라미터 누락
- 21: 서비스 키 일시적 사용 불가
- 33: 서명되지 않은 호출
- 200: 성공

## 🚀 향후 개선사항
1. MVVM 패턴 적용
2. 페이징 처리
3. 이미지 캐싱 최적화
4. 에러 처리 강화
5. UI/UX 개선
