package com.example.newsapp.ui.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.Specific
import com.example.newsapp.presentation.Authentication.AuthViewModel
import com.example.newsapp.presentation.Everything.EverythingViewModel
import com.example.newsapp.presentation.Specific.SpecificViewModel
import com.google.firebase.auth.FirebaseAuth
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.calendar.models.CalendarTimeline


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    everythingViewModel: EverythingViewModel,
    authViewModel: AuthViewModel,
    username: String,
    specificViewModel: SpecificViewModel,
    navController: NavHostController
) {
    val userId = FirebaseAuth.getInstance().currentUser?.uid
    val currentUser = FirebaseAuth.getInstance().currentUser
    var displayName = currentUser?.displayName

    var selectDate by remember {
        mutableStateOf("")
    }
    val isFocused by remember {
        mutableStateOf(false)
    }
    //CALENDAR
    val calendarState = rememberSheetState()

    val navItemList = listOf(
        NavItem(
            label = "Home",
            icon = R.drawable.homeunfocused
        ),
        NavItem(
            label = "Favorite",
            icon = R.drawable.favoriteunfocused
        ),
        NavItem(
            label = "Profile",
            icon = R.drawable.profileunfocused
        )
    )
    var selectedIndex by remember {
        mutableStateOf(0)
    }

    CalendarDialog(
        state = calendarState,
        selection = CalendarSelection.Date{ date ->
            Log.d("SelectDate", "HomeScreen: $date ")
            selectDate = date.toString()
            everythingViewModel.getNews(query = Specific.Latest.name,from = selectDate)
            specificViewModel.getNews(query = Specific.All.name,from = selectDate)
        },
        config = CalendarConfig(
            style = CalendarStyle.MONTH,
            disabledTimeline = CalendarTimeline.FUTURE,
        )
    )


    LaunchedEffect(key1 = true) {
        if (username != "username") {
            displayName = username
        }
        if (userId != null) {
            displayName?.let { authViewModel.checkAndSaveUser(userId = userId, username = it) }
        }
        Log.d("Home", "HomeScreen: $userId" )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                Text(text = stringResource(id = R.string.Newsly),
                    fontWeight = FontWeight.ExtraBold,
                    fontStyle = FontStyle.Italic,
                    fontFamily = FontFamily.SansSerif)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary)
            )
        },
        bottomBar = {
            NavigationBar{
                navItemList.forEachIndexed{ index, item ->
                    NavigationBarItem(
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                modifier = Modifier.size(20.dp),
                                painter = painterResource(id = item.icon),
                                contentDescription = "icon"
                            )
                        },
                        label = {
                                Text(item.label)
                        }
                    )
                }
            }
        },
        floatingActionButton = {
            if(selectedIndex == 0)
            {
                FloatingActionButton(
                    onClick = {

                    },
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "search"
                    )
                }
            }
        },
    ) {
        ContentScreen(
            modifier = Modifier.padding(it),
            selectedIndex = selectedIndex,
            navController = navController,
            everythingViewModel,
            specificViewModel,
            authViewModel
        )
        
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentScreen(modifier : Modifier = Modifier,selectedIndex:Int,navController: NavHostController,everythingViewModel: EverythingViewModel,specificViewModel: SpecificViewModel,authViewModel: AuthViewModel)
{
    when(selectedIndex)
    {
        0 -> MainScreen(modifier,navController = navController,everythingViewModel,specificViewModel)
        1 -> FavoriteScreen(navController = navController)
        2 -> ProfileScreen(navController = navController,authViewModel)
    }
}




