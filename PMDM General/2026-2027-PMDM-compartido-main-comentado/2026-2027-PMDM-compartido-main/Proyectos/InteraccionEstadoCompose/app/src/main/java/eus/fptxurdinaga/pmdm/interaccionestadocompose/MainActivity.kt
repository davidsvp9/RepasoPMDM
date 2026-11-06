package eus.fptxurdinaga.pmdm.interaccionestadocompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import eus.fptxurdinaga.pmdm.interaccionestadocompose.ui.theme.InteraccionEstadoComposeTheme

/**
 * ============================================================================
 * PROYECTO 2: InteraccionEstadoCompose
 * ============================================================================
 * OBJETIVO: Comprender la interacción del usuario y la gestión del ESTADO (State)
 * localmente en la función Composable mediante remember y mutableStateOf.
 *
 * CONCEPTOS CLAVE PARA EL EXAMEN:
 * 1. ¿Qué es el Estado? Cualquier valor que puede cambiar a lo largo del tiempo
 *    y cuyo cambio debe refrescar la pantalla (proceso llamado RECOMPOSICIÓN).
 * 2. remember: Hace que la variable NO se reinicie cada vez que Compose redibuja la pantalla.
 * 3. mutableStateOf: Convierte una variable en "observable"; cuando su valor cambia,
 *    Compose sabe que debe redibujar los componentes que la usan.
 * 4. mutableStateListOf: Lista observable especial para colecciones.
 * 5. Delegación "by": Permite acceder al valor directamente sin escribir .value.
 *
 * ⚠️ NOTA DE EXAMEN SOBRE ROOM:
 * - En este proyecto NO se usa Room ni bases de datos SQLite.
 * - Los números se guardan en una lista en memoria RAM (se pierden al cerrar la app).
 * - Room NO ENTRA EN EL EXAMEN (todo lo relacionado con Room es irrelevante para la prueba).
 * ============================================================================
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Permite que la app dibuje de borde a borde en la pantalla del dispositivo
        enableEdgeToEdge()
        setContent {
            // Aplicamos el tema visual del proyecto
            InteraccionEstadoComposeTheme {
                // Lanzamos la pantalla principal
                PrimeraPantalla()
            }
        }
    }
}

/**
 * Pantalla interactiva que permite:
 * 1. Escribir un número.
 * 2. Añadirlo a una lista en memoria.
 * 3. Calcular la suma de los números ingresados.
 * 4. Vaciar la lista y reiniciar los campos.
 */
@Composable
fun PrimeraPantalla() {

    // -------------------------------------------------------------
    // VARIABLES DE ESTADO (MEMORIA LOCAL DE LA PANTALLA)
    // -------------------------------------------------------------

    // Guarda el texto que el usuario está escribiendo en el campo de texto (TextField).
    // Usamos 'by' para leer y escribir directamente en 'texto' sin hacer '.value'.
    var texto by remember {
        mutableStateOf("")
    }

    // Guarda mensajes de error para mostrarlos al usuario si la validación falla.
    var mensajeError by remember {
        mutableStateOf("")
    }

    // Lista observable de enteros. Al usar mutableStateListOf(), cuando añadimos o quitamos
    // elementos (add/clear), Jetpack Compose redibuja automáticamente la lista en pantalla.
    val numeros = remember {
        mutableStateListOf<Int>()
    }

    // Guarda el resultado final de la suma. Empieza en 'null' porque al inicio no hay suma calculada.
    var resultado by remember {
        mutableStateOf<Int?>(null)
    }

    // -------------------------------------------------------------
    // INTERFAZ GRÁFICA (LAYOUT)
    // -------------------------------------------------------------

    // Columna vertical con separación interior (padding) de 48dp
    Column(
        modifier = Modifier.padding(48.dp)
    ) {

        // CAMPO DE TEXTO: Donde el usuario escribe el número
        OutlinedTextField(
            value = texto, // Muestra el valor actual de la variable 'texto'
            onValueChange = { nuevoTexto ->
                // Cada vez que el usuario teclea o borra una letra, se actualiza el estado 'texto'
                texto = nuevoTexto
            },
            label = {
                // Etiqueta flotante encima o dentro del campo de texto
                Text("Número")
            },
            singleLine = true // Evita saltos de línea al presionar Enter
        )

        // BOTÓN 1: "Añadir"
        // FLUJO AL HACER CLIC:
        // 1. Intenta convertir el texto a entero con .toIntOrNull().
        // 2. Si está en blanco -> mensaje de error.
        // 3. Si no es un número válido (ej. escribió "hola") -> mensaje de error.
        // 4. Si es correcto -> añade el número a la lista, limpia el campo de texto y borra el error.
        Button(
            onClick = {
                val numero = texto.toIntOrNull()

                when {
                    texto.isBlank() ->
                        mensajeError = "Introduce un número"

                    numero == null ->
                        mensajeError = "Debe ser un entero"

                    else -> {
                        // Éxito: agregamos a la lista observable
                        numeros.add(numero)
                        // Limpiamos el campo de texto para el siguiente número
                        texto = ""
                        // Limpiamos cualquier error previo
                        mensajeError = ""
                    }
                }
            }
        ) {
            Text("Añadir")
        }

        // MOSTRAR ERROR CONDICIONALMENTE:
        // Si hay algún mensaje de error guardado, se dibuja un Text con el aviso
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError)
        }

        // MOSTRAR LISTA DE NÚMEROS:
        // Recorre todos los números guardados en la lista observable y genera un Text por cada uno
        for (numero in numeros) {
            Text("• $numero")
        }

        // BOTÓN 2: "Calcular suma"
        // FLUJO AL HACER CLIC:
        // 1. Comprueba si la lista está vacía. Si lo está, muestra error.
        // 2. Si tiene números, llama a la función calcularSuma() y guarda el resultado en el estado 'resultado'.
        Button(
            onClick = {
                if (numeros.isEmpty()) {
                    mensajeError = "No hay números para calcular"
                } else {
                    resultado = calcularSuma(numeros)
                    mensajeError = ""
                }
            }
        ) {
            Text("Calcular suma")
        }

        // MOSTRAR RESULTADO CONDICIONALMENTE:
        // Si 'resultado' ya no es null (es decir, ya se calculó), mostramos el valor en pantalla
        if (resultado != null) {
            Text("Resultado: $resultado")
        }

        // BOTÓN 3: "Vaciar"
        // FLUJO AL HACER CLIC:
        // Restablece todas las variables de estado a su valor inicial
        Button(
            onClick = {
                numeros.clear()
                texto = ""
                resultado = null
                mensajeError = ""
            }
        ) {
            Text("Vaciar")
        }
    }
}

/**
 * FUNCIÓN AUXILIAR DE LÓGICA DE NEGOCIO:
 * Recibe una lista de números enteros [numeros] y devuelve la suma total de sus elementos.
 *
 * Flujo:
 * 1. Inicializa un acumulador 'suma' a 0.
 * 2. Itera por cada número sumándolo al acumulador.
 * 3. Retorna el total obtenido.
 */
fun calcularSuma(numeros: List<Int>): Int {
    var suma = 0

    for (numero in numeros) {
        suma += numero
    }

    return suma
}
