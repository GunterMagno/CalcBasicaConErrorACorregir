package org.example.ui

/**
 * Interfaz que define los métodos para interactuar con el usuario, proporcionando funcionalidad
 * para mostrar mensajes, solicitar información y realizar operaciones de control de flujo.
 */
interface IEntradaSalida {

    /**
     * Muestra un mensaje en la consola.
     *
     * @param msj Mensaje a mostrar.
     * @param salto Indica si debe añadirse un salto de línea al final del mensaje. Por defecto es `true`.
     */
    fun mostrar(msj: String, salto: Boolean = true)

    /**
     * Muestra un mensaje de error en la consola.
     *
     * @param msj Mensaje de error a mostrar.
     * @param salto Indica si debe añadirse un salto de línea al final del mensaje. Por defecto es `true`.
     */
    fun mostrarError(msj: String, salto: Boolean = true)

    /**
     * Solicita al usuario una entrada de texto.
     *
     * @param msj Mensaje que se muestra antes de solicitar la entrada. Por defecto es una cadena vacía.
     * @return La entrada proporcionada por el usuario.
     */
    fun pedirInfo(msj: String = ""): String

    /**
     * Solicita al usuario un número decimal.
     *
     * @param msj Mensaje que se muestra antes de solicitar el número. Por defecto es una cadena vacía.
     * @return El número decimal proporcionado por el usuario, o `null` si la entrada no es válida.
     */
    fun pedirDouble(msj: String = ""): Double?

    /**
     * Solicita al usuario un número entero.
     *
     * @param msj Mensaje que se muestra antes de solicitar el número. Por defecto es una cadena vacía.
     * @return El número entero proporcionado por el usuario, o `null` si la entrada no es válida.
     */
    fun pedirEntero(msj: String = ""): Int?

    /**
     * Pregunta al usuario si desea intentarlo de nuevo.
     *
     * @param msj Mensaje que se muestra al usuario. Por defecto es "¿Deseas intentarlo de nuevo? (s/n): ".
     * @return `true` si el usuario responde afirmativamente, `false` en caso contrario.
     */
    fun preguntar(msj: String = "¿Deseas intentarlo de nuevo? (s/n): "): Boolean

    /**
     * Limpia la pantalla de la consola.
     *
     * @param numSaltos Número de saltos de línea a realizar. Por defecto es `20`.
     */
    fun limpiarPantalla(numSaltos: Int = 20)

    /**
     * Pausa la ejecución de la aplicación y espera que el usuario presione Enter para continuar.
     *
     * @param msg Mensaje que se muestra al usuario mientras espera. Por defecto es "Pulse enter para continuar...".
     */
    fun pausar(msg: String = "Pulse enter para continuar...")
}