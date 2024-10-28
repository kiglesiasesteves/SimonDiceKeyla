package com.example.simondice.UI

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.simondice.datos.Ronda
import com.example.simondice.datos.SimonColor
import com.example.simondice.modelView.ModelView
import kotlinx.coroutines.delay
import com.example.simondice.R


class IU {

    @SuppressLint("MutableCollectionMutableState")
    companion object {
        @Composable
        fun SimonGameScreen(ronda: Ronda) {
        var sequence by remember { mutableStateOf(ModelView().Start(record = ronda.record)) }
        val sequenceUser by remember { mutableStateOf(mutableListOf<SimonColor>()) }
        var feedbackMessage by remember { mutableStateOf("") }
        var currentSequenceIndex by remember { mutableIntStateOf(-1) }
        var isSequenceActive by remember { mutableStateOf(false) }
        val aContext= LocalContext.current

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

                SimonButtons(sequence, currentSequenceIndex) { color ->
                    sequenceUser.add(color)
                    Log.d("Secuencia Usuario", "Esta es la secuencia: ${sequenceUser.joinToString(", ") { it.value.toString() }}")

                    if (ModelView().Comprobar(sequence, sequenceUser)) {
                        feedbackMessage = "Correcto!"
                        if (sequenceUser.size == sequence.size) {
                            Log.d("Juego", "¡Secuencia completa! Generando nueva secuencia.")
                            val text="WIN"
                            ronda.incrementarRecord()
                            val toast = Toast.makeText( aContext, text, Toast.LENGTH_SHORT)
                            toast.show()
                            sequence = ModelView().Start(record = ronda.record)
                            sequenceUser.clear()
                            isSequenceActive = true
                        }
                    } else {
                        val text= "Game Over"
                        val toast = Toast.makeText(aContext, text, Toast.LENGTH_SHORT)
                        toast.show()
                        feedbackMessage = "Incorrecto. Vuelve a Empezar."
                        ronda.record=0
                        sequenceUser.clear()
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                StartButton(modifier = Modifier.padding(top = 16.dp)) {
                    sequence = ModelView().Start(record = ronda.record)
                    sequenceUser.clear()
                    feedbackMessage = ""
                    Log.d("Juego", "Nueva secuencia: ${sequence.joinToString(", ") { it.value.toString() }}")
                    isSequenceActive = true
                }

                Spacer(modifier = Modifier.height(32.dp))

                PuntuactionButton(ronda) {
                    Log.d("Puntuación", "Nueva puntuación: ${ronda.record}")
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = feedbackMessage,
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Red)
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
    fun PuntuactionButton(ronda: Ronda, modifier: Modifier = Modifier, onClick: () -> Unit ) {
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
                    text = "Puntuación: ${ronda.record}", fontFamily = FontFamily.Cursive, fontSize = 25.sp,
                    style = MaterialTheme.typography.bodyLarge.copy(Color.Black),
                    modifier = Modifier.align(Alignment.Center).padding(end = 45.dp)
                )
            }
}}}}
