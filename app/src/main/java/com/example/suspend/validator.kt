package com.example.suspend

class validator {
    companion object{
        fun isNumberValid(number: Int): Boolean{
            return number in 1..1000
        }
    }
}