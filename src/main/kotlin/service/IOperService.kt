package org.example.service

import org.example.model.Operacion

interface IOperService {
    fun registrarOperacion(operacion: Operacion)
    fun obtenerHistorial(): List<Operacion>
}