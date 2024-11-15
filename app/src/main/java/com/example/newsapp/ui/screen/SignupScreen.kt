package com.example.newsapp.ui.screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.AuthStatus
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.screen.components.getPasswordError
import com.example.newsapp.ui.screen.components.isEmailValid


//@Preview(showBackground = true)
@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    authViewModel: AuthViewModel
) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var username by remember {
        mutableStateOf("")
    }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }


    val authStatus = authViewModel.authStatus.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authStatus.value) {
        when(authStatus.value)
        {
            is AuthStatus.Authenticated -> {
                navController.navigate(AppScreen.HomeScreen.name+"/${username}")
            }
            is AuthStatus.Error -> Toast.makeText(context, (authStatus.value as AuthStatus.Error).message, Toast.LENGTH_SHORT).show()
            else ->Unit
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .padding(17.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.heightIn(min = 30.dp))
            CustomTopAppBar(onClick = {
                navController.navigate(AppScreen.OpeningScreen.name) {
                    popUpTo(AppScreen.OpeningScreen.name) {
                        inclusive = true
                    }
                }
            })
            Spacer(modifier = Modifier.heightIn(min = 60.dp))
            GreetText(value = R.string.createAccount)
            Spacer(modifier = Modifier.heightIn(min = 30.dp))

            SimpleOutlinedTextField(
                onNextClicked = {
                    username = it.trim()
                }
            )

            Spacer(modifier = Modifier.heightIn(min = 20.dp))
            CustomOutlinedTextField(label = stringResource(id = R.string.email), isEmail = true,
                onNextClicked = {
                    email = it.trim()
                    emailError = if (isEmailValid(email)) null else "Invalid email format"
                }
            )
            emailError?.let { Text(text = it, color = Color.Red.copy(alpha = 0.5f)) }
            Spacer(modifier = Modifier.heightIn(min = 20.dp))
            CustomOutlinedTextField(label = stringResource(id = R.string.password_label),isEmail = false,
                onNextClicked = {
                    password = it.trim()
                    passwordError = getPasswordError(password)
                }
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp))
            passwordError?.let { Text(text = it, color = Color.Red.copy(alpha = 0.5f)) }
            Spacer(modifier = Modifier.heightIn(min = 50.dp))
            CustomButtonBlack(str = stringResource(id = R.string.signup),
                onClick = {
                    // User sign up logic
                    if (isEmailValid(email) && passwordError == null && username.isNotBlank()) {
                        Log.d("Signup", "SignupScreen: ${username},${email}, $password")
                        authViewModel.signUp(email, password)
                    }

                }
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp))

            //For future update if i want to add more
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Spacer(modifier = Modifier
//                    .padding(end = 10.dp)
//                    .size(width = 100.dp, height = 1.dp)
//                    .background(color = Color.Gray)
//                    )
//                Text("Or Login with", color = Color.Black)
//                Spacer(modifier = Modifier
//                    .padding(start = 10.dp)
//                    .size(width = 100.dp, height = 1.dp)
//                    .background(color = Color.Gray)
//                )
//            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                SignUpText(
                    initialText = stringResource(id = R.string.alreadyHaveAnAccount),
                    clickableText = stringResource(id = R.string.login)
                ){
                    // navigate to Sign Up Screen
                    navController.navigate(AppScreen.LoginScreen.name)
                }
            }

        }
    }
}

@Composable
fun SimpleOutlinedTextField(
    label:String = "Username",
    onNextClicked:(String) -> Unit = {},
    ) {
    var text by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    Column {
        Text(label, color = Color.Black)
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onNextClicked(text)
                            },
            label = { Text( stringResource(id = R.string.usernamelabel), color = Color.Black) },
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                        onNextClicked(text)

                }
            )
        )
    }

}

