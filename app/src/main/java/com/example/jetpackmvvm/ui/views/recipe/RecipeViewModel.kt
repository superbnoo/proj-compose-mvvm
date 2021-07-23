package com.example.jetpackmvvm.ui.views.recipe

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetpackmvvm.domain.model.Recipe
import com.example.jetpackmvvm.repository.RecipeRepository
import com.example.jetpackmvvm.util.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
): ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading = mutableStateOf(false)


    fun onTriggerEvent(event: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is RecipeEvent.GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(event.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggeredEvent: Exception $e, ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(id: Int) {
        loading.value = true

        delay(1000)
        val recipe = repository.get(token = token, id = id)

        this.recipe.value = recipe

        loading.value = false
    }
}