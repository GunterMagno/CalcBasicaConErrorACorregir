package org.example.utils

/**
 * Interfaz que define los métodos necesarios para gestionar operaciones de ficheros,
 * incluyendo el registro de logs, la lectura y escritura en archivos, la creación de directorios
 * y la verificación de la existencia de archivos o directorios.
 */
interface ControlFichero {

    /**
     * Registra un mensaje en un archivo de log.
     *
     * @param mensaje Mensaje a registrar.
     * @param rutaLogs Ruta del directorio donde se almacenará el archivo de log.
     */
    fun registrarLog(mensaje: String, rutaLogs: String)

    /**
     * Lista los archivos en un directorio específico.
     *
     * @param ruta Ruta del directorio donde se buscarán los archivos.
     * @return Ruta del último archivo listado, o una cadena vacía si no se encuentran archivos.
     */
    fun listarArchivos(ruta: String): String

    /**
     * Lee el contenido de un archivo de texto.
     *
     * @param ruta Ruta del archivo a leer.
     * @return Lista de cadenas que representan las líneas del archivo.
     */
    fun leerArchivo(ruta: String): List<String>

    /**
     * Añade una línea al final de un archivo de texto.
     *
     * @param ruta Ruta del archivo donde se añadirá la línea.
     * @param linea Línea que se añadirá al archivo.
     * @return `true` si la línea se ha añadido correctamente, `false` si ocurre un error.
     */
    fun agregarLinea(ruta: String, linea: String): Boolean

    /**
     * Sobrescribe el contenido de un archivo con una lista de cadenas.
     *
     * @param ruta Ruta del archivo a sobrescribir.
     * @param elementos Lista de cadenas que se escribirán en el archivo.
     * @return `true` si la operación fue exitosa, `false` en caso de error.
     */
    fun escribirArchivo(ruta: String, elementos: List<String>): Boolean

    /**
     * Crea un directorio en la ruta especificada.
     *
     * @param ruta Ruta donde se creará el directorio.
     */
    fun crearDirectorio(ruta: String)

    /**
     * Verifica si un archivo existe en la ruta especificada.
     *
     * @param ruta Ruta del archivo a verificar.
     * @return `true` si el archivo existe, `false` si no existe.
     */
    fun existeFichero(ruta: String): Boolean

    /**
     * Verifica si un directorio existe en la ruta especificada.
     *
     * @param ruta Ruta del directorio a verificar.
     * @return `true` si el directorio existe, `false` si no existe.
     */
    fun existeDirectorio(ruta: String): Boolean
}