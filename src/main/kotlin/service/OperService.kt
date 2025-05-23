package org.example.service

import org.example.data.dao.IOperaciones
import org.example.model.Operacion

class OperService(private val gestionOperaciones: IOperaciones): IOperService {

    override fun registrarOperacion(operacion: Operacion) {
        gestionOperaciones.registrarOperacion(operacion)
    }

    override fun obtenerHistorial(): List<Operacion> {
        return gestionOperaciones.obtenerHistorial()
    }
}