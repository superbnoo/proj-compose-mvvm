package com.example.jetpackmvvm.ui.views.recipe_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
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
import com.example.jetpackmvvm.BaseApplication
import com.example.jetpackmvvm.ui.components.*
import com.example.jetpackmvvm.ui.theme.AppTheme
import com.example.jetpackmvvm.util.TAG
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeListFragment: Fragment() {

    private val viewModel: RecipeListViewModel by viewModels()

    @Inject
    lateinit var application: BaseApplication

    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return ComposeView(requireContext()).apply {
            setContent {
                
                AppTheme(darkTheme = application.isDark.value) {
                    val recipes = viewModel.recipes.value
                    //  val (query, setQuery) = remember { mutableStateOf("beef") }
                    val query = viewModel.query.value

                    val selectedCategory = viewModel.selectedCategory.value

                    val loading = viewModel.loading.value

                    val page = viewModel.page.value

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChange = viewModel::onQueryChange,
                                onExecuteSearch = viewModel::newSearch,
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChange = viewModel::onSelectedCategoryChange,
                                onToggleTheme = {
                                    application.toggleTheme()
                                }
                            )
                        },
                        content = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(color = MaterialTheme.colors.background)
                            ) {
                                if (loading && recipes.isEmpty()) {
                                    LoadingRecipeListShimmer(imageHeight = 250.dp)
                                }

                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        viewModel.onChangeRecipeScrollPosition(index)
                                        if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                                            // Log.d(TAG, "position on scroll - before calling nextPage $index")
                                            viewModel.nextPage()
                                        }
                                        RecipeCard(recipe = recipe, onClick = {})
                                    }
                                }

                                CircularProgressBar(loading, 0.3f)
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun MyBottomBar() {
    BottomNavigation(
        elevation = 12.dp
    ) {
        BottomNavigationItem(
            icon = {
                (Icon(Icons.Default.ArrowBack, contentDescription = null))
            },
            selected = true,
            onClick = {  }
        )
        BottomNavigationItem(
            icon = {
                (Icon(Icons.Default.Search, contentDescription = null))
            },
            selected = false,
            onClick = {  }
        )
        BottomNavigationItem(
            icon = {
                (Icon(Icons.Default.AccountCircle, contentDescription = null))
            },
            selected = false,
            onClick = {  }
        )
    }
}

@Composable
fun MyDrawer() {
    Column() {
        Text("Item1")
        Text("Item1")
        Text("Item1")
        Text("Item1")


    }
}




