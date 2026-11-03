package ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VentanaPrincipal(modifier: Modifier = Modifier, onNavigateToResultado: (String) -> Unit) {
    var expresion by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = expresion,
            onValueChange = { expresion = it },
            label = { Text("Ecuación") },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila 1: 1, 2, 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { expresion += "1" }) { Text(text = "1") }
            Button(onClick = { expresion += "2" }) { Text(text = "2") }
            Button(onClick = { expresion += "3" }) { Text(text = "3") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 2: 4, 5, 6
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { expresion += "4" }) { Text(text = "4") }
            Button(onClick = { expresion += "5" }) { Text(text = "5") }
            Button(onClick = { expresion += "6" }) { Text(text = "6") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 3: 7, 8, 9
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { expresion += "7" }) { Text(text = "7") }
            Button(onClick = { expresion += "8" }) { Text(text = "8") }
            Button(onClick = { expresion += "9" }) { Text(text = "9") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 4: +, 0, -, =
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { expresion += "+" }) { Text(text = "+") }
            Button(onClick = { expresion += "0" }) { Text(text = "0") }
            Button(onClick = { expresion += "-" }) { Text(text = "-") }

        }
        // Fila extra para Multiplicación y División
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { expresion += "*" }) { Text(text = "*") }
            Button(onClick = { expresion += "/" }) { Text(text = "/") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila de utilidades (Borrar)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                if (expresion.isNotEmpty()) {
                    expresion = expresion.dropLast(1)
                }
            }) {
                Text(text = "⌫ Borrar")
            }

            // Si quieres un botón de limpiar todo ("C") de golpe:
            Button(onClick = { expresion = "" }) {
                Text(text = "C (Todo)")
            }

        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (expresion.isNotEmpty()) {
                        onNavigateToResultado(expresion)
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f) // Ocupa un 80% del ancho para que destaque bien
            ) {
                Text(text = "=", fontSize = 20.sp)
            }
        }
    }
}