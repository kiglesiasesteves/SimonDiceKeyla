package com.example.simondice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.simondice.UI.IU
import com.example.simondice.datos.Ronda
import com.example.simondice.ui.theme.SimonDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonDiceTheme {
                IU.SimonGameScreen(Ronda(0))
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonDiceTheme {
        IU.SimonGameScreen(Ronda(0))
    }
}




