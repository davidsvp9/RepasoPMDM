package com.example.calculadorafinal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VentanaPrincipal(
    expresion: String,
    onAgregarCaracter: (String) -> Unit,
    onBorrar: () -> Unit,
    onLimpiar: () -> Unit,
    onCalcular: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = expresion,
            onValueChange = {},
            label = { Text("Ecuación") },
            readOnly = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Fila 1: 1, 2, 3
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAgregarCaracter("1") }) { Text(text = "1") }
            Button(onClick = { onAgregarCaracter("2") }) { Text(text = "2") }
            Button(onClick = { onAgregarCaracter("3") }) { Text(text = "3") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 2: 4, 5, 6
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAgregarCaracter("4") }) { Text(text = "4") }
            Button(onClick = { onAgregarCaracter("5") }) { Text(text = "5") }
            Button(onClick = { onAgregarCaracter("6") }) { Text(text = "6") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 3: 7, 8, 9
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAgregarCaracter("7") }) { Text(text = "7") }
            Button(onClick = { onAgregarCaracter("8") }) { Text(text = "8") }
            Button(onClick = { onAgregarCaracter("9") }) { Text(text = "9") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 4: +, 0, -
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAgregarCaracter("+") }) { Text(text = "+") }
            Button(onClick = { onAgregarCaracter("0") }) { Text(text = "0") }
            Button(onClick = { onAgregarCaracter("-") }) { Text(text = "-") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 5: Multiplicación (*) y División (/)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { onAgregarCaracter("*") }) { Text(text = "*") }
            Button(onClick = { onAgregarCaracter("/") }) { Text(text = "/") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila 6: Utilidades (Borrar, Limpiar)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = onBorrar) {
                Text(text = "⌫ Borrar")
            }

            Button(onClick = onLimpiar) {
                Text(text = "C (Todo)")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Botón Calcular (=)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    if (expresion.isNotEmpty()) {
                        onCalcular()
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text(text = "=", fontSize = 20.sp)
            }
        }
    }
}
