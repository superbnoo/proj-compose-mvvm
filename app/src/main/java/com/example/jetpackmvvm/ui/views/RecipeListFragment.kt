package com.example.jetpackmvvm.ui.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.fragment.app.Fragment
import com.example.jetpackmvvm.R
import com.example.jetpackmvvm.ui.external.HorizontalDottedProgress

class RecipeListFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        val view = ComposeView(requireContext())
//        view.apply {
//            setContent {
//                Text("hey you")
//            }
//        }

        val view = inflater.inflate(R.layout.fragment_recipe_list, container, false)
        view.findViewById<ComposeView>(R.id.compose_view).setContent {
            Column(
                modifier = Modifier
                    .border(1.dp, Color.Black)
                    .padding(16.dp)
            ) {
                Text("THIS IS A COMPOSABLE INSIDE THE FRAGMENT")
                Spacer(modifier = Modifier.padding(10.dp))
                CircularProgressIndicator()
                Spacer(modifier = Modifier.padding(10.dp))
                Text("NEAT")
                Spacer(modifier = Modifier.padding(10.dp))
                AndroidView(
                    factory = { context ->
                        HorizontalDottedProgress(context)
                    },
                    update = {}
                )
            }
        }
        return view
    }
}