package org.example.data.dao

import org.example.data.db.Database
import java.sql.SQLException
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class OperacionesDAOH2: IOperaciones {
    override fun registrarOperacion(operacion: String, resultado: Double) {
        val fechaHora = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaFormateada = fechaHora.format(formatter)

        val conexion = Database.obtenerConexion()

        try {
            val stmt = conexion?.prepareStatement(
                """
                INSERT INTO operaciones (operacion, resultado, fecha_hora)
                VALUES (?, ?, ?)
                """.trimIndent()
            )

            stmt?.setString(1, operacion)
            stmt?.setDouble(2, resultado)
            stmt?.setTimestamp(3, Timestamp.valueOf(fechaFormateada))
            stmt?.executeUpdate()

            stmt?.close()

        } catch (e: SQLException){
            println("Error en la conexión: ${e.message}")
        } finally {
            if (conexion != null) {
                Database.cerrarConexion(conexion)
            }
        }
    }

    override fun obtenerHistorial(): List<String> {
        val historial = mutableListOf<String>()

        val conexion = Database.obtenerConexion()

        try {

            val stmt = conexion?.createStatement()

            val query = stmt?.executeQuery(
            """
                SELECT operacion, resultado, fecha_hora 
                FROM operaciones 
                ORDER BY fecha_hora DESC
            """.trimIndent()
            )

            while (query?.next() == true){
                var cadena = ""

                cadena = "${query.getString("operacion")} ${query.getString("resultado")} ${query.getString("fecha_hora")}"
                historial.add(cadena)
            }

        } catch (e: SQLException){
            println("Error en la conexión: ${e.message}")
        } finally {
            if (conexion != null) {
                Database.cerrarConexion(conexion)
            }
        }

        return historial
    }

//    fun inicializar() { //ToDo Arreglar esto ponerle un try catch ocn finally EL .USE ABRE Y CIERRA SOLO LOS STATMENT Y LAS CONEXIONES y utilizar la funcion cerrar conexion
//        val conexion = obtenerConexion()
//        try {
//            val smtm = conexion?.createStatement()
//            smtm?.executeUpdate(
//                """
//                CREATE TABLE IF NOT EXISTS operaciones (
//                    id INT AUTO_INCREMENT PRIMARY KEY,
//                    operacion VARCHAR(50) NOT NULL,
//                    resultado DOUBLE NOT NULL,
//                    fecha_hora TIMESTAMP NOT NULL
//                )""".trimIndent()
//            )
//            smtm?.close()
//
//        } catch (e: SQLException){
//            println("Error en la conexión: ${e.message}")
//        } finally {
//            if (conexion != null) {
//                cerrarConexion(conexion)
//            }
//        }
//    }
}