package org.example

import org.example.app.Calculadora
import org.example.data.dao.OperacionesDAOH2
import org.example.model.Operadores
import org.example.service.OperService
import org.example.ui.Consola
import org.example.utils.GestionFicheros

/**
 * Función principal que gestiona la ejecución de la aplicación.
 * Dependiendo de los parámetros de entrada, realiza diferentes operaciones, como mostrar el historial
 * de operaciones, crear directorios de logs, o realizar un cálculo utilizando una calculadora.
 *
 * @param args Parámetros de entrada proporcionados por la línea de comandos.
 * @throws NumberFormatException Si los parámetros numéricos no son válidos o tienen un formato incorrecto.
 */

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
            for (operacion in operDbService.obtenerHistorial()){
                consola.mostrar("$operacion\n")
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