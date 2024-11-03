package com.example.mycalculator.ui.viewmodel

import android.util.Log
import androidx.activity.result.launch
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycalculator.data.remote.FactorDBApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow

class CalculatorViewModel (
    private val factorDBApi: FactorDBApi
) : ViewModel() {


    private fun evaluateExpression(expression: String): Double {
        // Implement your expression evaluation logic here (e.g., using ScriptEngineManager)
        // ...
        return 0.0 // Placeholder return value
    }
    suspend fun getFactors(number: Long): List<String> {
        //delay(1000)
        //return emptyList()
        return try {
            val response = factorDBApi.getFactors(number)
            response.factors.flatten()
        }
        catch (e: Exception) {
            Log.d("CalculatorViewModel", "Error fetching factors: ${e.message}")
            emptyList()
    }
}
}

