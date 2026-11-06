package eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

/**
 * ============================================================================
 * CLASE: SumaViewModel
 * ============================================================================
 * ¿QUÉ ES UN VIEWMODEL?
 * Es una clase que almacena y gestiona el estado y la lógica de la pantalla.
 * Ventajas fundamentales para el examen:
 * 1. SOBREVIVE a cambios de configuración: Si el usuario rota la pantalla, la
 *    Activity se destruye y se vuelve a crear, pero el ViewModel NO se destruye.
 *    Los datos siguen ahí intactos.
 * 2. SEPARACIÓN DE RESPONSABILIDADES: La pantalla (Composable) solo muestra cosas;
 *    toda la lógica de negocio (añadir, calcular, limpiar) reside aquí.

 * 🎓 CÓMO DEFINIR LISTAS Y VARIABLES DE OTROS TIPOS EN EL VIEWMODEL:
 * ----------------------------------------------------------------------------
 * 1. Con DECIMALES (Double):
 *      private val _precios = mutableStateListOf<Double>()
 *      val precios: List<Double> get() = _precios
 *      var total by mutableStateOf<Double?>(null)
 *          private set
 *      fun agregarPrecio(precio: Double) { _precios.add(precio) }
 *
 * 2. Con TEXTOS (String - ej: lista de nombres o tareas):
 *      private val _tareas = mutableStateListOf<String>()
 *      val tareas: List<String> get() = _tareas
 *      fun agregarTarea(tarea: String) { _tareas.add(tarea) }
 *
 * 3. Con OBJETOS (Data Class):
 *      data class Contacto(val nombre: String, val telefono: String)
 *      private val _contactos = mutableStateListOf<Contacto>()
 *      val contactos: List<Contacto> get() = _contactos
 *      fun agregarContacto(c: Contacto) { _contactos.add(c) }
 *
 * 4. Estados BOOLEANOS (ej: carga o activación):
 *      var cargando by mutableStateOf(false)
 *          private set
 * ============================================================================
 */
class SumaViewModel : ViewModel() {

    // ------------------------------------------------------------------------
    // ENCAPSULACIÓN DEL ESTADO (PATRÓN BACKING PROPERTY)
    // ------------------------------------------------------------------------

    // Variable privada y mutable: Solo el ViewModel puede modificar la lista directamente.
    // Usamos mutableStateListOf para que Compose observe cambios automáticamente.
    private val _numeros = mutableStateListOf<Int>()

    // Propiedad pública de solo lectura: La UI puede leer los números pero NO modificarlos
    // directamente (así obligamos a la UI a llamar a funciones como 'agregarNumero').
    val numeros: List<Int>
        get() = _numeros

    // Estado observable para el resultado final de la suma.
    // 'private set' significa: cualquiera puede leer 'resultado', pero SOLO SumaViewModel
    // puede modificar su valor.
    // (Si fuera Double: var resultado by mutableStateOf<Double?>(null))
    var resultado by mutableStateOf<Int?>(null)
        private set

    // ------------------------------------------------------------------------
    // FUNCIONES DE LÓGICA DE NEGOCIO (ACCIONES)
    // ------------------------------------------------------------------------

    /**
     * Añade un nuevo número entero a la lista interna.
     */
    fun agregarNumero(numero: Int) {
        _numeros.add(numero)
    }

    /**
     * Calcula la suma de todos los números si la lista no está vacía,
     * y guarda el valor en el estado 'resultado'.
     *
     * 💡 OTRAS OPERACIONES TÍPICAS EN EXAMEN:
     * - Media (devuelve Double):          val media: Double = _numeros.average()
     * - Suma automática directa:          resultado = _numeros.sum()
     * - Máximo y mínimo:                  val max = _numeros.maxOrNull()
     * - Contar con condición:             val mayores = _numeros.count { it > 10 }
     */
    fun calcular() {
        if (_numeros.isNotEmpty()) {
            resultado = calcularSuma(_numeros)
        }
    }

    /**
     * Limpia la lista de números y resetea el resultado a null.
     * Se utiliza cuando el usuario presiona "Reiniciar".
     */
    fun reiniciar() {
        _numeros.clear()
        resultado = null
    }
}

/**
 * FUNCIÓN AUXILIAR DE CÁLCULO:
 * Recibe una lista inmutable de enteros y retorna la suma de todos sus elementos.
 *
 * VARIACIÓN PARA DOUBLE:
 * fun calcularSumaDouble(numeros: List<Double>): Double {
 *     var suma = 0.0
 *     for (n in numeros) { suma += n }
 *     return suma
 * }
 */
fun calcularSuma(numeros: List<Int>): Int {
    var suma = 0

    for (numero in numeros) {
        suma += numero
    }

    return suma
}
