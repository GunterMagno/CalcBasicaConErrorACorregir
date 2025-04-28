package org.example.utils

interface ControlFichero {
    fun registrarLog(mensaje: String,rutaLogs: String)
    fun listarArchivos(ruta: String): String
    fun leerArchivo(ruta: String): List<String>
    fun agregarLinea(ruta: String, linea: String): Boolean
    fun escribirArchivo(ruta: String, elementos: List<String>): Boolean
    fun crearDirectorio(ruta: String)
    fun existeFichero(ruta: String): Boolean
    fun existeDirectorio(ruta: String): Boolean
}