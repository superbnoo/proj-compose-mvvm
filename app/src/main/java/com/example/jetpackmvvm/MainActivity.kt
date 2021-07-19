package com.example.jetpackmvvm

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetpackmvvm.network.RecipeService
import com.example.jetpackmvvm.ui.theme.JetpackMVVMTheme
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val service = Retrofit.Builder()
            .baseUrl("https://food2fork.ca/api/recipe/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(RecipeService::class.java)

        CoroutineScope(IO).launch {
            val recipe = service.get(
                token = "Token 9c8b06d329136da358c2d00e76946b0111ce2c48",
                id = 583
            )
            Log.d("MainAct", "onCreate: ${recipe.title}")
        }
    }
}

@Composable
fun MainContent() {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        Image(
            painter = painterResource(id = R.drawable.happy_meal_small),
            contentDescription = "",
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop,
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Happy Meal",
                    style = TextStyle(
                        fontSize = 30.sp
                    )
                )
                Text(
                    text = "$5.99",
                    style = TextStyle(
                        color = Color(0xFF85bb65),
                        fontSize = 17.sp
                    ),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Spacer(
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = "800 Calories",
                style = TextStyle(
                    fontSize = 17.sp
                )
            )
            Spacer(
                modifier = Modifier.padding(top = 10.dp)
            )
            Button(
                onClick = {},
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text = "ORDER NOW"
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    JetpackMVVMTheme {
        MainContent()
    }
}