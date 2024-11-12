package com.example.newsapp.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideIn
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import kotlinx.coroutines.delay

@Composable
fun AppSplashScreen(modifier: Modifier = Modifier,navController: NavController) {
    var isAnimate by remember {
        mutableStateOf(false)
    }
    val animateScale by animateFloatAsState(
        targetValue = if(isAnimate) 0.5f else 0f,
        label = "animate size",
        animationSpec = tween(
            durationMillis = 2000,
            easing = LinearEasing
        )
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (isSystemInDarkTheme()) Color.Blue else Color.Cyan.copy(alpha = .3f)),
        contentAlignment = Alignment.Center
    )
    {
        Icon(painter = painterResource(id = R.drawable.logo),
            contentDescription = null,
            modifier = Modifier.scale(animateScale))
    }
    LaunchedEffect(key1 = true) {
        isAnimate = true
        delay(2000)
        navController.popBackStack()
        navController.navigate(AppScreen.HomeScreen.name)
    }
}




@Preview
@Composable
private fun PreviewSplash() {
    NewsAppTheme {

    }
}