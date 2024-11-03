package com.example.mycalculator.ui.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import com.example.mycalculator.data.remote.FactorDBClient
import com.example.mycalculator.ui.viewmodel.CalculatorViewModel
import kotlinx.coroutines.launch
import org.mariuszgromada.math.mxparser.Expression

@Composable
fun MyGreeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello 2 $name!",
        modifier = modifier
    )
}

@Composable
fun Calculator() {
    var input by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    //Don't need to use by to delegate getter and setter methods
    // remember creates a 'stable instance', which is remembered across recompositions

    val myViewModel = remember { CalculatorViewModel(FactorDBClient.api) }



    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TextField(
            value = input,
            onValueChange = { input = it },
            label = { Text("Enter expression") },
            readOnly = true, // Make the TextField read-only
            modifier = Modifier.fillMaxWidth()
        )

        // Number buttons grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(listOf("7", "8", "9", "4", "5", "6", "1", "2", "3", "0")) { number ->
                Button(onClick = { input += number }) {
                    Text(number)
                }
            }
        }

        // Operator buttons and Calculate button
        Row(
            modifier = Modifier.fillMaxWidth()
                    ,

            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Button(onClick = { input += "+" }) { Text("+") }
            Button(onClick = { input += "-" }) { Text("-") }
            Button(onClick = { input += "*" }) { Text("*") }
            Button(onClick = { input += "/" }) { Text("/") }
            Button(onClick = { input += "." }) { Text(".") }
            }

        // Calculate button
        Button(onClick = {
            try {
                result = calculate(input).toString()

            } catch (e: Exception) {
                result = "Error"
            }

        },
            modifier = Modifier.fillMaxWidth()) { Text("Calculate") }

        // factorize button
        Button(onClick = {

            myViewModel.viewModelScope.launch {
                result = "waiting..."
                result = myViewModel.getFactors(calculate(input).toLong()).toString()
            }

        },
            modifier = Modifier.fillMaxWidth()) { Text("Factorize") }


        // Clear button
        Button(
            onClick = { input = ""; result = "" },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Clear")
        }

        // Result display
        Text(
            text = "Result: $result",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

// Assume calculate(input) function is defined elsewhere to evaluate the expression
fun calculate(expression: String): Double {
    val expr = Expression(expression)
    return expr.calculate()
    }


