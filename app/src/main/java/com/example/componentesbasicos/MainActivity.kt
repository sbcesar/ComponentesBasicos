package com.example.componentesbasicos

import androidx.compose.runtime.*
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


/**
 * La actividad principal que inicia la aplicación y configura el contenido de la pantalla.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

/**
 * Vista previa del diseño en herramientas de desarrollo (Android Studio).
 * Permite ver la interfaz de usuario sin ejecutar la aplicación.
 */
@Preview(showBackground = true)
@Composable
fun Preview() {
    MainContent()
}

/**
 * Componente principal que define el diseño de la pantalla.
 * Incluye un título, un conjunto de checkboxes, un botón y un switch.
 */
@Composable
fun MainContent() {
    var isButtonEnabled by remember { mutableStateOf(true) }    // Estado del switch
    var isShowButtonPressed by remember { mutableStateOf(false) }   // Estado del botón
    var selectedObjective by remember { mutableStateOf("") }    // Objetivo seleccionado

    // Estados
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var isChecked3 by remember { mutableStateOf(false) }

    // Mapa que asocia el estado y las checkboxes
    val stateObjectives = mapOf(
        1 to isChecked1,
        2 to isChecked2,
        3 to isChecked3
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(R.color.background_layer_1_gray))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp, top = 30.dp)
                .background(colorResource(R.color.background_layer_2_gray)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Práctica Componentes",
                color = Color.White,
                modifier = Modifier
                    .padding(vertical = 16.dp),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.7f),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                colors = CardDefaults.cardColors(containerColor = colorResource(R.color.background_card))
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CheckBox(
                        checked = isChecked1,
                        onCheckedChange = { isChecked1 = it },
                        label = "Objetivo1"
                    )
                    CheckBox(
                        checked = isChecked2,
                        onCheckedChange = { isChecked2 = it },
                        label = "Objetivo2"
                    )
                    CheckBox(
                        checked = isChecked3,
                        onCheckedChange = { isChecked3 = it },
                        label = "Objetivo3"
                    )
                }
            }

            Spacer(modifier = Modifier.height(25.dp))

            TextBox(text = selectedObjective)

            Spacer(modifier = Modifier.height(16.dp))

            ButtonAndSwitch(
                isButtonEnabled = isButtonEnabled,
                onSwitchToggle = { enabled ->
                    isButtonEnabled = enabled
                    if (!enabled) {
                         selectedObjective = ""
                         isShowButtonPressed = false
                    } },
                onButtonClick = {
                    if (isButtonEnabled) {
                        isShowButtonPressed = true
                        selectedObjective = getLowestObjective(stateObjectives)
                    }
                }
            )
        }
    }
}

/**
 * Caja de texto estilizada que muestra el texto proporcionado.
 *
 * @param text El texto que se mostrará en la caja.
 */
@Composable
fun TextBox(text: String) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(colorResource(R.color.checkbox_purple))
            .padding(16.dp)
            .fillMaxWidth(0.7f),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = Color.Black
        )
    }
}

/**
 * Botón para mostrar el objetivo más bajo y un switch para habilitar o deshabilitar el botón.
 *
 * @param isButtonEnabled Indica si el botón está habilitado.
 * @param onSwitchToggle Acción que se ejecuta al cambiar el estado del switch.
 * @param onButtonClick Acción que se ejecuta al presionar el botón.
 */
@Composable
fun ButtonAndSwitch(
    isButtonEnabled: Boolean,
    onSwitchToggle: (Boolean) -> Unit,
    onButtonClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onButtonClick,
            enabled = isButtonEnabled,
            modifier = Modifier.padding(end = 16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isButtonEnabled) colorResource(R.color.checkbox_purple) else Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(text = "Mostrar")
        }

        Switch(
            checked = isButtonEnabled,
            onCheckedChange = onSwitchToggle,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                uncheckedThumbColor = Color.White,
                checkedTrackColor = colorResource(R.color.checkbox_purple),
                uncheckedTrackColor = Color.DarkGray
            )
        )
    }
}

/**
 * Checkbox estilizado con una etiqueta.
 *
 * @param checked Estado del checkbox.
 * @param onCheckedChange Acción a realizar cuando cambia el estado.
 * @param label Etiqueta asociada al checkbox.
 */
@Composable
fun CheckBox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            enabled = true,
            colors = CheckboxDefaults.colors(
                checkedColor = colorResource(R.color.checkbox_purple),
                uncheckedColor = Color.Gray,
                checkmarkColor = Color.Black,
                disabledUncheckedColor = Color.DarkGray
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = label,
            color = Color.White
        )
    }
}

/**
 * Obtiene el objetivo más bajo entre los seleccionados.
 *
 * @param stateObjectives Mapa que contiene los objetivos y su estado (seleccionado o no).
 * @return El texto que representa el objetivo más bajo seleccionado, o un mensaje indicando que no hay objetivos seleccionados.
 */
fun getLowestObjective(stateObjectives: Map<Int, Boolean>): String {
    var lowestObjetive: Int? = null

    for ((key, value) in stateObjectives) {
        if (value) {
            if (lowestObjetive == null || key < lowestObjetive) {
                lowestObjetive = key
            }
        }
    }

    return if (lowestObjetive != null) {
        "Objetivo $lowestObjetive"
    } else {
        "Ningún objetivo seleccionado"
    }

}
