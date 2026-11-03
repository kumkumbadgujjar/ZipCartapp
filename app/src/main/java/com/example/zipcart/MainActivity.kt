package com.example.zipcart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.zipcart.frontend.navigation.Screen
import com.example.zipcart.frontend.navigation.ZipCartNavGraph
import com.example.zipcart.ui.theme.ZipCartTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ZipCartTheme {
                ZipCartNavGraph(startDestination = Screen.Onboarding.route)
            }
        }
    }
}
