package com.example.simondice

import AppDatabase
import android.os.Bundle
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simondice.ui.theme.SimonDiceTheme
import kotlinx.coroutines.delay
import android.util.Log

class MainActivity : ComponentActivity() {
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(this)
        // Habilitar el modo edge-to-edge
        enableEdgeToEdge()

        // Recibir el nombre de usuario desde LoginActivity
        val userName = intent.getStringExtra("userName") // Puede ser null si no se pasa

        setContent {
            SimonDiceTheme {
                // Pasar el nombre de usuario a SimonGameScreen
                SimonGameScreen(Record(0), userName)
            }
        }
    }
}

@Composable
fun SimonGameScreen(record: Record, userName: String?) {
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(236, 236, 221)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            // Mostrar el nombre de usuario
            Text(
                text = "Jugador: ${userName ?: "Jugador no identificado"}",
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            SimonButtons(sequence, currentSequenceIndex) { color ->
                sequenceUser.add(color)

                if (CompararSecuenciaUsuario(sequence, sequenceUser)) {
                    feedbackMessage = "Correcto!"
                    if (sequenceUser.size == sequence.size) {
                        sequence = CreateSequenceGame()
                        sequenceUser.clear()
                        isSequenceActive = true
                    }
                } else {
                    feedbackMessage = "Incorrecto. Vuelve a Empezar."
                    sequenceUser.clear()
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            StartButton(modifier = Modifier.padding(top = 16.dp)) {
                sequence = CreateSequenceGame()
                sequenceUser.clear()
                feedbackMessage = ""
                isSequenceActive = true
            }

            Spacer(modifier = Modifier.height(32.dp))

            PuntuactionButton {
                // Aquí puedes gestionar la puntuación si es necesario
                Log.d("Puntuación", "Puntuación actual: ${record.record}")
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = feedbackMessage,
                style = MaterialTheme.typography.bodyLarge.copy(Color.Red)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Aciertos: ${record.record}",
                style = MaterialTheme.typography.bodyLarge.copy(Color.Black)
            )
        }
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

@Composable
fun PuntuactionButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier.size(500.dp, 100.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFECECDD)),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.bpuntuacion),
                contentDescription = null,
                modifier = Modifier.size(300.dp).align(Alignment.Center)
            )
            Text(
                text = "Puntuación: ", fontFamily = FontFamily.Serif, fontSize = 20.sp,
                style = MaterialTheme.typography.bodyLarge.copy(Color.Black),
                modifier = Modifier.align(Alignment.Center).padding(end = 85.dp)
            )
        }
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SimonDiceTheme {
        SimonGameScreen(Record(0), "Jugador de Prueba")
    }
}
