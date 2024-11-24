package com.example.newsapp.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.presentation.Specific.SpecificViewModel
import com.example.newsapp.ui.screen.AppSplashScreen
import com.example.newsapp.ui.screen.FavoriteScreen
import com.example.newsapp.ui.screen.HomeScreen
import com.example.newsapp.ui.screen.LoginScreen
import com.example.newsapp.ui.screen.OpeningScreen
import com.example.newsapp.ui.screen.ProfileScreen
import com.example.newsapp.ui.screen.SignupScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation()
{
        val navController = rememberNavController()
        val everythingViewModel : EverythingViewModel = viewModel()
        val specificViewModel : SpecificViewModel = viewModel()
        val authViewModel: AuthViewModel = viewModel()
        NavHost(navController = navController, startDestination = AppScreen.SplashScreen.name) {

            composable(route = AppScreen.SplashScreen.name){
                AppSplashScreen(navController = navController,authViewModel = authViewModel)
            }
            composable(route = AppScreen.HomeScreen.name+"/{username}",
                arguments = listOf(navArgument("username") { type = NavType.StringType })
                ){backStackEntry ->
                val username = backStackEntry.arguments?.getString("username")
                if (username != null) {
                    HomeScreen(navController = navController,
                        everythingViewModel = everythingViewModel,
                        specificViewModel = specificViewModel,
                        authViewModel = authViewModel,
                        username = username
                    )
                }
            }
            composable(route = AppScreen.OpeningScreen.name){
                OpeningScreen(navController = navController)
            }
            composable(route = AppScreen.LoginScreen.name){
                LoginScreen(navController = navController, authViewModel = authViewModel)
            }
            composable(route = AppScreen.SignupScreen.name){
                SignupScreen(navController = navController,authViewModel = authViewModel)
            }

        }
}