package com.example.newsapp.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.ui.SearchSection
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, everythingViewModel: EverythingViewModel,authViewModel: AuthViewModel,username:String) {

    var isSearchState by remember{
        mutableStateOf(false)
    }
    val status = everythingViewModel.news.observeAsState()
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val currentUser = FirebaseAuth.getInstance().currentUser
    var displayName = currentUser?.displayName

    LaunchedEffect(key1 = true) {
        if (username != "username") {
            displayName = username
        }
        if (userId != null) {
            displayName?.let { authViewModel.checkAndSaveUser(userId = userId, username = it) }
        }
        Log.d("Home", "HomeScreen: $userId" )
    }
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

