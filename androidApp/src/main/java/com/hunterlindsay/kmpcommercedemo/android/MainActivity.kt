package com.hunterlindsay.kmpcommercedemo.android

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.hunterlindsay.kmpcommercedemo.android.ui.KMPCommerceDemoTheme
import com.hunterlindsay.kmpcommercedemo.android.ui.introduction.OpeningScreen
import com.hunterlindsay.kmpcommercedemo.core.app_wiring.KMPCommerceDemoDependencies
import com.hunterlindsay.kmpcommercedemo.deprecated.Greeting
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //TEST
        val dependencies = KMPCommerceDemoDependencies()

        lifecycleScope.launch {
            dependencies.productService.loadProducts()
            dependencies.productService.loadCategories()
            val state = dependencies.productService.state.value

            Log.d("KMPCommerceDemo", "Products loaded: ${state.products.size}")
            Log.d("KMPCommerceDemo", "Categories loaded: ${state.categories.size}")
            Log.d("KMPCommerceDemo", "Error: ${state.errorMessage}")
        }

        setContent {
            KMPCommerceDemoTheme {
                val dependencies = remember {
                    KMPCommerceDemoDependencies()
                }

                LaunchedEffect(Unit) {
                    dependencies.productService.loadCategories()
                }

                OpeningScreen(
                    productService = dependencies.productService
                )
            }
        }
    }
}

@Composable
fun GreetingView(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        GreetingView("Hello, Android!")
    }
}
