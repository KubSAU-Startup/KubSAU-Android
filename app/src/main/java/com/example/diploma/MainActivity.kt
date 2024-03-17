package com.example.diploma

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.diploma.common.navigation.graphs.RootGraph
import com.example.diploma.ui.theme.DiplomaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiplomaTheme {
                RootGraph()
            }
        }
    }
}