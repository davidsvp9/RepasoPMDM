package com.example.calculadorafinal

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calculadorafinal.ui.VentanaPrincipal
import com.example.calculadorafinal.ui.VentanaResultado

const val ARG_OPERACION = "operacion"
const val RUTA_RESULTADO = "resultado/{$ARG_OPERACION}"

@Composable
fun AppNavigation(
    calculadoraViewModel: CalculadoraViewModel = viewModel()
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "principal"
    ) {
        composable("principal") {
            VentanaPrincipal(
                expresion = calculadoraViewModel.expresion,
                onAgregarCaracter = calculadoraViewModel::agregarCaracter,
                onBorrar = calculadoraViewModel::borrar,
                onLimpiar = calculadoraViewModel::limpiar,
                onCalcular = {
                    calculadoraViewModel.calcular()
                    navController.navigate("resultado/calculo")
                }
            )
        }
        composable(
            route = RUTA_RESULTADO,
            arguments = listOf(
                navArgument(ARG_OPERACION) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val operacion = backStackEntry.arguments?.getString(ARG_OPERACION) ?: "operacion"
            VentanaResultado(
                operacion = operacion,
                resultado = calculadoraViewModel.resultado,
                onReiniciar = {
                    calculadoraViewModel.reiniciar()
                    navController.popBackStack()
                }
            )
        }
    }
}
