package com.example.suspend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger

open class MyViewModel: ViewModel() {
    private var isCalculationFinished = false
    val inputData: MutableLiveData<BigInteger> by lazy{
        MutableLiveData<BigInteger>()
    }

    private fun factorialCalc(number: Int): BigInteger{
        var result: BigInteger = BigInteger.ONE
        for (i in 1..number) {
            result *= i.toBigInteger()
        }
        return result
    }

    fun calculationHandler(number1: Int, number2: Int){
        viewModelScope.launch {
            delay(1000)
            inputData.value = calcResult(number1, number2)
        }
    }

    private suspend fun calcResult(number1: Int, number2: Int) = withContext(Dispatchers.Default) {
        val factorialOne = viewModelScope.async { factorialCalc(number1) }.await()
        val factorialTwo = viewModelScope.async { factorialCalc(number2) }.await()
        isCalculationFinished = true
        return@withContext factorialOne + factorialTwo
    }


}