package org.example.utils

import java.sql.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestionBaseDatosH2 : ControlBaseDatos {
    private val url = "jdbc:h2:~/test"
    private val usuario = "usuario"
    private val contrasenia = "usuario"

    override fun obtenerConexion(): Connection {
        var conexion: Connection? = null
        try {
            Class.forName("com.mysql.cj.jdbc.Driver")
            conexion = DriverManager.getConnection(url, usuario, contrasenia)
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
        } finally {
            try {
                conexion?.close()
                println("Conexión cerrada")
            } catch (e: SQLException) {
                println("Error al cerrar la conexión: ${e.message}")
            }
        }
        return conexion!!
    }

    override fun inicializar() {
        obtenerConexion().use { conn ->
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

    override fun registrarOperacion(operacion: String, resultado: Double) {
        val fechaHora = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaFormateada = fechaHora.format(formatter)

        obtenerConexion().use { conn ->
            conn.prepareStatement("""
                    INSERT INTO operaciones (operacion, resultado, fecha_hora)
                    VALUES (?, ?, ?)
                """.trimIndent()).use { stmt ->
                stmt.setString(1, operacion)
                stmt.setDouble(2, resultado)
                stmt.setTimestamp(3, Timestamp.valueOf(fechaFormateada))
                stmt.executeUpdate()
            }
        }
    }

    override fun obtenerHistorial(): List<String> {
        val historial = mutableListOf<String>()

        obtenerConexion().use { conn ->
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
