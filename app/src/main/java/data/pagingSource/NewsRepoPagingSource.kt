package data.pagingSource
import androidx.paging.PagingSource
import androidx.paging.PagingState
import domain.api.NewsDisplayingAPI
import domain.models.NewsData


class NewsRepoPagingSource(
    private val api: NewsDisplayingAPI,
    private val code:String
): PagingSource<Int, NewsData>(){

    override fun getRefreshKey(state: PagingState<Int, NewsData>): Int? {
        val anchorPosition = state.anchorPosition ?:return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsData> {
        val pageIndex = params.key ?: 0
        return try {
            val response = api.getNews(
                countryCode = code,
                pageIndex = pageIndex,
                pageSize = params.loadSize
            )
            val newsList = response.body()?.newsComponents ?: emptyList()

            LoadResult.Page(
                data = newsList,
                prevKey = if (pageIndex == 1) null else pageIndex - 1,
                nextKey = if(newsList.isEmpty()) null else pageIndex + 1
            )


        }catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}