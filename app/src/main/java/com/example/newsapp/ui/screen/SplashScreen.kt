package com.example.newsapp.ui.screen


import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.data.model.AuthStatus
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.theme.NewsAppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay



@Composable
fun AppSplashScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val authStatus = authViewModel.authStatus.observeAsState()
    val context = LocalContext.current
//    val currentUser = FirebaseAuth.getInstance().currentUser
    Surface(
        modifier = Modifier
            .fillMaxSize(),
            color = colorResource(id = R.color.splashBackGround)
    ) {
        Box(
            contentAlignment = Alignment.Center
        ){

            Image(
                modifier =  Modifier.size(200.dp),
                painter = painterResource(id = R.drawable.boyicon),
                contentDescription = "logo"
            )
        }
    }
    LaunchedEffect(authStatus.value) {
        delay(3000)
            when (authStatus.value) {
                is AuthStatus.Authenticated -> {
                    val username = "username"
                    navController.popBackStack()
                    navController.navigate(AppScreen.HomeScreen.name+"/${username}")
                }
                is AuthStatus.Unauthenticated -> {
                    navController.popBackStack()
                    navController.navigate(AppScreen.OpeningScreen.name)
                }

                is AuthStatus.Error -> Toast.makeText(
                    context,
                    (authStatus.value as AuthStatus.Error).message,
                    Toast.LENGTH_SHORT
                ).show()

                else -> Unit
            }
    }
}




@Preview
@Composable
private fun PreviewSplash() {
    NewsAppTheme {

    }
}