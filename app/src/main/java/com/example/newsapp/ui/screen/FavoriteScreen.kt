package com.example.newsapp.ui.screen


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.data.model.CardContent
import com.example.newsapp.presentation.Authentication.AuthViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(modifier: Modifier = Modifier, navController: NavHostController,authViewModel: AuthViewModel) {
    val context = LocalContext.current
    val favoriteArticles = remember { mutableStateListOf<CardContent>() }

    LaunchedEffect(Unit) {
        authViewModel.fetchArticles(context)
        {
            it.forEach { article ->
                favoriteArticles.add(article)
            }
        }
    }

   Column(
       modifier = modifier.fillMaxSize(),
   ) {
       LazyColumn(
           modifier = Modifier
               .fillMaxSize()
               .padding(17.dp)
       ) {
           items(favoriteArticles)
           {
               SpecificNewsSection(it,navController)
           }
       }
   }
}