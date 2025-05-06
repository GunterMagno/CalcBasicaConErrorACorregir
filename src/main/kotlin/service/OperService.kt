package org.example.service

import org.example.data.dao.IOperaciones

class OperService(private val gestionOperaciones: IOperaciones): IOperService {

    override fun registrarOperacion(operacion: String, resultado: Double) {
        gestionOperaciones.registrarOperacion(operacion,resultado)
    }

    override fun obtenerHistorial(): List<String> {
        return gestionOperaciones.obtenerHistorial()
    }
}