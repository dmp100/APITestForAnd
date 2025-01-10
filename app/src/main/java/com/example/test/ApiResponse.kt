data class ApiResponse(
    val response: Response
)

data class Response(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val items: Items,
    val numOfRows: Int,
    val pageNo: Int,
    val totalCount: Int
)

data class Items(
    val item: List<Item>
)

data class Item(
    val galContentId: String,
    val galContentTypeId: String,
    val galTitle: String,
    val galWebImageUrl: String,
    val galCreatedtime: String,
    val galModifiedtime: String,
    val galPhotographyMonth: String,
    val galPhotographyLocation: String,
    val galPhotographer: String,
    val galSearchKeyword: String
)