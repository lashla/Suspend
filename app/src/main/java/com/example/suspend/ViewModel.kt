package com.example.suspend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import java.math.BigInteger

open class MyViewModel: ViewModel() {
    private var isCalculationFinished = false
    val resultData: MutableLiveData<BigInteger> by lazy{
        MutableLiveData<BigInteger>()
    }

    private fun calculateFactorial(number: Int): BigInteger{
        var result: BigInteger = BigInteger.ONE
        for (i in 1..number) {
            result *= i.toBigInteger()
        }
        return result
    }

    fun startFactorialCalculation(number1: Int, number2: Int){
        viewModelScope.launch {
            delay(1000)
            resultData.value = calculateFactorialsSum(number1, number2)
        }
    }

    private suspend fun calculateFactorialsSum(number1: Int, number2: Int) = withContext(Dispatchers.Default) {
        val factorialOne = viewModelScope.async { calculateFactorial(number1) }.await()
        val factorialTwo = viewModelScope.async { calculateFactorial(number2) }.await()
        isCalculationFinished = true
        return@withContext factorialOne + factorialTwo
    }


}