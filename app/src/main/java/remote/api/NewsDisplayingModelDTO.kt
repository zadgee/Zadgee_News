package remote.api

import android.icu.text.SimpleDateFormat
import com.google.gson.annotations.SerializedName

data class NewsDisplayingModelDTO(
    @SerializedName("articles")
    val newsComponents:ArrayList<NewsDataDTO>,
)

data class NewsDataDTO(
    @SerializedName("author")
    val author:String,
    @SerializedName("title")
    val title:String,
    @SerializedName("urlToImage")
    val pic:String,
    @SerializedName("publishedAt")
    val newsDate: SimpleDateFormat
)