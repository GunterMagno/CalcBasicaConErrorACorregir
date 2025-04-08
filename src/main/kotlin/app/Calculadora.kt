package org.example.app

import org.example.model.Operadores
import org.example.ui.IEntradaSalida
import org.example.utils.ControlFichero

class Calculadora(private val consola: IEntradaSalida, private val fich: ControlFichero) {

    private fun pedirNumero(msj: String, msjError: String = "Número no válido!"): Double {
        return consola.pedirDouble(msj) ?: throw InfoCalcException(msjError)
    }

    private fun pedirInfo() = Triple(
        pedirNumero("Introduce el primer número: ", "El primer número no es válido!"),
        Operadores.getOperador(consola.pedirInfo("Introduce el operador (+, -, *, /): ").firstOrNull())
            ?: throw InfoCalcException("El operador no es válido!"),
        pedirNumero("Introduce el segundo número: ", "El segundo número no es válido!"))

    fun realizarCalculo(numero1: Double, operador: Operadores, numero2: Double) =
        when (operador) {
            Operadores.SUMA -> numero1 + numero2
            Operadores.RESTA -> numero1 - numero2
            Operadores.MULTIPLICACION -> numero1 * numero2
            Operadores.DIVISION -> numero1 / numero2
        }

    fun iniciar() {

        val input = consola.pedirInfo()


        do {
            try {
                consola.limpiarPantalla()
                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)
                consola.mostrar("Resultado: %.2f".format(resultado))
            } catch (e: NumberFormatException) {
                consola.mostrarError(e.message ?: "Se ha producido un error!")
            } catch (e: InfoCalcException){
                consola.mostrarError(e.message ?: "Se ha producido un error!")
            }
        } while (consola.preguntar())
        consola.limpiarPantalla()
    }
}