package com.example.newsapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.example.newsapp.data.model.AuthStatus
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.ui.navigation.AppScreen
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    val authStatus = authViewModel.authStatus.observeAsState()
    val context = LocalContext.current
    LaunchedEffect(authStatus.value) {
        delay(1000)
        when (authStatus.value) {
            is AuthStatus.Unauthenticated -> {
                navController.popBackStack()
                navController.navigate(AppScreen.OpeningScreen.name) { // clear all the back stack
                    popUpTo(AppScreen.OpeningScreen.name) {
                        inclusive = true
                    }
                }
            }

            is AuthStatus.Error -> Toast.makeText(
                context,
                (authStatus.value as AuthStatus.Error).message,
                Toast.LENGTH_SHORT
            ).show()

            else -> Unit
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(onClick = {
            authViewModel.signOut()
        }) {
            Text("log out")
        }
    }
}