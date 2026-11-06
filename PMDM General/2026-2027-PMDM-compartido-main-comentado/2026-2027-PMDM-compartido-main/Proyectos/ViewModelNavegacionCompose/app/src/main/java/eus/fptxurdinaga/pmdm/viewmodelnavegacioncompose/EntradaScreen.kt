package eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * ============================================================================
 * PANTALLA: EntradaScreen
 * ============================================================================
 * OBJETIVO: Permitir al usuario ingresar números y solicitar el cálculo.
 *
 * CONCEPTO CLAVE PARA EL EXAMEN: ELEVACIÓN DE ESTADO (State Hoisting)
 * - Esta pantalla NO almacena la lista definitiva de números.
 * - Recibe los datos mediante parámetros: [numeros].
 * - Comunica los eventos hacia arriba usando lambdas/callbacks:
 *   [onAgregarNumero] y [onCalcular].
 * - Solo mantiene como estado local lo estrictamente visual/efímero:
 *   [texto] (lo que se está escribiendo) y [mensajeError] (validación del input).
 *
 * 🎓 VARIACIONES DE PARÁMETROS EN SCREENS (CÓMO USAR OTROS TIPOS):
 * ----------------------------------------------------------------------------
 * 1. Si fuera con DECIMALES (Double):
 *      numeros: List<Double>, onAgregarNumero: (Double) -> Unit
 * 2. Si fuera con TEXTOS (Strings):
 *      nombres: List<String>, onAgregarNombre: (String) -> Unit
 * 3. Si fuera con OBJETOS (Data Class):
 *      productos: List<Producto>, onAgregarProducto: (Producto) -> Unit
 * 4. Enviando múltiples valores en un callback:
 *      onGuardar: (String, Double) -> Unit // onGuardar("Pan", 1.20)
 * ============================================================================
 */
@Composable
fun EntradaScreen(
    numeros: List<Int>,              // Lista de números que vienen del ViewModel
    onAgregarNumero: (Int) -> Unit,  // Callback al añadir un número válido
    onCalcular: () -> Unit           // Callback al solicitar el cálculo y navegación
    //Todos estos parametros se definen en AppNavegation
) {
    // Estado local para el texto que se está escribiendo en el campo de texto
    var texto by remember {
        mutableStateOf("")
    }

    // Estado local para mostrar mensajes de validación (campo vacío, texto no numérico...)
    var mensajeError by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.padding(48.dp)
    ) {
        // CAMPO DE TEXTO PARA INTRODUCIR NÚMERO
        OutlinedTextField(
            value = texto,
            onValueChange = { nuevoTexto ->
                texto = nuevoTexto
            },
            label = {
                Text("Número")
            },
            singleLine = true
        )

        // ====================================================================
        // 📚 DICCIONARIO DE TRANSFORMACIONES DE VARIABLES (CHUETA PARA EL EXAMEN)
        // ====================================================================
        //
        // 1. DE STRING A NÚMERO:
        //    • String a Int (seguro):     val num = texto.toIntOrNull()      // Devuelve Int? (o null si falla)
        //    • String a Int (inseguro):   val num = texto.toInt()            // Falla si no es número
        //    • String a Double:           val dec = texto.toDoubleOrNull()   // "3.14" -> 3.14 (Double?)
        //    • String a Float:            val flo = texto.toFloatOrNull()    // "2.5" -> 2.5f (Float?)
        //    • Reemplazar coma por punto: val dec = texto.replace(',', '.').toDoubleOrNull()
        //
        // 2. ENTRE NÚMEROS:
        //    • Int a Double:              val d = numero.toDouble()          // 5 -> 5.0
        //    • Double a Int (corta):      val i = decimal.toInt()            // 5.8 -> 5
        //    • Double a Int (redondea):   val r = decimal.roundToInt()       // 5.8 -> 6 (de kotlin.math)
        //
        // 3. A STRING:
        //    • Con .toString():           val s = numero.toString()          // 10 -> "10"
        //    • Con interpolación:         val s = "$numero"                  // 10 -> "10"
        // ====================================================================

        // BOTÓN "Añadir":
        Button(
            onClick = {
                // Transformación actual: de String a Int?
                val numero = texto.toIntOrNull()

                // SI EL EJERCICIO FUERA DE DOUBLE:
                // val numero = texto.replace(',', '.').toDoubleOrNull()

                when {
                    texto.isBlank() ->
                        mensajeError = "Introduce un número"
                    numero == null ->
                        mensajeError = "Debe ser un entero" // O "Debe ser un número válido" si fuera Double
                    else -> {
                        // Notificamos al ViewModel a través del callback
                        onAgregarNumero(numero)
                        texto = ""
                        mensajeError = ""
                    }
                }
            }
        ) {
            Text("Añadir")
        }

        // Muestra mensaje de error si existe
        if (mensajeError.isNotEmpty()) {
            Text(mensajeError)
        }

        // Dibuja cada elemento de la lista (funciona igual si es List<Int>, List<Double> o List<String>)
        for (numero in numeros) {
            Text("• $numero")
        }

        // BOTÓN "Calcular suma":
        Button(
            onClick = {
                if (numeros.isEmpty()) {
                    mensajeError = "No hay números para calcular"
                } else {
                    mensajeError = ""
                    onCalcular() // Dispara el cálculo y la navegación en AppNavigation
                }
            }
        ) {
            Text("Calcular suma")
        }
    }
}
