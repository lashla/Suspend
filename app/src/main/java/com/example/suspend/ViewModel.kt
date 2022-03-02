package com.example.suspend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

open class MyViewModel: ViewModel() {

    val inputData: MutableLiveData<Int> by lazy{
        MutableLiveData<Int>()
    }

    private fun factorialCalc(number: Int): Int{
        var result = 1
        for (i in 1..number) {
            result *= i
        }
        return result
    }

    fun calculationHandler(number1: Int, number2: Int){
        viewModelScope.launch {
            delay(1000)
            inputData.value = calcResult(number1, number2)
        }
    }

    suspend fun calcResult(number1: Int, number2: Int) = withContext(Dispatchers.Default) {
            val factorialOne = viewModelScope.async { factorialCalc(number1) }.await()
            val factorialTwo = viewModelScope.async { factorialCalc(number2) }.await()
            return@withContext factorialOne + factorialTwo
    }


}