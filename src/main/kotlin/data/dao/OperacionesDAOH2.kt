package org.example.data.dao

import org.example.data.db.Database
import org.example.model.Operacion
import java.sql.SQLException
import java.sql.Timestamp

/**
 * Implementación de [IOperaciones] para gestionar las operaciones en una base de datos H2.
 * Proporciona métodos para registrar operaciones y obtener el historial de operaciones.
 */
class OperacionesDAOH2: IOperaciones {

    /**
     * Registra una operación en la base de datos.
     *
     * @param operacion La operación que se va a registrar.
     * @throws SQLException Si ocurre un error al ejecutar la sentencia SQL.
     */
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

        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        } finally {
                if (conexion != null) {
                    Database.cerrarConexion(conexion)
                }
        }
    }


    /**
     * Obtiene el historial de operaciones registradas en la base de datos.
     *
     * @return Una lista de operaciones registradas, ordenadas por fecha (más recientes primero).
     * @throws SQLException Si ocurre un error al ejecutar la consulta SQL.
     */
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

        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        }catch (e: Exception) {
            println("Error inesperado: ${e.message}")
        } finally {
            if (conexion != null) {
                Database.cerrarConexion(conexion)
            }
        }

        return historial
    }
}
