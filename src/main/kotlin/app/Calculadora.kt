package org.example.app

import org.example.model.Operadores
import org.example.ui.IEntradaSalida
import org.example.utils.ControlBaseDatos
import org.example.utils.ControlFichero

class Calculadora(private val consola: IEntradaSalida, private val fich: ControlFichero,private val rutaLogs: String, private val baseDatos: ControlBaseDatos) {

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
            Operadores.DIVISION -> {
                if(numero2 == 0.0){
                     throw InfoCalcException("No se puede dividir entre cero.")
                }else{
                    numero1 / numero2
                }
            }
        }

    fun iniciar() {

        val input = consola.pedirInfo()

        do {
            try {
                consola.limpiarPantalla()

                val (numero1, operador, numero2) = pedirInfo()
                val resultado = realizarCalculo(numero1, operador, numero2)

                consola.mostrar("Resultado: %.2f".format(resultado))
                //fich.registrarLog("$numero1 ${operador.simbolos[0]} $numero2 = $resultado",rutaLogs)
                baseDatos.registrarOperacion("$numero1 ${operador.simbolos[0]} $numero2",resultado)

            } catch (e: NumberFormatException) {
                consola.mostrarError("Se ha producido un error -> ${e.message}")
            } catch (e: InfoCalcException){
                consola.mostrarError("Se ha producido un error -> ${e.message}")
            }
        } while (consola.preguntar())
        consola.limpiarPantalla()
    }
}