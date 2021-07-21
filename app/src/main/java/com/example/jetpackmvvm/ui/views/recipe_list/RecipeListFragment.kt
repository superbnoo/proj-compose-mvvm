package com.example.jetpackmvvm.ui.views.recipe_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.jetpackmvvm.ui.components.CircularProgressBar
import com.example.jetpackmvvm.ui.components.FoodCategoryChip
import com.example.jetpackmvvm.ui.components.RecipeCard
import com.example.jetpackmvvm.ui.components.SearchAppBar
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

                val recipes = viewModel.recipes.value
                //  val (query, setQuery) = remember { mutableStateOf("beef") }
                val query = viewModel.query.value

                val selectedCategory = viewModel.selectedCategory.value

                val loading = viewModel.loading.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChange = viewModel::onQueryChange,
                        onExecuteSearch = viewModel::newSearch,
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChange = viewModel::onSelectedCategoryChange
                    )

                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        LazyColumn {
                            itemsIndexed(
                                items = recipes
                            ) { index, recipe ->
                                RecipeCard(recipe = recipe, onClick = {})
                            }
                        }

                        CircularProgressBar(loading, 0.3f)
                    }
                }
            }
        }
    }
}