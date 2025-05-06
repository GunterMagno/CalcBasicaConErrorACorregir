package org.example.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

object Database {
    private const val URL = "jdbc:h2:./data/calcdb"
    private const val USUARIO = "sa"
    private const val CONTRASENIA = ""

    fun obtenerConexion(): Connection? {
        var conexion: Connection?
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA)
            //println("Conexión abierta")
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
            conexion = null
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
            conexion = null
        }
        return conexion
    }

    fun cerrarConexion(conexion: Connection) {
        try {
            conexion.close()
            //println("Conexión cerrada")
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        }
    }
}