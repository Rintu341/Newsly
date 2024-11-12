package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.ui.SearchSection

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, everythingViewModel: EverythingViewModel) {

    var isSearchState by remember{
        mutableStateOf(false)
    }
    val status = everythingViewModel.news.observeAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if(!isSearchState){
                        Text(text = "News App")
                    }else{
                        SearchSection(
                            onArrowClick = {
                            isSearchState = false
                            },
                            onSearchClick = { str->
                                everythingViewModel.getNews(str)

                            })
                    }}
                )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isSearchState = true;
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }
        }
    ) {innerpadding ->
        Column(
            modifier = Modifier
                .padding(innerpadding)
                .fillMaxSize()
        ) {
            Text(text = status.value?.data?.articles?.get(0)?.title.toString())
        }
    }
}

