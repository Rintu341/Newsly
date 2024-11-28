package com.example.newsapp.ui.screen

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.data.model.CardContent
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.ui.navigation.AppScreen
import com.google.gson.Gson

@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
fun DetailsScreen(
    cardContent: CardContent,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeightDp = configuration.screenHeightDp
    val halfScreenHeightDp = screenHeightDp / 2
    val context = LocalContext.current
    val annotatedString = buildAnnotatedString {
        append("${cardContent.content?.let { cleanString(it) }} ")
        pushStringAnnotation(
            tag = "SEE_ALL",
            annotation = "See All"
        )
        withStyle(style = SpanStyle(color = Color.Blue, fontWeight = FontWeight.Bold)) {
            append("See All")
        }
        pop()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(halfScreenHeightDp.dp)
            ) {
                LoadImage(modifier = Modifier.height(halfScreenHeightDp.dp),cardContent = cardContent)
            }
        
            Box (
            modifier = Modifier
                .fillMaxSize(),
                contentAlignment = Alignment.BottomStart
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                    ,
                    colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondary),
                    shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)
                ) {
                    //content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 128.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
                    ) {
                        ClickableText(
                            text = annotatedString,
                            onClick = { offset ->
                                annotatedString.getStringAnnotations(tag = "SEE_ALL", start = offset, end = offset)
                                    .firstOrNull()?.let {
                                        // Trigger the operation when "See All" is clicked
                                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cardContent.url))
                                        context.startActivity(intent)
                                    }
                            },
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black

                            )
                        )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.BottomEnd
                        ) {
                            IconButton(
                                onClick = {
                                    authViewModel.saveArticle(cardContent,context)
                                },
                                modifier = Modifier
                                    .size(56.dp)
                                    .clip(shape = RoundedCornerShape(50)),
                                colors = IconButtonDefaults.iconButtonColors(colorResource(id = R.color.Primary))) {
                                Icon(
                                    modifier = Modifier.size(26.dp),
                                    painter = painterResource(id = R.drawable.group),
                                    contentDescription = "favorite",
                                    tint = Color.White
                                )
                            }

                        }
                    }

                }
            }
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                BlurredBackground(cardContent = cardContent)
            }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 40.dp),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                IconButton(onClick = {
                    navController.navigate(AppScreen.HomeScreen.name + "/{username}")
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back" )
                }

                Row {
                    Icon(
                        modifier = Modifier
                            .size(32.dp)
                            .clickable {
                                // Create an intent to share a URL
                                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                    type =
                                        "text/plain" // Specify the type of content to share
                                    putExtra(
                                        Intent.EXTRA_TEXT,
                                        "Check out this link: ${cardContent.url}"
                                    )
                                }
                                // Show the share options
                                context.startActivity(
                                    Intent.createChooser(
                                        shareIntent,
                                        "Share via"
                                    )
                                )
                            },
                        painter = painterResource(id = R.drawable.share),
                        contentDescription = "share"
                    )
                }

            }
        }


    }

}

fun cleanString(input: String): String {
    // Remove HTML tags
    val noHtml = input.replace(Regex("<.*?>"), "")

    // Replace escape sequences like \r\n with a space
    val normalized = noHtml.replace("\\r\\n", " ")

    // Remove any excessive placeholders like "[+10694 chars]"
    val cleaned = normalized.replace(Regex("\\[\\+\\d+ chars]"), "")

    // Trim any extra spaces
    return cleaned.trim()
}



//@Preview(showBackground = true)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BlurredBackground(cardContent: CardContent) {
    Box(
        modifier = Modifier
//            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth()
            .height(165.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxSize(),
                colors = CardDefaults.cardColors(Color.Transparent),
                shape = RoundedCornerShape(16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                colors = listOf(
                                    colorResource(id = R.color.blur).copy(0.9f),
                                    colorResource(id = R.color.blur).copy(0.9f)
                                )
                            )
                        )
                )
                {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = formatDateTimeWithYear(cardContent.publishedAt!!),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = cardContent.title!!,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Serif,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Published by "+ cardContent.author,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}
