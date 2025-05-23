package org.example.model

/**
 * Enum que representa los operadores matemáticos disponibles en la aplicación.
 * Cada operador tiene una lista de símbolos que lo representan.
 */
enum class Operadores(val simbolos: List<Char>) {
    SUMA(listOf('+')),
    RESTA(listOf('-')),
    MULTIPLICACION(listOf('*', 'x')),
    DIVISION(listOf(':', '/'));

    companion object {
        /**
         * Obtiene el operador correspondiente a un símbolo.
         *
         * @param operador El símbolo del operador.
         * @return El operador correspondiente, o `null` si no se encuentra un operador con ese símbolo.
         */
        fun getOperador(operador: Char?) = operador?.let { op -> entries.find { op in it.simbolos } }
    }
}