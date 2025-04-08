package org.example.utils

import org.example.ui.IEntradaSalida
import java.io.File
import java.io.IOException
import java.nio.file.Files

class GestionFicheros(private val consola: IEntradaSalida): ControlFichero {

    override fun listarArchivos(ruta: String) {
        if (existeDirectorio(ruta)){
            for (archivo in leerArchivo(ruta)){
                consola.mostrar(archivo)
            }
        }
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
        Files.createDirectory(ruta) // Como se creaba un directorio xdxxddxxddxxdxddxdx
    }

    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}