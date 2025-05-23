package org.example.model

import java.sql.Timestamp

data class Operacion(val operacion: String, val resultado: Double, val fecha: Timestamp){
    override fun toString(): String {
        return "Operacion -> $operacion, Resultado = $resultado\n\t\t\tFecha = $fecha"
    }
}