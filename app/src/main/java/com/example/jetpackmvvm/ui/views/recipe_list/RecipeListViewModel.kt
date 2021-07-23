package com.example.jetpackmvvm.ui.views.recipe_list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackmvvm.domain.model.Recipe
import com.example.jetpackmvvm.repository.RecipeRepository
import com.example.jetpackmvvm.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Named

const val PAGE_SIZE = 30

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel(){

    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    val query = mutableStateOf("")

    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)

    val loading = mutableStateOf(false)

    val page = mutableStateOf(1)

    private var recipeListScrollPosition = 0

    init {
        onTriggerEvent(RecipeListEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event){
                    is RecipeListEvent.NewSearchEvent -> {
                        newSearch()
                    }
                    is RecipeListEvent.NextPageEvent -> {
                        nextPage()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent exception: $e , ${e.cause}")
            }
        }
    }

    // useCase 1
    private suspend fun newSearch() {
        loading.value = true
        resetSearchState()
        // delay(2000)
        val result = repository.search(
            token = token,
            page = 1,
            query = query.value
        )
        recipes.value = result
        loading.value = false
    }

    // useCase 2
    private suspend fun nextPage() {
        // prevent duplicate events due to recompose happen quickly
        if ((recipeListScrollPosition + 1) >= page.value * PAGE_SIZE) {
            loading.value = true
            incrementPage()
            Log.d(TAG, "nextPage: triggered: ${page.value}")
            delay(1000)
            if (page.value > 1) {
                val result = repository.search(
                    token = token,
                    page = page.value,
                    query = query.value
                )
                Log.d(TAG, "nextPage: triggered: ${result}")
                appendRecipes(result)
            }
            loading.value = false
        }
    }

    /*
     * Append new recipes to the current list of recipes
     */
    private fun appendRecipes(recipes: List<Recipe>) {
        val current = ArrayList(this.recipes.value)
        current.addAll(recipes)
        this.recipes.value = current
    }

    private fun incrementPage() {
        page.value = page.value + 1
    }

    fun onChangeRecipeScrollPosition(position: Int) {
        recipeListScrollPosition = position
        Log.d(TAG, "position on scroll $position")
    }

    fun onQueryChange(query: String) {
        this.query.value = query
    }

    fun onSelectedCategoryChange(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChange(category)
    }

    private fun clearSelectedCateogry() {
        selectedCategory.value = null
    }

    private fun resetSearchState() {
        recipes.value = listOf()
        page.value = 1
        onChangeRecipeScrollPosition(0)
        if (selectedCategory.value?.value != query.value) {
            clearSelectedCateogry()
        }
    }




}