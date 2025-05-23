package org.example.app

/**
 * Excepción personalizada para la aplicación calculadora.
 * Esta excepción se utiliza para manejar errores específicos de la lógica de la calculadora.
 *
 * @param message El mensaje que describe el error ocurrido.
 */
class InfoCalcException(message: String) : Exception(message)