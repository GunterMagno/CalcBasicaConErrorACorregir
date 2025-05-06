package org.example.data.dao

interface IOperaciones {
    fun registrarOperacion(operacion: String, resultado: Double)
    fun obtenerHistorial(): List<String>
}