package com.example.jetpackmvvm.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.jetpackmvvm.ui.views.recipe_list.FoodCategory
import com.example.jetpackmvvm.ui.views.recipe_list.getAllFoodCategories

@ExperimentalComposeUiApi
@Composable
fun SearchAppBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onExecuteSearch: () -> Unit,
    selectedCategory: FoodCategory?,
    onSelectedCategoryChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Surface(
        elevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
        color = Color.White,
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextField(
                    value = query,
                    modifier = Modifier
                        .fillMaxWidth(0.95f)
                        .padding(8.dp),
                    onValueChange = { newValue ->
                        onQueryChange(newValue)
                    },
                    label = {
                        Text(text = "Search")
                    },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    leadingIcon = {
                        Icon(Icons.Filled.Search, contentDescription = null)
                    },
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onExecuteSearch()
                            keyboardController?.hideSoftwareKeyboard()
                        },
                    ),
                    textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                    colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                )
            }
            val scrollState = rememberLazyListState()
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 8.dp),
                state = scrollState,
            ) {
                items(getAllFoodCategories()){ category ->
                    FoodCategoryChip(
                        category = category.value,
                        isSelected = category == selectedCategory,
                        onExecuteSearch = {
                            onExecuteSearch()
                        },
                        onSelectedCategoryChange = {
                            onSelectedCategoryChange(it)
                        }
                    )
                }
            }
        }
    }
}