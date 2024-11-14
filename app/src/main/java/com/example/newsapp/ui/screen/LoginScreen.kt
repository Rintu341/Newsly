package com.example.newsapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.screen.components.getPasswordError
import com.example.newsapp.ui.screen.components.isEmailValid

@Composable
fun LoginScreen(modifier: Modifier = Modifier, greet:String = "Hi Welcome!👋",navController: NavController) {


    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

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
            GreetText(value = R.string.greet)
            Spacer(modifier = Modifier.heightIn(min = 30.dp))
            CustomOutlinedTextField(label = stringResource(id = R.string.email_label), isEmail = true,
                onNextClicked ={
                    email = it.trim()
                    emailError = if (isEmailValid(email)) null else "Invalid email format"
                } )
            emailError?.let { Text(text = it, color = Color.Red.copy(alpha = 0.5f)) }
            Spacer(modifier = Modifier.heightIn(min = 20.dp))
            CustomOutlinedTextField(label = stringResource(id = R.string.password_label),isEmail = false,
                onNextClicked = {
                    password = it.trim()
                    passwordError = getPasswordError(password)
                })
            Spacer(modifier = Modifier.heightIn(min = 10.dp))
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ){
                Text(text = "Forgot password?",
                    style = TextStyle(
                        color = Color.Black
                    ),
                    modifier = Modifier.clickable {
                        // Forgot password to solve
                    })
            }
            passwordError?.let { Text(text = it, color = Color.Red.copy(alpha = 0.5f)) }
            Spacer(modifier = Modifier.heightIn(min = 50.dp))
            CustomButtonBlack(str = stringResource(id = R.string.login),
                onClick = {
                    //Perform action (User login)
                    if (isEmailValid(email) && passwordError == null)
                    {

                    }
                }
            )
            Spacer(modifier = Modifier.heightIn(min = 10.dp))
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
                    clickableText = stringResource(id = R.string.signup)
                ){
                        // navigate to Sign Up Screen
                    navController.navigate(AppScreen.SignupScreen.name)
                }
            }

        }
    }

}


@Composable
fun GreetText(modifier: Modifier = Modifier, value: Int) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(value),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.Black
            )
        )
    }
}
@Preview(showBackground = true)
@Composable
fun SignUpText(
    initialText: String = "Don’t have an account? ",
    clickableText: String = "Sign up",
    onSignUpClick: () -> Unit = {}
) {
    val annotatedText = buildAnnotatedString {
        // Add the initial non-clickable text
        append(initialText)

        // Add the clickable text with an annotation
        pushStringAnnotation(tag = "SignUp", annotation = "SignUp")
        withStyle(style = SpanStyle(color = Color.Black, fontWeight = FontWeight.ExtraBold)) {
            append(clickableText)
        }
        pop()
    }

    // Display the clickable text
    ClickableText(
        text = annotatedText,
        onClick = { offset ->
            // Check if the clicked position matches the "SignUp" tag
            annotatedText.getStringAnnotations(tag = "SignUp", start = offset, end = offset)
                .firstOrNull()?.let { annotation ->
                    if (annotation.item == "SignUp") {
                        onSignUpClick()
                    }
                }
        },
        modifier = Modifier.padding(8.dp)
    )
}



@Preview(showBackground = true)
@Composable
fun CustomTopAppBar(modifier: Modifier = Modifier,onClick:() -> Unit = {}) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    )
    {
        Image(
                modifier = Modifier
                    .size(height = 39.dp, width = 39.dp)
                    .clickable {
                        onClick.invoke()
                    },
                painter = painterResource(id = R.drawable.back),
                contentDescription = "back button"
        )
        Image(
                modifier = Modifier
                    .size(height = 44.dp, width = 46.dp),
                painter = painterResource(id = R.drawable.star8),
                contentDescription = "back button"
        )

    }
}


@Preview(showBackground = true)
@Composable
fun CustomOutlinedTextField(
    modifier: Modifier = Modifier,
    label :String = "Password",
    isEmail:Boolean = true,
    onNextClicked:(String) -> Unit = {}
) {

    val focusManager = LocalFocusManager.current

    var value by remember{
        mutableStateOf("")
    }
    var passwordVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(label,
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value ,
            onValueChange ={
                value = it
                onNextClicked(value)
            },
            label = { Text(if(isEmail)"Your Email" else "Password", color = Color.DarkGray)},
            shape = RoundedCornerShape(10.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = if(isEmail) KeyboardType.Email else KeyboardType.Password,
                imeAction =  if(isEmail) ImeAction.Next else ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.clearFocus()
                        onNextClicked(value)

                },
                onDone = {
                    focusManager.clearFocus()
                        onNextClicked(value)

                }
            ),
            visualTransformation = if(!isEmail)if(passwordVisible ) VisualTransformation.None else PasswordVisualTransformation() else { VisualTransformation.None},
            trailingIcon = {
                if(!isEmail) {
                    val image = if (passwordVisible)
                        painterResource(id = R.drawable.show)
                    else painterResource(id = R.drawable.hide)

                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            modifier = Modifier.size(20.dp),
                            painter = image,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            tint = Color.Black
                        )
                    }
                }


            }
        )
    }

}



//@Preview(showBackground = true)
@Composable
private fun PreviewLogin() {

}