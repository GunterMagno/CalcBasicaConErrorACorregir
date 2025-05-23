package org.example.data.dao

import org.example.data.db.Database
import org.example.model.Operacion
import java.sql.SQLException
import java.sql.Timestamp

class OperacionesDAOH2: IOperaciones {
    override fun registrarOperacion(operacion: Operacion) {

        val conexion = Database.obtenerConexion()

        try {
            val stmt = conexion?.prepareStatement(
                """
                INSERT INTO operaciones (operacion, resultado, fecha_hora)
                VALUES (?, ?, ?)
                """.trimIndent()
            )

            stmt?.setString(1, operacion.operacion)
            stmt?.setDouble(2, operacion.resultado)
            stmt?.setTimestamp(3, operacion.fecha)
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

    override fun obtenerHistorial(): List<Operacion> {
        val historial = mutableListOf<Operacion>()

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
                historial.add(Operacion(query.getString("operacion"),query.getDouble("resultado"),query.getTimestamp("fecha_hora")))
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

//    fun inicializar() {
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