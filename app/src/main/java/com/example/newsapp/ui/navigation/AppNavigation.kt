package com.example.newsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.ui.screen.AppSplashScreen
import com.example.newsapp.ui.screen.HomeScreen
import com.example.newsapp.ui.screen.LoginScreen
import com.example.newsapp.ui.screen.OpeningScreen


@Composable
fun AppNavigation()
{
        val navController = rememberNavController()
        val everythingViewModel : EverythingViewModel = viewModel()
        NavHost(navController = navController, startDestination = AppScreen.SplashScreen.name) {

            composable(route = AppScreen.SplashScreen.name){
                AppSplashScreen(navController = navController)
            }
            composable(route = AppScreen.HomeScreen.name){
                HomeScreen(everythingViewModel = everythingViewModel)
            }
            composable(route = AppScreen.OpeningScreen.name){
                OpeningScreen(navController = navController)
            }
            composable(route = AppScreen.LoginScreen.name){
                LoginScreen(navController = navController)
            }


        }
}