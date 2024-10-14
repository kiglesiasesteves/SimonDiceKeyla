package com.example.simondice

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.simondice.ui.theme.SimonDiceTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SimonDiceTheme {
                SimonGameScreen()
            }
        }
    }
}

@Composable
fun SimonGameScreen() {
    var sequence by remember { mutableStateOf(CreateSequenceGame()) }
    var sequenceUser by remember { mutableStateOf(mutableListOf<SimonColor>()) }
    var feedbackMessage by remember { mutableStateOf("") }
    var currentSequenceIndex by remember { mutableStateOf(-1) }
    var isSequenceActive by remember { mutableStateOf(false) }

    LaunchedEffect(isSequenceActive) {
        if (isSequenceActive) {
            for (index in sequence.indices) {
                currentSequenceIndex = index
                delay(1000)
                currentSequenceIndex = -1
                delay(500)
            }
            sequenceUser.clear()
            isSequenceActive = false
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
            .background(Color(236, 236, 221)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SimonButtons(sequence, currentSequenceIndex) { color ->
            sequenceUser.add(color)
            Log.d("Secuencia Usuario", "Esta es la secuencia: ${sequenceUser.joinToString(", ") { it.value.toString() }}")

            if (CompararSecuenciaUsuario(sequence, sequenceUser)) {
                feedbackMessage = "Correcto!"
                if (sequenceUser.size == sequence.size) {
                    Log.d("Juego", "¡Secuencia completa! Generando nueva secuencia.")
                    sequence = CreateSequenceGame()
                    sequenceUser.clear()
                    isSequenceActive = true
                }
            } else {
                feedbackMessage = "Incorrecto. Vuelve a Empezar."
                sequenceUser.clear()
            }
        }

        StartButton(modifier = Modifier.padding(top = 100.dp)) {
            sequence = CreateSequenceGame()
            sequenceUser.clear()
            feedbackMessage = ""
            Log.d("Juego", "Nueva secuencia: ${sequence.joinToString(", ") { it.value.toString() }}")
            isSequenceActive = true
        }

        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = feedbackMessage,
            style = MaterialTheme.typography.bodyLarge.copy(Color.Red)
        )
    }
}

@Composable
fun SimonButtons(sequence: List<SimonColor>, currentSequenceIndex: Int, onColorClick: (SimonColor) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorButton(
                imageResId = if (sequence.getOrNull(currentSequenceIndex) == SimonColor.Red) R.drawable.brosa1iluminado else R.drawable.brosa1,
                onClick = { onColorClick(SimonColor.Red) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            ColorButton(
                imageResId = if (sequence.getOrNull(currentSequenceIndex) == SimonColor.Blue) R.drawable.bazul2iluminado else R.drawable.bazul2,
                onClick = { onColorClick(SimonColor.Blue) }
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ColorButton(
                imageResId = if (sequence.getOrNull(currentSequenceIndex) == SimonColor.Yellow) R.drawable.bamarillo3iluminado else R.drawable.bamarillo3,
                onClick = { onColorClick(SimonColor.Yellow) }
            )
            Spacer(modifier = Modifier.width(16.dp))
            ColorButton(
                imageResId = if (sequence.getOrNull(currentSequenceIndex) == SimonColor.Green) R.drawable.bverde4iluminado else R.drawable.bverde4,
                onClick = { onColorClick(SimonColor.Green) }
            )
        }
    }
}
@Composable
fun ColorButton(imageResId: Int, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = imageResId),
        contentDescription = null,
        modifier = modifier
            .size(150.dp)
            .clickable { onClick() }
    )
}

@Composable
fun StartButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.size(200.dp, 60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECECDD))
    ) {
        Image(
            painter = painterResource(id = R.drawable.bstart),
            contentDescription = null,
            modifier = Modifier.size(150.dp)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonDiceTheme {
        SimonGameScreen()
    }
}

fun CreateSequenceGame(): List<SimonColor> {
    return List(4) { SimonColor.values().random() }.also {
        Log.i("Secuencia", "Secuencia: ${it.joinToString(", ") { it.value.toString() }}")
    }
}

fun CompararSecuenciaUsuario(sequence: List<SimonColor>, sequenceUser: List<SimonColor>): Boolean {
    for (i in sequenceUser.indices) {
        if (sequenceUser[i] != sequence[i]) {
            Log.d("Respuesta", "Incorrecto en posición ${i + 1}: ${sequenceUser[i]} != ${sequence[i]}")
            return false
        }
    }
    return true
}
