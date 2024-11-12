package com.example.newsapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


@Preview(showBackground = true)
@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    onArrowClick:() -> Unit = {},
    onSearchClick:(String) -> Unit = {}
    ) {

    var searchStr by remember {
        mutableStateOf("")
    }
//    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    Box()
    {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
            shape = RoundedCornerShape(50),
            singleLine = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back Arrow",
                    modifier = Modifier.clickable {
                        onArrowClick.invoke()
                    }
                    )
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = {
                    focusManager.clearFocus()
                    if(searchStr.isNotEmpty() && searchStr.isNotBlank()) onSearchClick.invoke(searchStr)
                }
            ),
            value = searchStr,
            onValueChange = {
                searchStr = it
            }
        )
    }
}