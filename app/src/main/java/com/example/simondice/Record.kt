package com.example.simondice

 data class Record(var record: Int) {

     fun incrementarRecord(record: Record) {
         record.record++
     }

     var nombre: String = ""
}