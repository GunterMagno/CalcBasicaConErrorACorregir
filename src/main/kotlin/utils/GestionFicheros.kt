package org.example.utils

import org.example.ui.IEntradaSalida
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class GestionFicheros(private val consola: IEntradaSalida): ControlFichero {

    override fun registrarLog(mensaje: String,rutaLogs: String) {

        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val fechaHora = LocalDateTime.now().format(formatter)

        val entradaLog = "[$fechaHora] $mensaje"
        val nombreArchivo = "log${LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))}.txt"

        val rutaCompleta = "$rutaLogs\\$nombreArchivo"

        if (!existeDirectorio(rutaLogs)) {
            crearDirectorio(rutaLogs)
        }

        agregarLinea(rutaCompleta, entradaLog)
    }

    override fun listarArchivos(ruta: String): String {
        if (existeDirectorio(ruta)) {
            val archivos = File(ruta).listFiles()
            if (archivos.isNullOrEmpty()) {
                consola.mostrar("No existen ficheros de Log")
            } else {
                consola.mostrar("${archivos.size} Archivos encontrados en $ruta:\n")
                var contador = 0
                for (archivo in archivos){
                    contador++
                    consola.mostrar("$contador - ${archivo.name}")
                }
                return archivos.last().path
            }
        }
        return ""
    }

    override fun leerArchivo(ruta: String): List<String> {
        val archivo = File(ruta)
        return try {
            if (archivo.exists()){
                archivo.readLines()
            }else{
                throw IOException("Error no se pudo leer el archivo o no existe.")
            }
        } catch (e: IOException) {
            consola.mostrarError("Error al leer el archivo: ${e.message}")
            emptyList()
        }
    }

    override fun agregarLinea(ruta: String, linea: String): Boolean {
        return try{
            File(ruta).appendText("$linea\n")
            true
        }catch (e: IOException){
            consola.mostrarError("Error al escribir en el archivo: ${e.message}")
            false
        }
    }

    override fun escribirArchivo(ruta: String, elementos: List<String>): Boolean {
        return try{
            File(ruta).writeText(elementos.joinToString(" "))
            true
        } catch (e: IOException){
            consola.mostrarError("Error al sobreescribir el archivo: ${e.message}")
            false
        }
    }

    override fun crearDirectorio(ruta: String) {
        val directorio = File(ruta)
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                consola.mostrar("Directorio $ruta creado correctamente")
            } else {
                consola.mostrarError("No se pudo crear el directorio $ruta")
            }
        } else {
            consola.mostrar("El directorio $ruta ya existe")
        }
    }

    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}