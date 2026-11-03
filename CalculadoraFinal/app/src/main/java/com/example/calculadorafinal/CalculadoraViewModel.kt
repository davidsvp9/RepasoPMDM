package com.example.calculadorafinal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculadoraViewModel : ViewModel() {

    var expresion by mutableStateOf("")
        private set

    var resultado by mutableStateOf("")
        private set

    fun agregarCaracter(caracter: String) {
        expresion += caracter
    }

    fun borrar() {
        if (expresion.isNotEmpty()) {
            expresion = expresion.dropLast(1)
        }
    }

    fun limpiar() {
        expresion = ""
    }

    fun calcular() {
        resultado = calcularExpresion(expresion)
    }

    fun reiniciar() {
        expresion = ""
        resultado = ""
    }
}

private fun calcularExpresion(expresion: String): String {
    try {
        if (expresion.isEmpty()) return "0"

        // 1. Separamos la expresión en tokens (números y operadores)
        val tokens = mutableListOf<String>()
        var numeroActual = ""
        for (char in expresion) {
            if (char.isDigit() || char == '.') {
                numeroActual += char
            } else {
                if (numeroActual.isNotEmpty()) {
                    tokens.add(numeroActual)
                    numeroActual = ""
                }
                tokens.add(char.toString())
            }
        }
        if (numeroActual.isNotEmpty()) {
            tokens.add(numeroActual)
        }

        // 2. Primera pasada: Resolver multiplicaciones (*) y divisiones (/)
        val tokensPaso1 = mutableListOf<String>()
        var i = 0
        while (i < tokens.size) {
            if (tokens[i] == "*" || tokens[i] == "/") {
                val anterior = tokensPaso1.removeAt(tokensPaso1.size - 1).toDouble()
                val siguiente = tokens[i + 1].toDouble()
                val resultadoParcial = if (tokens[i] == "*") {
                    anterior * siguiente
                } else {
                    if (siguiente == 0.0) return "Error (Div/0)"
                    anterior / siguiente
                }
                tokensPaso1.add(resultadoParcial.toString())
                i += 2
            } else {
                tokensPaso1.add(tokens[i])
                i++
            }
        }

        // 3. Segunda pasada: Resolver sumas (+) y restas (-)
        var total = tokensPaso1[0].toDouble()
        i = 1
        while (i < tokensPaso1.size) {
            val operador = tokensPaso1[i]
            val siguiente = tokensPaso1[i + 1].toDouble()
            if (operador == "+") {
                total += siguiente
            } else if (operador == "-") {
                total -= siguiente
            }
            i += 2
        }

        // 4. Formatear el resultado (quitar decimales .0 si es entero)
        return if (total % 1.0 == 0.0) {
            total.toInt().toString()
        } else {
            total.toString()
        }
    } catch (e: Exception) {
        return "Error"
    }
}
