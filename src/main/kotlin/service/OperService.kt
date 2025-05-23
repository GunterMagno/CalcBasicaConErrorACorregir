package org.example.service

import org.example.data.dao.IOperaciones
import org.example.model.Operacion

/**
 * Servicio encargado de gestionar las operaciones a través de la interfaz [IOperaciones],
 * proporcionando métodos para registrar operaciones y obtener el historial de las mismas.
 *
 * @param gestionOperaciones Instancia de [IOperaciones] para realizar operaciones sobre los datos.
 */
class OperService(private val gestionOperaciones: IOperaciones): IOperService {

    /**
     * Registra una operación en el sistema.
     *
     * @param operacion La operación que se va a registrar.
     */
    override fun registrarOperacion(operacion: Operacion) {
        gestionOperaciones.registrarOperacion(operacion)
    }

    /**
     * Obtiene el historial de operaciones realizadas.
     *
     * @return Una lista de operaciones registradas.
     */
    override fun obtenerHistorial(): List<Operacion> {
        return gestionOperaciones.obtenerHistorial()
    }
}