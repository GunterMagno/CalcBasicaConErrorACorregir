package org.example.utils

import java.sql.Connection
import java.time.LocalDateTime

interface ControlBaseDatos {
    fun inicializar()
    fun obtenerConexion(): Connection
    fun registrarOperacion(operacion: String, resultado: Double, fechaHora: LocalDateTime)
    fun obtenerHistorial(): List<String>
}