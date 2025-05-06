package org.example.service

interface IOperService {
    fun registrarOperacion(operacion: String, resultado: Double)
    fun obtenerHistorial(): List<String>
}