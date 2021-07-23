package com.example.jetpackmvvm.ui.components

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.jetpackmvvm.domain.model.Recipe
import com.example.jetpackmvvm.ui.util.SnackbarController
import com.example.jetpackmvvm.ui.views.recipe_list.PAGE_SIZE
import com.example.jetpackmvvm.ui.views.recipe_list.RecipeListEvent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecipeList(
    loading: Boolean,
    recipes: List<Recipe>,
    page: Int,
    onChangeRecipeScrollPosition: (Int) -> Unit,
    onTriggerEvent: (RecipeListEvent) -> Unit,
    scaffoldState: ScaffoldState,
    snackbarController: SnackbarController,
    navigateToRecipeItem: (Bundle) -> Unit
) {
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
                onChangeRecipeScrollPosition(index)
                if ((index + 1) >= (page * PAGE_SIZE) && !loading) {
                    // Log.d(TAG, "position on scroll - before calling nextPage $index")
                    onTriggerEvent(RecipeListEvent.NextPageEvent)
                }
                RecipeCard(
                    recipe = recipe,
                    onClick = {
                        if (recipe.id != null) {
                            val bundle = Bundle()
                            bundle.putInt("recipeId", recipe.id)
                            navigateToRecipeItem(bundle)
                        } else {
                            snackbarController.getScope().launch {
                                snackbarController.showSnackbar(
                                    scaffoldState = scaffoldState,
                                    message = "Recipe Error",
                                    actionLabel = "Ok"
                                )
                            }
                        }
                    }
                )
            }
        }

        CircularProgressBar(loading, 0.3f)

        DefaultSnackbar(
            snackbarHostState = scaffoldState.snackbarHostState,
            onDismiss = {
                scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
            },
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}