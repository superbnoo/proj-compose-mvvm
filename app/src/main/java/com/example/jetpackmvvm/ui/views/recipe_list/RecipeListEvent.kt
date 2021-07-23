package com.example.jetpackmvvm.ui.views.recipe_list

sealed class RecipeListEvent {

    object NewSearchEvent: RecipeListEvent()

    object NextPageEvent: RecipeListEvent()
}