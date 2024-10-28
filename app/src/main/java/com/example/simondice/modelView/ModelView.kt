package com.example.simondice.modelView

import android.util.Log
import com.example.simondice.datos.Secuencia
import com.example.simondice.datos.SimonColor

class ModelView {


    fun Start(record: Int): List<SimonColor> {
        return Secuencia(record).random.also { sequence ->
            Log.i("Secuencia", "Secuencia: ${sequence.joinToString(", ") { it.value.toString() }}")
        }
    }

    fun Comprobar(sequence: List<SimonColor>, sequenceUser: List<SimonColor>): Boolean {
        for (i in sequenceUser.indices) {
            if (sequenceUser[i] != sequence[i]) {
                Log.d("Respuesta", "Incorrecto en posición ${i + 1}: ${sequenceUser[i]} != ${sequence[i]}")
                return false
            }
        }
        return true
    }
}