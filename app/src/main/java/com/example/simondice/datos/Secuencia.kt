package com.example.simondice.datos

class Secuencia(private val record: Int) {

    val random: List<SimonColor>
        get() {
            val randomColors: MutableList<SimonColor> = ArrayList()
            for (i in 0..record) {
                val randomColor = SimonColor.entries.toTypedArray().random()
                randomColors.add(randomColor)
            }
            return randomColors
        }
}