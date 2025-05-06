package org.example

import org.example.app.Calculadora
import org.example.data.dao.OperacionesDAOH2
import org.example.model.Operadores
import org.example.service.OperService
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

//ToDo probar con argumentos, en el archivo bat no me deja meterlos
fun main(args: Array<String>) {

    val consola = Consola()
    val fich = GestionFicheros(consola)
    var rutaLogs = "./log"
    val operDAOH2 = OperacionesDAOH2()
    val operDbService = OperService(operDAOH2)
    var continuar = true

    when(args.size){
        0 -> {
            /* //ToDo Esto era para logs
            if(!fich.existeDirectorio(rutaLogs)){
                consola.mostrar("No existe el directorio $rutaLogs, creandolo...")
                fich.crearDirectorio(rutaLogs)
            }

            val rutaUltimo = fich.listarArchivos(rutaLogs)

            for (linea in fich.leerArchivo(rutaUltimo)){
                consola.mostrar(linea)
            }
            */

            //operDbService.inicializar()
            consola.mostrar("\nHistorial de Operaciones Realizadas")
            consola.mostrar("-------------------------------------")
            for (linea in operDbService.obtenerHistorial()){
                consola.mostrar(linea)
            }
            consola.mostrar("-------------------------------------")
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
            rutaLogs = args[0]

            try{
                val num1 = args[1].toDouble()
                val operador = Operadores.getOperador(args[2].toCharArray()[0])
                val num2 = args[3].toDouble()

                if (operador == null) {
                    consola.mostrarError("Operador introducido no valido.")
                } else {
                    Calculadora(consola, fich,rutaLogs,operDbService).realizarCalculo(num1, operador, num2)
                }
            }catch (e: NumberFormatException){
                consola.mostrarError("Error en formato de números: ${e.message}")
            }
        }
        else -> {
            consola.mostrarError("Error al introducir los argumentos iniciales.")
            continuar = false
        }
    }

    if(continuar){
        consola.pausar("\nPulse Enter para inicar la calculadora...")
        Calculadora(consola, fich,rutaLogs,operDbService).iniciar()
    }
}