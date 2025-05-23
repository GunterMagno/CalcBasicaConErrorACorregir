package org.example.data.dao

import org.example.model.Operacion

interface IOperaciones {
    fun registrarOperacion(operacion: Operacion)
    fun obtenerHistorial(): List<Operacion>
}