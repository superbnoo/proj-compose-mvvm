package com.example.jetpackmvvm.ui.views.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.jetpackmvvm.BaseApplication
import com.example.jetpackmvvm.ui.components.RecipeView
import com.example.jetpackmvvm.ui.theme.AppTheme
import com.example.jetpackmvvm.ui.views.recipe_list.RecipeListViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RecipeFragment: Fragment() {
    @Inject
    lateinit var application: BaseApplication

    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let { rId ->
            viewModel.onTriggerEvent(RecipeEvent.GetRecipeEvent(rId))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDark.value) {
                    val loading = viewModel.loading.value
                    val recipe = viewModel.recipe.value

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        recipe?.let {
                            RecipeView(
                                recipe = recipe
                            )
                        } ?: Text(text = "Loading...")


                    }
                }
            }
        }
    }
}