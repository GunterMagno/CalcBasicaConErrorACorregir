package org.example.ui

import java.util.Scanner

/**
 * Implementación de la interfaz [IEntradaSalida] que gestiona la interacción con el usuario a través
 * de la consola. Permite mostrar mensajes, pedir entradas, limpiar la pantalla, y pausar la ejecución.
 */
class Consola : IEntradaSalida {
    private val scanner = Scanner(System.`in`)

    /**
     * Muestra un mensaje en la consola.
     *
     * @param msj Mensaje a mostrar.
     * @param salto Indica si se debe añadir un salto de línea al final del mensaje. Por defecto es `true`.
     */
    override fun mostrar(msj: String, salto: Boolean) {
        print("$msj${if (salto) "\n" else ""}")
    }

    /**
     * Muestra un mensaje de error en la consola con el prefijo "ERROR -".
     *
     * @param msj Mensaje de error a mostrar.
     * @param salto Indica si se debe añadir un salto de línea al final del mensaje. Por defecto es `true`.
     */
    override fun mostrarError(msj: String, salto: Boolean) {
        mostrar("ERROR - $msj", salto)
    }

    /**
     * Solicita al usuario una entrada de texto.
     *
     * @param msj Mensaje que se muestra antes de solicitar la entrada. Si es vacío, no se muestra ningún mensaje.
     * @return La entrada de texto proporcionada por el usuario.
     */
    override fun pedirInfo(msj: String): String {
        if (msj.isNotEmpty()) mostrar(msj, false)
        return scanner.nextLine().trim()
    }

    /**
     * Solicita al usuario un número decimal (Double).
     *
     * @param msj Mensaje que se muestra antes de solicitar el número. Si es vacío, no se muestra ningún mensaje.
     * @return El número decimal proporcionado por el usuario, o `null` si la entrada no es válida.
     */
    override fun pedirDouble(msj: String): Double? {
        return pedirInfo(msj).replace(',', '.').toDoubleOrNull()
    }

    /**
     * Solicita al usuario un número entero (Int).
     *
     * @param msj Mensaje que se muestra antes de solicitar el número. Si es vacío, no se muestra ningún mensaje.
     * @return El número entero proporcionado por el usuario, o `null` si la entrada no es válida.
     */
    override fun pedirEntero(msj: String): Int? {
        return pedirInfo(msj).toIntOrNull()
    }

    /**
     * Pregunta al usuario si desea continuar, esperando una respuesta afirmativa o negativa.
     *
     * @param msj Mensaje que se muestra al usuario. Por defecto es "¿Deseas intentarlo de nuevo? (s/n): ".
     * @return `true` si el usuario responde afirmativamente (con "s" o "si"), `false` en caso contrario.
     */
    override fun preguntar(msj: String): Boolean {
        do {
            val respuesta = pedirInfo(msj).lowercase()
            when (respuesta) {
                "s", "si" -> return true
                "n", "no" -> return false
                else -> mostrarError("Respuesta no válida. Responde con s, n, si o no.")
            }
        } while (true)
    }

    /**
     * Limpia la pantalla de la consola. Si la consola no soporta la limpieza, se agregan saltos de línea
     * para simular el efecto.
     *
     * @param numSaltos Número de saltos de línea a realizar. Por defecto es `20`.
     */
    override fun limpiarPantalla(numSaltos: Int) {
        if (System.console() != null) {
            mostrar("\u001b[H\u001b[2J", false)
            System.out.flush()
        } else {
            repeat(numSaltos) {
                mostrar("")
            }
        }
    }

    /**
     * Pausa la ejecución de la aplicación y espera que el usuario presione Enter para continuar.
     *
     * @param msg Mensaje que se muestra al usuario mientras espera. Por defecto es "Pulse enter para continuar...".
     */
    override fun pausar(msg: String) {
        mostrar(msg)
        scanner.nextLine()
    }
}