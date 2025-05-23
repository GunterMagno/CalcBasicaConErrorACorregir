package org.example.data.db

import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

/**
 * Objeto singleton que gestiona la conexión a la base de datos H2.
 * Proporciona métodos para obtener y cerrar una conexión a la base de datos.
 */
object Database {
    private const val URL = "jdbc:h2:./data/calcdb"
    private const val USUARIO = "sa"
    private const val CONTRASENIA = ""

    /**
     * Obtiene una conexión a la base de datos utilizando las credenciales definidas.
     *
     * @return La conexión a la base de datos, o `null` si ocurrió un error al intentar establecerla.
     */
    fun obtenerConexion(): Connection? {
        var conexion: Connection?
        try {
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENIA)
        } catch (e: SQLException) {
            println("Error en la conexión: ${e.message}")
            conexion = null
        } catch (e: ClassNotFoundException) {
            println("No se encontró el driver JDBC: ${e.message}")
            conexion = null
        }
        return conexion
    }

    /**
     * Cierra una conexión a la base de datos de manera segura.
     *
     * @param conexion La conexión a cerrar.
     */
    fun cerrarConexion(conexion: Connection) {
        try {
            conexion.close()
        } catch (e: SQLException) {
            println("Error al cerrar la conexión: ${e.message}")
        }
    }
}