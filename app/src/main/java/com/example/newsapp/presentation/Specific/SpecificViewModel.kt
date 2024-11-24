package com.example.newsapp.presentation.Specific

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.data.model.Constants
import com.example.newsapp.data.model.NewsResponse
import com.example.newsapp.data.model.Specific
import com.example.newsapp.data.network.NetworkResult
import com.example.newsapp.presentation.Everything.EverythingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SpecificViewModel @Inject constructor(
    private val everythingRepository: EverythingRepository
) :ViewModel() {

    private val _news = MutableLiveData<NetworkResult<NewsResponse> >()
    val news : LiveData<NetworkResult<NewsResponse>> get() = _news

    private val defaultStr :String = Specific.All.name
    val calendar = Calendar.getInstance()
    val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    var yesterdayFormatted = dateFormat.format(calendar.time)

    init {
        calendar.add(Calendar.DATE, -2)
        yesterdayFormatted = dateFormat.format(calendar.time)  // Subtract one day
        getNews(defaultStr, Constants.API_KEY, from = yesterdayFormatted)
    }
    /*
        We cannot set the value of MutableLiveData on Background Thread
        LiveData can only be called from Main(UI) thread
        while postValue() is used to update value from background thread
     */
    fun getNews(query: String, apikey:String = Constants.API_KEY, from:String = yesterdayFormatted)  {
        CoroutineScope(Dispatchers.IO).launch {
            _news.postValue(NetworkResult.Loading())
            try {
                val newsData = withContext(Dispatchers.IO) {
                    everythingRepository.getNews(query, apikey,from)
                }
                newsData.articles = newsData.articles.filter { // Data filter
                    it.source.id != null
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