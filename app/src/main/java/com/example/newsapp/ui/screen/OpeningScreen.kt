package com.example.newsapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreen
import com.example.newsapp.ui.theme.NewsAppTheme

@Composable
fun OpeningScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(17.dp)
            .background(color = Color.White),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Illustration()
            Spacer(modifier = Modifier.heightIn(min = 35.dp))



            Column(
                modifier = Modifier
                    .heightIn(min = 95.dp)
                    .widthIn(min = 319.dp)
                    .padding(17.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                BoldText(value = stringResource(id = R.string.BOLD_String))

                Spacer(modifier = Modifier.heightIn(min = 5.dp))
                
                Text(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 15.dp, end = 15.dp),
                        text = stringResource(id = R.string.normal_text),
                    textAlign = TextAlign.Center)
            }



            Spacer(modifier = Modifier.heightIn(min = 50.dp))
            CustomButtonBlack(str = stringResource(id = R.string.Signin), onClick = {
                navController.navigate(AppScreen.LoginScreen.name)
            })
            Spacer(modifier = Modifier.height(10.dp))
            CustomButtonWhite(str = stringResource(id = R.string.Createaccount),
                onClick = {
                    navController.navigate(AppScreen.SignupScreen.name)
                })

        }
    }

}



@Preview
@Composable
fun Illustration(modifier: Modifier = Modifier) {
    Box (
        contentAlignment = Alignment.Center
    )
    {
        Image(
            modifier = Modifier
                .heightIn(min = 273.dp)
                .widthIn(min = 315.dp),
            painter = painterResource(id = R.drawable.illustration),
            contentDescription = "illustration"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BoldText(modifier: Modifier = Modifier, value:String = stringResource(id = R.string.BOLD_String)) {
    Text(
        text = value,
        style = TextStyle(
            color = Color.Black,
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
        )

    )
}


@Preview(showBackground = true)
@Composable
fun CustomButtonBlack(modifier: Modifier = Modifier,str:String = "Sign In",
                      onClick:()->Unit = {}) {
    Box(
        modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    )
    {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .widthIn(min = 353.dp),
            onClick = {
                onClick.invoke()
            },
            colors = ButtonDefaults.buttonColors(Color.Black),
            shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = str,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 16.sp
                    ))
            }
    }
}
@Preview(showBackground = true)
@Composable
fun CustomButtonWhite(modifier: Modifier = Modifier, str: String = "Create account",
                      onClick:() -> Unit = {}) {
    Box(
        modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
                .widthIn(min = 353.dp),
            onClick = {
                onClick.invoke()
            },
            colors = ButtonDefaults.outlinedButtonColors(Color.White),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(width = 1.dp, color = colorResource(id = R.color.BottonBordercolor))
        ) {
            Text(
                text = str,
                color = Color.Black,
                style = TextStyle(fontSize = 17.sp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewOpening() {
    NewsAppTheme {
//        OpeningScreen(navController)
    }
}