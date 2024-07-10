package com.kubsau.regrab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.kubsau.regrab.common.navigation.graphs.RootGraph
import com.kubsau.regrab.ui.theme.DiplomaTheme

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
