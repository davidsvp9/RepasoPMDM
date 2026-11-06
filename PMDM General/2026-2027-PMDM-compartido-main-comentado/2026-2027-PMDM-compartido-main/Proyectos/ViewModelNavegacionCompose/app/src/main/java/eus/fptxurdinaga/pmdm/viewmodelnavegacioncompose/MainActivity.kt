package eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose.ui.theme.ViewModelNavegacionComposeTheme

/**
 * ============================================================================
 * PROYECTO 3: ViewModelNavegacionCompose
 * ============================================================================
 * OBJETIVO: Separar la lógica y el estado de la UI utilizando ViewModel,
 * y gestionar la navegación entre varias pantallas usando Jetpack Navigation Compose.
 *
 * CONCEPTOS CLAVE PARA EL EXAMEN:
 * 1. Arquitectura MVVM (Model-View-ViewModel):
 *    - La pantalla (View) solo dibuja y avisa al ViewModel de acciones del usuario.
 *    - El ViewModel mantiene los datos y sobrevive a giros de pantalla.
 * 2. Navegación en Compose (NavHost, NavController, composable):
 *    - NavHost: Contenedor que cambia qué pantalla se muestra según la ruta.
 *    - NavController: El "conductor" que permite ir a otra pantalla (.navigate())
 *      o volver atrás (.popBackStack()).
 *    - Rutas con argumentos: "resultado/{operacion}" permite pasar parámetros entre pantallas.
 *
 * ============================================================================
 */

// CONSTANTES DE NAVEGACIÓN:
// Nombre del argumento que se va a enviar por la ruta a la pantalla de resultados
const val ARG_OPERACION = "operacion"

// Ruta dinámica parametrizada: "resultado/{operacion}"
// Por ejemplo: si operacion="suma", la ruta resultante será "resultado/suma"
const val RUTA_RESULTADO = "resultado/{$ARG_OPERACION}"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite dibujo a pantalla completa (edge-to-edge)
        enableEdgeToEdge()

        setContent {
            ViewModelNavegacionComposeTheme {
                // Punto de inicio de la navegación de la aplicación
                AppNavigation()
            }
        }
    }
}
