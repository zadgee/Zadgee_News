package screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import domain.state.NewsState
import domain.repository.NewsDisplayingRepository
import domain.state.NewsUiState
import javax.inject.Inject

@HiltViewModel
class NewsViewModel@Inject constructor(
    private val repository: NewsDisplayingRepository
): ViewModel() {
    private val _newsState = MutableStateFlow(NewsState())
    val newsState = _newsState.asStateFlow()


    fun displayNews(){
        viewModelScope.launch(Dispatchers.IO){
            _newsState.emit(
                NewsState(
                    error = null,
                    isLoading = true
                )
            )
            when(val displayingNews = repository.getNews()){
                is NewsUiState.Error ->{
                    _newsState.emit(
                        NewsState(
                            error = displayingNews.errorMessage,
                            isLoading = false,
                            newsDisplay = null
                        )
                    )
                }
                is NewsUiState.Success ->{
                    _newsState.emit(
                        NewsState(
                            error = null,
                            isLoading = false,
                            newsDisplay = displayingNews.data
                        )
                    )
                }

            }
        }
    }
}