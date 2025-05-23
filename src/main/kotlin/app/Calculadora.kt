package org.example.app

import org.example.model.Operacion
import org.example.model.Operadores
import org.example.service.IOperService
import org.example.ui.IEntradaSalida
import org.example.utils.ControlFichero
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Clase principal que implementa la lógica de la calculadora.
 * Permite al usuario realizar operaciones matemáticas, como suma, resta, multiplicación y división,
 * y registra las operaciones realizadas en una base de datos.
 *
 * @property consola La interfaz para interactuar con el usuario.
 * @property fich La interfaz para gestionar archivos.
 * @property rutaLogs La ruta de los archivos de registro.
 * @property dbService El servicio de base de datos para registrar las operaciones.
 */
class Calculadora(
    private val consola: IEntradaSalida,
    private val fich: ControlFichero,
    private val rutaLogs: String,
    private val dbService: IOperService
) {

    /**
     * Pide un número al usuario y valida la entrada.
     *
     * @param msj El mensaje que se muestra al solicitar el número.
     * @param msjError El mensaje de error si el número es inválido.
     * @return El número ingresado por el usuario.
     * @throws InfoCalcException Si el número no es válido.
     */
    private fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double {
        return consola.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    /**
     * Pide la información necesaria para realizar un cálculo (dos números y un operador).
     *
     * @return Un [Triple] con el primer número, el operador y el segundo número.
     * @throws InfoCalcException Si el operador o los números no son válidos.
     */
    private fun pedirInfo() = Triple(
        pedirNumero("Introduce el primer número: ", "El primer número no es válido!"),
        Operadores.getOperador(consola.pedirInfo("Introduce el operador (+, -, *, /): ").firstOrNull())
            ?: throw InfoCalcException("El operador no es válido!"),
        pedirNumero("Introduce el segundo número: ", "El segundo número no es válido!"))

    /**
     * Realiza un cálculo matemático basado en los dos números y el operador proporcionado.
     *
     * @param numero1 El primer número.
     * @param operador El operador a utilizar.
     * @param numero2 El segundo número.
     * @return El resultado del cálculo.
     * @throws InfoCalcException Si se intenta dividir por cero.
     */
    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double) =
        when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> {
                if (numero2 == 0.0) {
                    throw InfoCalcException("No se puede dividir entre cero.")
                } else {
                    numero1 / numero2
                }
            }
        }

    /**
     * Inicia la calculadora, mostrando al usuario el proceso de cálculo y registrando las operaciones.
     */
    fun iniciar() {

        val input = consola.pedirInfo()

        do {
            try {
                consola.limpiarPantalla()

                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)

                consola.mostrar("Resultado: %.2f".format(resultado))

                // Registrar la operación en la base de datos
                val operacion = "$numero1 ${operador.simbolos[0]} $numero2"
                val fechaHora = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val fechaFormateada = Timestamp.valueOf(fechaHora.format(formatter))

                dbService.registrarOperacion(Operacion(operacion, resultado, fechaFormateada))

            } catch (e: NumberFormatException) {
                consola.mostrarError("Se ha producido un error -> ${e.message}")
            } catch (e: InfoCalcException) {
                consola.mostrarError("Se ha producido un error -> ${e.message}")
            }
        } while (consola.preguntar())

        consola.limpiarPantalla()
    }
}