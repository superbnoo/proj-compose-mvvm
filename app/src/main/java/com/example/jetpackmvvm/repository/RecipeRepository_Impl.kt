package com.example.jetpackmvvm.repository

import com.example.jetpackmvvm.domain.model.Recipe
import com.example.jetpackmvvm.network.RecipeService
import com.example.jetpackmvvm.network.model.RecipeDtoMapper

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
): RecipeRepository {


    override suspend fun search(token: String, page: Int, query: String): List<Recipe> {
        val result = recipeService.search(token, page, query).recipes
        return mapper.toDomainList(result)
    }

    override suspend fun get(token: String, id: Int): Recipe {
        return mapper.mapToDomainModel(recipeService.get(token, id))
    }
}