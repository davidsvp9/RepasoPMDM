package com.example.calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ui.VentanaPrincipal
import ui.VentanaResultado
import com.example.calculadora.ui.theme.CalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = "principal",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("principal") {
                            VentanaPrincipal(
                                onNavigateToResultado = { expresionEscrita: String ->
                                    // Aquí llamamos a la función que calcula el resultado
                                    val resultadoCalculado = calcularExpresion(expresionEscrita)
                                    // Navegamos pasando el resultado ya calculado (ej: "3")
                                    navController.navigate("resultado/$resultadoCalculado")
                                }
                            )
                        }

                        composable("resultado/{resultado}") { backStackEntry ->
                            val resultadoFinal = backStackEntry.arguments?.getString("resultado") ?: ""
                            VentanaResultado(
                                navController = navController,
                                resultado = resultadoFinal
                            )
                        }
                    }
                }
            }
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

}