package eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

/**
 * ============================================================================
 * GESTOR DE NAVEGACIÓN: AppNavigation
 * ============================================================================
 *
 * 🎓 GUÍA DE VARIACIONES DE ARGUMENTOS EN RUTAS (PARA EL EXAMEN):
 *
 * En este ejercicio pasamos un String ("suma") en la ruta:
 *    navArgument(ARG_OPERACION) { type = NavType.StringType }
 *    val operacion = backStackEntry.arguments?.getString(ARG_OPERACION) ?: "resultado"
 *
 * ¿CÓMO SE PASARÍAN OTROS TIPOS DE DATOS ENTRE PANTALLAS?
 * ----------------------------------------------------------------------------
 * 1. NÚMERO ENTERO (Int):
 *    • En argumentos: navArgument("id") { type = NavType.IntType }
 *    • Extracción:    val id = backStackEntry.arguments?.getInt("id") ?: 0
 *    • Al navegar:    navController.navigate("detalle/42")
 *
 * 2. NÚMERO DECIMAL (Float):
 *    • En argumentos: navArgument("precio") { type = NavType.FloatType }
 *    • Extracción:    val precio = backStackEntry.arguments?.getFloat("precio") ?: 0.0f
 *
 * 3. BOOLEANO (Boolean):
 *    • En argumentos: navArgument("activo") { type = NavType.BoolType }
 *    • Extracción:    val activo = backStackEntry.arguments?.getBoolean("activo") ?: false
 *
 * 4. MÚLTIPLES ARGUMENTOS EN UNA MISMA RUTA:
 *    • Ruta: "resultado/{operacion}/{total}"
 *    • Arguments:
 *        arguments = listOf(
 *            navArgument("operacion") { type = NavType.StringType },
 *            navArgument("total") { type = NavType.IntType }
 *        )
 *    • Al navegar: navController.navigate("resultado/suma/150")
 * ============================================================================
 */

@Composable
fun AppNavigation(
    sumaViewModel: SumaViewModel = viewModel()
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "entrada"
    ) {

        // PANTALLA 1: ENTRADA
        composable("entrada") {
            EntradaScreen(
                numeros = sumaViewModel.numeros,
                onAgregarNumero = sumaViewModel::agregarNumero,
                onCalcular = {
                    sumaViewModel.calcular()
                    // Navega pasando el parámetro "suma" en la ruta
                    navController.navigate("resultado/suma")
                }
            )
        }

        // PANTALLA 2: RESULTADO
        composable(
            route = RUTA_RESULTADO, // "resultado/{operacion}"
            arguments = listOf(
                navArgument(ARG_OPERACION) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val operacion =
                backStackEntry.arguments
                    ?.getString(ARG_OPERACION)
                    ?: "resultado"

            ResultadoScreen(
                operacion = operacion,
                resultado = sumaViewModel.resultado,
                onReiniciar = {
                    sumaViewModel.reiniciar()
                    navController.popBackStack()
                }
            )
        }
    }
}
