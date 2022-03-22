package com.example.suspend


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MyViewModel::class.java]
        setupCalculateButtonListener()
    }



    private fun setupCalculateButtonListener() {
        button.setOnClickListener{
            onCalculateButtonClicked()
        }
        initViewModel()
    }

    private fun invokeCalculations(inputNumberOne: Int, inputNumberTwo: Int) {
        viewModel.startFactorialCalculation(inputNumberOne, inputNumberTwo)
    }

    private fun onCalculateButtonClicked() {
        val inputNumberOne = (editTextNumber.text.toString()).toInt()
        val inputNumberTwo = (editTextNumber2.text.toString()).toInt()
        if (editTextNumber.text.isNullOrEmpty()
            && editTextNumber2.text.isNullOrEmpty()
            && !validator.isNumberValid(inputNumberOne)
            && !validator.isNumberValid(inputNumberTwo)){
            Toast.makeText(this, "One of inputs is invalid", Toast.LENGTH_LONG).show()
        } else {
            invokeCalculations(inputNumberOne,inputNumberTwo)
            progressBar.visibility = View.VISIBLE
            textView.visibility = View.INVISIBLE
        }
    }

    private fun initViewModel(){
        viewModel.resultData.observe(this){
            textView.text = it.toString()
            progressBar.visibility = View.INVISIBLE
            textView.visibility = View.VISIBLE
        }
    }
}