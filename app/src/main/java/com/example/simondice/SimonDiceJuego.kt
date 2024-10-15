package com.example.simondice

import android.util.Log

class SimonDiceJuego {

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
}