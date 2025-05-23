package org.example.service

import org.example.model.Operacion

/**
 * Interfaz que define los métodos necesarios para registrar operaciones y obtener el historial de operaciones.
 * Se utiliza para gestionar las operaciones realizadas dentro de la aplicación.
 */
interface IOperService {

    /**
     * Registra una nueva operación en el sistema.
     *
     * @param operacion La operación a registrar.
     */
    fun registrarOperacion(operacion: Operacion)

    /**
     * Obtiene el historial de operaciones realizadas.
     *
     * @return Una lista de todas las operaciones registradas.
     */
    fun obtenerHistorial(): List<Operacion>
}