package com.example.newsapp.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.data.model.AuthStatus
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.ui.navigation.AppScreen
import kotlinx.coroutines.delay

@Composable
fun ProfileScreen(navController: NavHostController, authViewModel: AuthViewModel,modifier: Modifier = Modifier) {
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
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {

//        Text(text = "Name: ${authViewModel.username}")
        Text("Name")
        authViewModel.username?.let { OutlinedTextField(
            value = it,
            onValueChange = {  },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) }
        Text("Email")
        authViewModel.currentUser?.email?.let { OutlinedTextField(
            value = it,
            onValueChange = {  },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        ) }
        Spacer(modifier = Modifier.height(50.dp))
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Button(onClick = {
                authViewModel.signOut()
//                authViewModel.fetchUserName(context)
            }) {
                Text(text = "Log out")
            }
        }

    }
}