package com.example.simondice.datos

data class Ronda(var record: Int) {


    fun incrementarRecord() {
        record++
    }

    var nombre: String = ""
}