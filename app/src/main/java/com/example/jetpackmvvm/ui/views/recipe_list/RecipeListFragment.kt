package com.example.jetpackmvvm.ui.views.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackmvvm.ui.components.RecipeCard
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                val keyboardController = LocalSoftwareKeyboardController.current

                val recipes = viewModel.recipes.value
                //  val (query, setQuery) = remember { mutableStateOf("beef") }
                val query = viewModel.query.value

                Column {
                    Surface(
                        elevation = 8.dp,
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colors.primary,
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextField(
                                value = query,
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .padding(8.dp),
                                onValueChange = { newValue ->
                                    viewModel.onQueryChange(newValue)
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
                                        viewModel.newSearch(query)
                                        keyboardController?.hideSoftwareKeyboard()
                                    },
                                ),
                                textStyle = TextStyle(color = MaterialTheme.colors.onSurface),
                                colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colors.surface),
                            )
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { index, recipe ->
                            RecipeCard(recipe = recipe, onClick = {})
                        }
                    }
                }
            }
        }
    }
}