package com.example.jetpackmvvm.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.example.jetpackmvvm.domain.model.Recipe
import com.example.jetpackmvvm.util.DEFAULT_RECIPE_IMAGE
import com.example.jetpackmvvm.util.loadPicture

@Composable
fun RecipeView(
    recipe: Recipe
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            recipe.featuredImage?.let { url ->
                val image = loadPicture(url, DEFAULT_RECIPE_IMAGE).value
                image?.let {
                    Image(
                        bitmap = image.asImageBitmap(),
                        contentDescription = "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(255.dp),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 4.dp)
                ){
                    Text(
                        text = recipe.title ?: "...",
                        modifier = Modifier
                            .fillMaxWidth(0.85f)
                            .wrapContentWidth(Alignment.Start)
                        ,
                        style = MaterialTheme.typography.h3
                    )
                    val rank = recipe.rating.toString()
                    Text(
                        text = rank,
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.End)
                            .align(Alignment.CenterVertically)
                        ,
                        style = MaterialTheme.typography.h5
                    )
                }
                val updated = recipe.dateUpdated
                Text(
                    text = "Updated ${updated} by ${recipe.publisher}"
                    ,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                    ,
                    style = MaterialTheme.typography.caption
                )
                for(ingredient in recipe.ingredients){
                    Text(
                        text = ingredient,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                        ,
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}