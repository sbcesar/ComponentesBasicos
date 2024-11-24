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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainContent()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    MainContent()
}

@Composable
fun MainContent() {
    var isChecked1 by remember { mutableStateOf(false) }
    var isChecked2 by remember { mutableStateOf(false) }
    var isChecked3 by remember { mutableStateOf(false) }

    var isTextBoxEnabled by remember { mutableStateOf(true) }

    var stateObjetive1 = mutableMapOf(1 to isChecked1)
    var stateObjetive2 = mutableMapOf(2 to isChecked2)
    var stateObjetive3 = mutableMapOf(3 to isChecked3)

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

            TextBoxWithSwitch(
                stateObjetive1 = stateObjetive1,
                stateObjetive2 = stateObjetive2,
                stateObjetive3 = stateObjetive3,
                isTextBoxEnabled = isTextBoxEnabled,
                onToggle = { isTextBoxEnabled = it }
            )
        }
    }
}


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

@Composable
fun TextBoxWithSwitch(
    stateObjetive1: MutableMap<Int, Boolean>,
    stateObjetive2: MutableMap<Int, Boolean>,
    stateObjetive3: MutableMap<Int, Boolean>,
    isTextBoxEnabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    var textContent by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(if (isTextBoxEnabled) colorResource(R.color.checkbox_purple) else Color.DarkGray)
                .padding(16.dp)
                .fillMaxWidth(0.7f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isTextBoxEnabled) {
                    val lowestMap = findLowest(stateObjetive1, stateObjetive2, stateObjetive3)
                    when (lowestMap) {
                        stateObjetive1 -> "Objetivo 1"
                        stateObjetive2 -> "Objetivo 2"
                        stateObjetive3 -> "Objetivo 3"
                        else -> "Ningún objetivo seleccionado"
                    }
                } else {
                    ""
                },
                color = if (isTextBoxEnabled) Color.Black else Color.Gray
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { /* Acción al presionar "Mostrar" */ },
                modifier = Modifier.padding(end = 16.dp),
                colors = ButtonColors(
                    containerColor = colorResource(R.color.checkbox_purple),
                    disabledContainerColor = Color.DarkGray,
                    contentColor = Color.White,
                    disabledContentColor = Color.White
                )
            ) {
                Text(text = "Mostrar")
            }

            Switch(
                checked = isTextBoxEnabled,
                onCheckedChange = onToggle,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.White,
                    checkedTrackColor = colorResource(R.color.checkbox_purple),
                    uncheckedTrackColor = Color.DarkGray
                )
            )
        }
    }
}



@Composable
fun findLowest(
    stateObjetive1: MutableMap<Int, Boolean>,
    stateObjetive2: MutableMap<Int, Boolean>,
    stateObjetive3: MutableMap<Int, Boolean>
): MutableMap<Int,Boolean> {
    val allMaps = listOf(stateObjetive1, stateObjetive2, stateObjetive3)

    var lowerMap: MutableMap<Int, Boolean>? = null
    var lowerKey: Int? = null

    for (map in allMaps) {
        for ((key, value) in map) {
            if (value) {
                if (lowerKey == null || key < lowerKey) {
                    lowerKey = key
                    lowerMap = map
                }
            }
        }
    }

    return lowerMap ?: mutableMapOf()
}