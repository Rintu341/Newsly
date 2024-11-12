package com.example.newsapp.presentation.OnBoarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.newsapp.presentation.OnBoarding.Page

@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier,
                   pages: Page)
{
    Column {
        Image(
            painter = painterResource(id = pages.page ),
            contentDescription = null)


    }

}