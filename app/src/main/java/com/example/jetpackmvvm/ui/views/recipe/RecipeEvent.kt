package com.example.jetpackmvvm.ui.views.recipe

sealed class RecipeEvent {
    data class GetRecipeEvent(
        val id: Int
    ): RecipeEvent() {

    }
}