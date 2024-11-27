package com.example.newsapp.ui.screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.newsapp.R
import com.example.newsapp.data.model.CardContent
import com.example.newsapp.presentation.Specific.SpecificViewModel
import com.example.newsapp.ui.navigation.AppScreen

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
//@Preview (showBackground = true, showSystemUi = true, device = Devices.PHONE)
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    searchViewModel: SpecificViewModel
) {
    var searchText by remember{
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchState by searchViewModel.news.observeAsState()
    Surface(
        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.primary

    ) {
        Column {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.TopStart
            )
            {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ){
                    SearchBar(
                        searchText,
                        focusManager = focusManager,
                        keyboardController = keyboardController,
                        navController = navController,
                        onSearchTextChange = {
                            searchText = it
                        }
                    ){
                        searchViewModel.getNews(it)
                        Log.d(
                            "Search", "SearchScreen: $it")
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(17.dp)
            ) {
                searchState?.data?.articles?.size?.let {
                    items(it) {it1 ->
                        SpecificNewsSection(
                            CardContent(
                                author = searchState?.data?.articles?.get(it1)?.author,
                                title = searchState?.data?.articles?.get(it1)?.title,
                                description = searchState?.data?.articles?.get(it1)?.description,
                                publishedAt = searchState?.data?.articles?.get(it1)?.publishedAt,
                                content = searchState?.data?.articles?.get(it1)?.content,
                                urlImage = searchState?.data?.articles?.get(it1)?.urlToImage,
                                url = searchState?.data?.articles?.get(it1)?.url
                            ),
                            navController
                        )
                    }
                }
            }


        }
    }
}
@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    focusManager: FocusManager,
    keyboardController: SoftwareKeyboardController?,
    navController: NavController,
    onSearchClick: (String) -> Unit
) {
    OutlinedTextField(
        colors = OutlinedTextFieldDefaults.colors( MaterialTheme.colorScheme.onSurface),
        value = searchText,
        onValueChange = { onSearchTextChange(it) }, // Update the state in the parent
        shape = RoundedCornerShape(50.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 17.dp, end = 17.dp, top = 40.dp),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    // Navigate to HomeScreen
                    navController.popBackStack()
                    navController.navigate(AppScreen.HomeScreen.name + "/username")
                }
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "menu",
                tint = Color.Black,
                modifier = Modifier.clickable {
                    // Show the calendar dialog
                }
            )
        },
        placeholder = {
            Text(text = "Search", color = Color.Black)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (searchText.trim().isNotBlank()) {
                    // Trigger search action
                    onSearchClick(searchText)
                }
                focusManager.clearFocus()
                keyboardController?.hide()
            }
        )
    )
}
