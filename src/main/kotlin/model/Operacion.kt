package org.example.model

import java.sql.Timestamp

/**
 * Clase de datos que representa una operación matemática realizada.
 *
 * @param operacion Expresión matemática que describe la operación (e.g., "3 + 5").
 * @param resultado El resultado de la operación matemática.
 * @param fecha La fecha y hora en la que se realizó la operación.
 */
data class Operacion(val operacion: String, val resultado: Double, val fecha: Timestamp) {

    /**
     * Sobrescribe el método [toString] para proporcionar una representación legible de la operación.
     *
     * @return Cadena que representa la operación, su resultado y la fecha en la que fue realizada.
     */
    override fun toString(): String {
        return "Operacion -> $operacion, Resultado = $resultado\n\t\t\tFecha = $fecha"
    }
}