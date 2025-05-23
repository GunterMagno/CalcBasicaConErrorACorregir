package org.example.data.dao

import org.example.model.Operacion

/**
 * Interfaz que define las operaciones de acceso a datos relacionadas con las operaciones matemáticas.
 * Esta interfaz proporciona métodos para registrar una operación y obtener el historial de operaciones.
 */
interface IOperaciones {

    /**
     * Registra una nueva operación en el sistema.
     *
     * @param operacion La operación matemática que se va a registrar.
     */
    fun registrarOperacion(operacion: Operacion)

    /**
     * Obtiene el historial de operaciones realizadas en el sistema.
     *
     * @return Una lista de objetos [Operacion] que representan las operaciones realizadas.
     */
    fun obtenerHistorial(): List<Operacion>
}