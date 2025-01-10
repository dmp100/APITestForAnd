package com.example.test

data class ApiRequest(
    // 한페이지결과수
    val numOfRows: Int?,

    // 페이지번호
    val pageNo: Int?,

    // OS 구분 (required)
    // IOS (아이폰), AND (안드로이드), WIN (윈도우폰), ETC(기타)
    val MobileOS: String,

    // 서비스명(어플명) (required)
    val MobileApp: String,

    // 정렬구분: A=촬영일, B=제목, C=수정일
    val arrange: String?,

    // 응답메세지 형식 : REST방식의 URL호출 시 json값 추가(디폴트 응답메세지 형식은XML)
    val _type: String?,

    // 인증키(서비스키) (required)
    val serviceKey: String
)
