package com.example.newsapp.presentation.Everything

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.model.Constants
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.model.Specific
import com.example.newsapp.data.network.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.http.Query
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject
import kotlin.coroutines.coroutineContext


@HiltViewModel
class EverythingViewModel @Inject constructor(
    private val everythingRepository: EverythingRepository
) : ViewModel(){

    private val _news = MutableLiveData<NetworkResult<NewsResponse> >()
    val news : LiveData<NetworkResult<NewsResponse>> get() = _news

    private val defaultStr :String = Specific.Latest.name
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var yesterdayFormatted = dateFormat.format(calendar.time)

    init {
        updateDateAndFetchNews()
    }
    /*
        We cannot set the value of MutableLiveData on Background Thread
        LiveData can only be called from Main(UI) thread
        while postValue() is used to update value from background thread
     */
    /*
    fun getNews(query: String,apikey:String = Constants.API_KEY,from:String = yesterdayFormatted)  {
        Log.d("TAG", "getNews: $from")
        Log.d("TAG", "getNews:  ${_news.value?.data}")
        CoroutineScope(Dispatchers.IO).launch {
            _news.postValue(NetworkResult.Loading())
            try {
                val newsData = withContext(Dispatchers.IO) {
                    everythingRepository.getNews(query, apikey,from)
                }
                _news.postValue(NetworkResult.Success(data = newsData)) // use postValue to update the value on background thread
            }catch (e: Exception)
            {
                _news.postValue(NetworkResult.Error(message = e.message))
                Log.d("FETCH", "getNews: ${e.printStackTrace()} ")
            }
        }
    }
    */

    fun getNews(query: String, apikey: String = Constants.API_KEY, from: String) {
        Log.d("TAG", "getNews: $from")
        Log.d("TAG", "getNews: ${_news.value?.data}")
        CoroutineScope(Dispatchers.IO).launch {
            _news.postValue(NetworkResult.Loading())
            try {
                val newsData = withContext(Dispatchers.IO) {
                    everythingRepository.getNews(query, apikey, from)
                }
                newsData.articles = newsData.articles.filter { // Data filter
                    it.source.id != null
                }
                _news.postValue(NetworkResult.Success(data = newsData)) // Use postValue to update the value on background thread
            } catch (e: Exception) {
                _news.postValue(NetworkResult.Error(message = e.message))
                Log.d("FETCH", "getNews: ${e.printStackTrace()} ")
            }
        }
    }

    fun updateDateAndFetchNews() {
        calendar.add(Calendar.DATE, -2)
        yesterdayFormatted = dateFormat.format(calendar.time)
        Log.d("String", "updateDateAndFetchNews: $defaultStr")
        getNews(defaultStr, Constants.API_KEY, from = yesterdayFormatted)
    }


}

