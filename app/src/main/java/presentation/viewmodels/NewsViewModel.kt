package presentation.viewmodels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import domain.models.NewsData
import domain.use_cases.ConvertCoordinatesToCountryCodeUseCase
import domain.use_cases.GetNewsUseCase
import domain.use_cases.GetUserLocationUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class NewsViewModel@Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val getUserLocationUseCase:GetUserLocationUseCase,
    private val convertCoordinatesToCountryCodeUseCase: ConvertCoordinatesToCountryCodeUseCase
): ViewModel() {


    private val primaryCategoryFlow = MutableStateFlow<String?>(null)


     fun setPrimaryNewsCode(code:String){
         primaryCategoryFlow.value = code
     }

    @OptIn(ExperimentalCoroutinesApi::class)
    val newsStateFlow : Flow<PagingData<NewsData>> = primaryCategoryFlow
            .filterNotNull()
            .flatMapLatest { _->
                val location = getUserLocationUseCase.getUserLocation()
                val latitude = location?.latitude
                val longitude = location?.longitude
                val userCoordinates = listOf(
                    latitude,
                    longitude
                ).toString()
                val code = convertCoordinatesToCountryCodeUseCase.convertCoordinatesToCountryCode(userCoordinates).toString()
                getNewsUseCase.getNews(code)
            }.cachedIn(viewModelScope)








}