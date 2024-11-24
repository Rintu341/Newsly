package com.example.newsapp.ui.screen

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.newsapp.R
import com.example.newsapp.ui.navigation.AppScreen

//sealed class BottomBarScreen(
//    val route:String,
//    val title:String,
//    val inon_unfocused:Int,
//    val icon_focused:Int)
//{
//    object Home : BottomBarScreen(
//        route = AppScreen.HomeScreen.name,
//        title = "Home",
//        inon_unfocused = R.drawable.homeunfocused,
//        icon_focused = R.drawable.homefocused
//    )
//    object Favorite : BottomBarScreen(
//        route = AppScreen.FavoriteScreen.name,
//        title = "Favorite",
//        inon_unfocused = R.drawable.favoriteunfocused,
//        icon_focused = R.drawable.favoritefocused
//    )
//    object Profile : BottomBarScreen(
//        route = AppScreen.ProfileScreen.name,
//        title = "Profile",
//        inon_unfocused = R.drawable.profileunfocused,
//        icon_focused = R.drawable.profilefocused
//    )
//}
data class NavItem(
    val label: String,
    val icon: Int,
)

//@Composable
//fun BottomBar(
//    navController: NavController,
//    currentRoute: String
//) {
//    val bottomBarScreens = listOf(
//        BottomBarScreen.Home,
//        BottomBarScreen.Favorite,
//        BottomBarScreen.Profile
//    )
//
//    BottomNavigation(
//        modifier = Modifier.padding(bottom = 40.dp).height(70.dp),
//        elevation = 10.dp,
//        backgroundColor = MaterialTheme.colorScheme.primary,
//        contentColor = MaterialTheme.colorScheme.onPrimary
//    ) {
//        bottomBarScreens.forEach { screen ->
//            val isSelected = currentRoute == screen.route
//
//            BottomNavigationItem(
//                icon = {
//                    Icon(
//                        painter = painterResource(
//                            id = if (isSelected) screen.icon_focused else screen.inon_unfocused
//                        ),
//                        contentDescription = screen.title,
//                        modifier = Modifier.size(23.dp)
//                    )
//                },
//                label = {
//                    Text(text = screen.title,
//                        fontSize = 11.sp)
//                },
//                selected = isSelected,
//                onClick = {
//                    if (!isSelected) { // Only navigate if it's not the current screen
//                        navController.navigate(screen.route) {
//                            // Clear the back stack to avoid duplicate destinations
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            restoreState = true
//                            launchSingleTop = true
//                        }
//                    }
//                },
//                alwaysShowLabel = true
//            )
//        }
//    }
//}





