package com.example.jetpackmvvm.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout


@Composable
fun CircularProgressBar(
    isDisplay: Boolean,
    verticalBias: Float
) {
    if (isDisplay) {
        ConstraintLayout(
            modifier = Modifier.fillMaxSize()
        ) {
            val (progressBar) = createRefs()
            val topBias = createGuidelineFromTop(verticalBias)
            CircularProgressIndicator(
                modifier = Modifier.constrainAs(progressBar)
                {
                    top.linkTo(topBias)
                    end.linkTo(parent.end)
                    start.linkTo(parent.start)
                },
                color = MaterialTheme.colors.primary
            )
        }




//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(50.dp),
//            horizontalArrangement = Arrangement.Center
//        ) {
//            CircularProgressIndicator(
//                color = MaterialTheme.colors.primary
//            )
//        }
    }
}

