package org.example

import org.example.app.Calculadora
import org.example.model.Operadores
import org.example.ui.Consola
import org.example.utils.GestionFicheros

/*
fun main() {
    val scanner = Scanner(System.`in`)

    println("Introduce el primer número:")
    val numero1 = scanner.nextDouble()
    println("Introduce el operador (+, -, *, /):")
    val operador = scanner.next()[0]
    println("Introduce el segundo número:")
    val numero2 = scanner.nextDouble()

    val resultado = when (operador) {
        '+' -> numero1 + numero2
        '-' -> numero1 - numero2
        '*' -> numero1 * numero2
        '/' -> numero1 / numero2
        else -> "Operador no válido"
    }

    println("Resultado: $resultado")
}
*/

fun main(args: Array<String>) {

    var rutaLogs = "./log"
    val consola = Consola()
    val fich = GestionFicheros(consola)

    when(args.size){
        0 -> {
            if(!fich.existeDirectorio(rutaLogs)){
                consola.mostrar("No existe el directorio $rutaLogs, creandolo...")
                fich.crearDirectorio(rutaLogs)
            }
            fich.listarArchivos(rutaLogs)
        }
        1 -> {
            rutaLogs = args[0]
            if(!fich.existeDirectorio(rutaLogs)){
                consola.mostrar("No existe el directorio $rutaLogs, creandolo...")
                fich.crearDirectorio(rutaLogs)
            }
            fich.listarArchivos(rutaLogs)
        }
        4 -> {
            val ruta = args[0]
            val num1 = args[1].toDouble()
            val operador = Operadores.getOperador(args[2].toCharArray()[0])
            val num2 = args[3].toDouble()

            if (operador == null){
                consola.mostrarError("Operador introducido no valido.")
            }else {
                Calculadora(consola, fich).realizarCalculo(num1, operador, num2)
            }
        }
        else -> {
            consola.mostrarError("Error al introducir los argumentos iniciales.")
            //Hacer que no inicie
        }
    }


    consola.pausar("Pulse Enter para inicar la calculadora...")
    Calculadora(consola,fich).iniciar()
}


/*
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    val numLineas = scanner.nextInt()
    scanner.nextLine() // Limpia el salto de línea pendiente

    var resultado = 1

    for (i in 1..numLineas) {
        var suma = 0
        while (scanner.hasNextInt()) {
            suma += scanner.nextInt()
        }
        resultado *= suma
        if (scanner.hasNextLine()) scanner.nextLine() // pasar a la siguiente línea
    }

    println(resultado)
}
*/