package domain.models
import com.google.gson.annotations.SerializedName

data class NewsDisplayingDTO(
    @SerializedName("articles")
    val newsComponents:ArrayList<NewsData>,
)

data class NewsData(
    @SerializedName("author")
    val author:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("urlToImage")
    val pic:String,
    @SerializedName("publishedAt")
    val newsDate: String
)