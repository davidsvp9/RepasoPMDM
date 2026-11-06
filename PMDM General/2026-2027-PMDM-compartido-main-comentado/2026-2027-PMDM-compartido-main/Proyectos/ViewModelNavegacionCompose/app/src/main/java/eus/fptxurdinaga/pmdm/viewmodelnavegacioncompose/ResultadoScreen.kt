package eus.fptxurdinaga.pmdm.viewmodelnavegacioncompose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

/**
 * ============================================================================
 * PANTALLA: ResultadoScreen
 * ============================================================================
 *
 * 🎓 CÓMO CAMBIARÍA SEGÚN EL TIPO DE RESULTADO QUE SE QUIERA MOSTRAR:
 * ----------------------------------------------------------------------------
 * 1. Con DECIMALES (Double?):
 *      fun ResultadoScreen(operacion: String, resultado: Double?, ...)
 *      // Formatear a 2 decimales:
 *      Text(resultado?.let { "%.2f".format(it) } ?: "Sin resultado")
 *      // Formatear con euros:
 *      Text(resultado?.let { "%.2f €".format(it) } ?: "0.00 €")
 *
 * 2. Con BOOLEANOS (Boolean):
 *      fun ResultadoScreen(esAprobado: Boolean, ...)
 *      Text(if (esAprobado) "Aprobado" else "Suspenso")
 *
 * 3. Mostrando una lista o historial:
 *      fun ResultadoScreen(historial: List<String>, ...)
 *      for (item in historial) { Text(item) }
 * ============================================================================
 */
@Composable
fun ResultadoScreen(
    operacion: String,       // Nombre de la operación (ej: "suma")
    resultado: Int?,         // Resultado numérico (Int anulable)
    onReiniciar: () -> Unit  // Acción para reiniciar y volver atrás
) {
    Column {
        Text("Resultado de $operacion")

        // Transformación de Int? a String de forma segura
        Text(
            resultado?.toString()
                ?: "Sin resultado"
        )

        Button(
            onClick = onReiniciar
        ) {
            Text("Reiniciar")
        }
    }
}
