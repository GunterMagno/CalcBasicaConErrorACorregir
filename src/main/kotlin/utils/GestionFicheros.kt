package org.example.utils

import org.example.ui.IEntradaSalida
import java.io.File
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase encargada de la gestión de ficheros relacionados con los logs, incluyendo la creación de directorios,
 * escritura, lectura y listado de archivos, así como el registro de eventos en un archivo de log.
 *
 * @param consola Instancia que implementa la interfaz [IEntradaSalida] para la interacción con el usuario.
 */
class GestionFicheros(private val consola: IEntradaSalida): ControlFichero {

    /**
     * Registra un mensaje en un archivo de log, incluyendo la fecha y hora actual.
     *
     * @param mensaje Mensaje a registrar en el archivo de log.
     * @param rutaLogs Ruta del directorio donde se guardará el archivo de log.
     * @throws IOException Si no se puede escribir en el archivo de log.
     */
    override fun registrarLog(mensaje: String, rutaLogs: String) {

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

    /**
     * Lista los archivos en el directorio especificado.
     *
     * @param ruta Ruta del directorio donde se buscarán los archivos.
     * @return Ruta del último archivo listado, o una cadena vacía si no hay archivos.
     */
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

    /**
     * Lee el contenido de un archivo de texto.
     *
     * @param ruta Ruta del archivo a leer.
     * @return Lista de cadenas, cada una representando una línea del archivo.
     * @throws IOException Si el archivo no existe o no se puede leer.
     */
    override fun leerArchivo(ruta: String): List<String> {
        val archivo = File(ruta)
        return try {
            if (archivo.exists()){
                archivo.readLines()
            } else {
                throw IOException("Error no se pudo leer el archivo o no existe.")
            }
        } catch (e: IOException) {
            consola.mostrarError("Error al leer el archivo: ${e.message}")
            emptyList()
        }
    }

    /**
     * Añade una línea al final de un archivo de texto.
     *
     * @param ruta Ruta del archivo donde se añadirá la línea.
     * @param linea Línea a agregar al archivo.
     * @return `true` si la línea se ha añadido correctamente, `false` si ocurre un error.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    override fun agregarLinea(ruta: String, linea: String): Boolean {
        return try {
            File(ruta).appendText("$linea\n")
            true
        } catch (e: IOException) {
            consola.mostrarError("Error al escribir en el archivo: ${e.message}")
            false
        }
    }

    /**
     * Sobrescribe el contenido de un archivo con una lista de elementos.
     *
     * @param ruta Ruta del archivo a sobrescribir.
     * @param elementos Lista de cadenas que se escribirán en el archivo.
     * @return `true` si se escribe correctamente, `false` en caso de error.
     * @throws IOException Si ocurre un error al escribir en el archivo.
     */
    override fun escribirArchivo(ruta: String, elementos: List<String>): Boolean {
        return try {
            File(ruta).writeText(elementos.joinToString(" "))
            true
        } catch (e: IOException) {
            consola.mostrarError("Error al sobreescribir el archivo: ${e.message}")
            false
        }
    }

    /**
     * Crea un directorio en la ruta especificada si no existe.
     *
     * @param ruta Ruta donde se creará el directorio.
     * @throws IOException Si no se puede crear el directorio.
     */
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

    /**
     * Verifica si un archivo existe en la ruta especificada.
     *
     * @param ruta Ruta del archivo a verificar.
     * @return `true` si el archivo existe, `false` en caso contrario.
     */
    override fun existeFichero(ruta: String): Boolean {
        return File(ruta).exists()
    }

    /**
     * Verifica si un directorio existe en la ruta especificada.
     *
     * @param ruta Ruta del directorio a verificar.
     * @return `true` si el directorio existe, `false` en caso contrario.
     */
    override fun existeDirectorio(ruta: String): Boolean {
        return File(ruta).isDirectory
    }
}