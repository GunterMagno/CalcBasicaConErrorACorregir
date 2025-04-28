package org.example.utils

import java.sql.*
import java.time.LocalDateTime

class GestionBaseDatosH2 : ControlBaseDatos {
    private val url = ""
    private val usuario = ""
    private val contraseña = ""

    override fun obtenerConexion(): Connection? {
        var conexion: Connection? = null
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            conexion = DriverManager.getConnection(url, usuario, contraseña)
            return conexion
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
            return null
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
            return null
        } finally {
            try {
                conexion?.close()
                println("Conexión cerrada")
            } catch (e: SQLException) {
                println("Error al cerrar la conexión: ${e.message}")
            }
        }
    }

    override fun inicializar() {
        obtenerConexion()?.use { conn ->
            val stmt = conn.createStatement()
            stmt.executeUpdate(
                """
                CREATE TABLE IF NOT EXISTS operaciones (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    operacion VARCHAR(50) NOT NULL,
                    resultado DOUBLE NOT NULL,
                    fecha_hora TIMESTAMP NOT NULL
                )""".trimIndent()
            )
            stmt.close()
        }
    }

    override fun registrarOperacion(operacion: String, resultado: Double, fechaHora: LocalDateTime) {
        obtenerConexion()?.use { conn ->
            conn.prepareStatement("""
                INSERT INTO operaciones (operacion, resultado, fecha_hora)
                VALUES (?, ?, ?)
            """.trimIndent()).use { stmt ->
                stmt.setString(1, operacion)
                stmt.setDouble(2, resultado)
                stmt.setTimestamp(3, Timestamp.valueOf(fechaHora))
                stmt.executeUpdate()
            }
        }
    }

    override fun obtenerHistorial(): List<String> {
        val historial = mutableListOf<String>()

        obtenerConexion()?.use { conn ->
            conn.createStatement().executeQuery("""
                SELECT operacion, resultado, fecha_hora 
                FROM operaciones 
                ORDER BY fecha_hora DESC
            """.trimIndent()).use {

            }
        }

        return historial
    }
}