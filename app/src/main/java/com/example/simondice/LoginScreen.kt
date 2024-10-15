package com.example.simondice

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.simondice.ui.theme.SimonDiceTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SimonDiceTheme {
                LoginScreen { userName ->
                    // Iniciar MainActivity y pasar el nombre de usuario
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("userName", userName)
                    }
                    startActivity(intent)
                }
            }
        }
    }
}

@Composable
fun LoginScreen(onLogin: (String) -> Unit) {
    var userName by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { onLogin(userName) }) {
            Text("Iniciar Juego")
        }
    }
}
