package eus.fptxurdinaga.pmdm.primeraappcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eus.fptxurdinaga.pmdm.primeraappcompose.ui.theme.PrimeraAppComposeTheme

/**
 * ============================================================================
 * PROYECTO 1: PrimeraAppCompose
 * ============================================================================
 * OBJETIVO: Introducción a Jetpack Compose y estructura básica de una App Android.
 *
 * NOTA DE EXAMEN SOBRE ROOM:
 * - En este proyecto NO se usa Room (persistencia en base de datos SQLite).
 * - Room NO entra en el examen (contenido irrelevante para la prueba).
 * - Aquí la interfaz se construye directamente en código declarativo con Compose.
 * ============================================================================
 */

/**
 * MainActivity es el punto de entrada principal de la aplicación Android.
 * En Jetpack Compose heredamos de ComponentActivity.
 */
class MainActivity : ComponentActivity() {

    /**
     * onCreate se ejecuta cuando el sistema operativo crea por primera vez la pantalla/actividad.
     * Es el primer método del ciclo de vida que se llama.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent sustituye al antiguo setContentView(R.layout.xxx) de XML.
        // Aquí definimos qué interfaz declarativa de Compose se va a dibujar.
        setContent {
            // Llamamos a la función componible que dibuja la pantalla inicial
            PrimeraPantalla()
        }
    }
}

/**
 * @Composable indica que esta función describe una parte de la interfaz gráfica.
 *
 * FLUJO DE EJECUCIÓN:
 * 1. El sistema invoca PrimeraPantalla() dentro de setContent.
 * 2. Se crea un contenedor vertical (Column).
 * 3. Se aplica un margen interno (padding de 24 dp) a la columna.
 * 4. Se dibuja dentro el texto "Primera app Android".
 */
@Composable
fun PrimeraPantalla() {
    // Column coloca sus elementos hijos uno debajo de otro (en vertical).
    // modifier nos permite configurar aspecto, tamaño, márgenes, etc.
    Column(
        modifier = Modifier.padding(24.dp) // Añade 24 unidades de espacio interior (padding)
    ) {
        // Text es el componente básico para mostrar texto en pantalla
        Text("Primera app Android")
    }
}
