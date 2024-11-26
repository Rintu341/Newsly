package com.example.newsapp.ui.screen

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.example.newsapp.R
import com.example.newsapp.data.model.CardContent
import com.example.newsapp.data.model.Specific
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.presentation.Specific.SpecificViewModel
import com.example.newsapp.ui.navigation.AppScreen
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainScreen(modifier: Modifier = Modifier,navController: NavHostController,everythingViewModel: EverythingViewModel,specificViewModel: SpecificViewModel) {

    val newsButtons = mapOf<Int,String>(
        0 to Specific.Arts.name,
        1 to Specific.Tech.name,
        2 to Specific.Finance.name,
        3 to Specific.Space.name,
        4 to Specific.Sport.name
    )
    val statusEverything by everythingViewModel.news.observeAsState()
    val statusSpecific by specificViewModel.news.observeAsState()
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    Column(
        modifier = modifier.padding(17.dp),

    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = "Latest News",
                fontSize = 18.sp,
                fontWeight = FontWeight.ExtraBold,
                fontStyle = FontStyle.Italic
            )
            Text(
                text = "See All   ->",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {

                }
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        SwappableCards(everythingViewModel = everythingViewModel,navController)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow {
            items(newsButtons.size){index ->
                NewsButton(
                    newsButtons[index]!!,
                    selected = index == selectedIndex,
                    index = index,
                    onClick = {it ->
                        // Function to handle button click
                        selectedIndex = it

                        specificViewModel.getNews(newsButtons[it]!!)

                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
//            modifier = Modifier.shimmerEffect()
        ){
            items(10)
            {it ->
                    SpecificNewsSection(
                        CardContent(
                            author = statusSpecific?.data?.articles?.get(it)?.author,
                            title = statusSpecific?.data?.articles?.get(it)?.title,
                            description = statusSpecific?.data?.articles?.get(it)?.description,
                            publishedAt = statusSpecific?.data?.articles?.get(it)?.publishedAt,
                            content = statusSpecific?.data?.articles?.get(it)?.content,
                            urlImage = statusSpecific?.data?.articles?.get(it)?.urlToImage,
                            url = statusSpecific?.data?.articles?.get(it)?.url
                        ),
                        navController
                    )
            }
        }

    }
}


//@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE,)
@Composable
private fun NewsButton(label : String = Specific.Space.name,index: Int = 0, selected :Boolean = false, onClick: (Int) -> Unit = {}) {
    val selected1 = selected
    val coroutineScope = rememberCoroutineScope()
    Button(
        modifier = Modifier.padding(end = 4.dp),
        onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                delay(500)
                onClick.invoke(index)
            }

        },
        colors = if(selected1) ButtonDefaults.buttonColors(colorResource(id = R.color.Primary)) else ButtonDefaults.buttonColors(Color.White),
    ) {
        Text(
            text = label,
            color = if(selected1)Color.White else Color.Black,
            fontWeight = FontWeight.Bold,
            )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showSystemUi = true, showBackground = true, device = Devices.PHONE)
@Composable
private fun SpecificNewsSection(
    cardContent: CardContent ,
        navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable {
                val cardContentJsonString = Gson().toJson(cardContent)
                navController.navigate(AppScreen.DetailsScreen.name +"?cardContent=$cardContentJsonString")
            }
            .height(128.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            LoadImage(cardContent = cardContent)
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
//                contentAlignment = Alignment.TopStart)
            )
            {

                Text(
                    text = cardContent.title?: "",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = FontFamily.Serif,
                    fontSize = 14.sp
                )
            }
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom
            ){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween)
                {
                    Text(
                        text =  cardContent.author ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = cardContent.publishedAt?.let { formatDateTimeWithYear(it) } ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        maxLines = 1,
                    )
                }

            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SwappableCards(everythingViewModel: EverythingViewModel, navController: NavHostController)
{
    val pagerState = rememberPagerState()
    val statusEverything by everythingViewModel.news.observeAsState()
    val cardItems = listOf<CardContent>(
        CardContent(
            author = statusEverything?.data?.articles?.get(0)?.author,
            title = statusEverything?.data?.articles?.get(0)?.title,
            description = statusEverything?.data?.articles?.get(0)?.description,
            publishedAt = statusEverything?.data?.articles?.get(0)?.publishedAt,
            urlImage = statusEverything?.data?.articles?.get(0)?.urlToImage,
            url = statusEverything?.data?.articles?.get(0)?.url,
            content = statusEverything?.data?.articles?.get(0)?.content
        ),
        CardContent(
            author = statusEverything?.data?.articles?.get(1)?.author,
            title = statusEverything?.data?.articles?.get(1)?.title,
            description = statusEverything?.data?.articles?.get(1)?.description,
            publishedAt = statusEverything?.data?.articles?.get(1)?.publishedAt,
            urlImage = statusEverything?.data?.articles?.get(1)?.urlToImage,
            url = statusEverything?.data?.articles?.get(1)?.url,
            content = statusEverything?.data?.articles?.get(1)?.content
        ),
        CardContent(
            author = statusEverything?.data?.articles?.get(2)?.author,
            title = statusEverything?.data?.articles?.get(2)?.title,
            description = statusEverything?.data?.articles?.get(2)?.description,
            publishedAt = statusEverything?.data?.articles?.get(2)?.publishedAt,
            urlImage = statusEverything?.data?.articles?.get(2)?.urlToImage,
            url = statusEverything?.data?.articles?.get(2)?.url,
            content = statusEverything?.data?.articles?.get(2)?.content

        ),
        CardContent(
            author = statusEverything?.data?.articles?.get(3)?.author,
            title = statusEverything?.data?.articles?.get(3)?.title,
            description = statusEverything?.data?.articles?.get(3)?.description,
            publishedAt = statusEverything?.data?.articles?.get(3)?.publishedAt,
            urlImage = statusEverything?.data?.articles?.get(3)?.urlToImage,
            url = statusEverything?.data?.articles?.get(3)?.url,
            content = statusEverything?.data?.articles?.get(3)?.content
        ),
        CardContent(
            author = statusEverything?.data?.articles?.get(4)?.author,
            title = statusEverything?.data?.articles?.get(4)?.title,
            description = statusEverything?.data?.articles?.get(4)?.description,
            publishedAt = statusEverything?.data?.articles?.get(4)?.publishedAt,
            urlImage = statusEverything?.data?.articles?.get(4)?.urlToImage,
            url = statusEverything?.data?.articles?.get(4)?.url,
            content = statusEverything?.data?.articles?.get(4)?.content
        )
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Swipeable Row of Cards
        HorizontalPager(
            count = cardItems.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            CardItem(cardItems[page],navController)
        }

        Spacer(modifier = Modifier.height(5.dp))

        // Dots Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = Color.Gray,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
    
}


//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CardItem(cardContent: CardContent =
        CardContent(
            author = "Mint",
            title = "Crypto investors should be prepared to lose all their money, BOE governor says",
            description = "“I’m going to say this very bluntly again,” he added. “Buy them only if you’re prepared to lose all your money.”"
        ), // Default Content
             navController: NavHostController
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.onSecondary),
        elevation = CardDefaults.elevatedCardElevation(10.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val cardContentSowJsonString = Gson().toJson(cardContent)
               navController.navigate(AppScreen.DetailsScreen.name +"?cardContent=$cardContentSowJsonString")
            }
//            .aspectRatio(1.5f) // Adjust card aspect ratio
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {

            LoadImage(cardContent = cardContent)
            // Overlay Text
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            ) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Bottom
                ){
                    Text(
                        text = ("by " + cardContent.author) ?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = cardContent.title?: "",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }

        }
    }
}

@Composable
fun LoadImage(modifier: Modifier = Modifier,cardContent: CardContent) {
    SubcomposeAsyncImage(
        model = cardContent.urlImage,
        contentDescription = "Card Background",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colorStops = arrayOf(
                        0.2f to Color.Black, // Black at 20%
                        0.8f to Color.White // White at 80%
                    )
                )
            )
    ) {
        when (painter.state) {
            is AsyncImagePainter.State.Loading -> {
                ShimmerBox(modifier = Modifier.shimmerEffect())
            }

            is AsyncImagePainter.State.Error -> {
                // Fallback in case of an error
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                )
            }

            else -> {
                SubcomposeAsyncImageContent()
            }
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun ShimmerBox(
    modifier: Modifier = Modifier,
    shape: RoundedCornerShape = RoundedCornerShape(8.dp)
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(Color.Gray),
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxHeight()
//            .aspectRatio(1.5f) // Adjust card aspect ratio
    )
    {

    }
}

fun Modifier.shimmerEffect():Modifier = composed {
    var size by remember{
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000
            )
        ),
        label = ""
    )
    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX,0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    )
        .onGloballyPositioned {
            size = it.size
        }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDateTimeWithYear(input: String): String {
    // Parse the input string
    val zonedDateTime = ZonedDateTime.parse(input)

    // Format to desired output: "week, date month year"
    val formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy", Locale.ENGLISH)

    return zonedDateTime.format(formatter)
}
