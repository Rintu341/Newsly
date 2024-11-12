package com.example.newsapp.presentation.Everything

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Constants
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


@HiltViewModel
class EverythingViewModel @Inject constructor(
    private val everythingRepository: EverythingRepository
) : ViewModel(){

    private val _news = MutableLiveData<NetworkResult<NewsResponse> >()
    val news : LiveData<NetworkResult<NewsResponse>> get() = _news

    private val defaultStr :String = "top news"

    init {
        getNews(defaultStr, Constants.API_KEY)
    }
    /*
        We cannot set the value of MutableLiveData on Background Thread
        LiveData can only be called from Main(UI) thread
        while postValue() is used to update value from background thread
     */
    fun getNews(query: String,apikey:String = Constants.API_KEY)  {
        CoroutineScope(Dispatchers.IO).launch {
            _news.postValue(NetworkResult.Loading())
            try {
                val newsData = withContext(Dispatchers.IO) {
                    everythingRepository.getNews(query, apikey)
                }
                _news.postValue(NetworkResult.Success(data = newsData)) // use postValue to update the value on background thread
            }catch (e: Exception)
            {
                _news.postValue(NetworkResult.Error(message = e.message))
                Log.d("FETCH", "getNews: ${e.printStackTrace()} ")
            }
        }
    }
}