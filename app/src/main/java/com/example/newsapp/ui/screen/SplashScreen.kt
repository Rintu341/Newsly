package com.example.newsapp.ui.screen


import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.delay



@Composable
fun AppSplashScreen(modifier: Modifier = Modifier,navController: NavController) {

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = colorResource(id = R.color.splashScreenColor)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ){
            Image(
                modifier =  Modifier.size(width = 75.dp, height = 75.dp),
                painter = painterResource(id = R.drawable.news),
                contentDescription = "logo"
            )
        }
    }
    LaunchedEffect(key1 = true) {
        delay(2000)
        navController.popBackStack()
        navController.navigate(AppScreen.OpeningScreen.name)
    }
}




@Preview
@Composable
private fun PreviewSplash() {
    NewsAppTheme {

    }
}