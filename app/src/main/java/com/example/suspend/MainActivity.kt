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
        incrementText()
    }

    private suspend fun incrementText() {
        button.setOnClickListener{
            val inputNumberOne = (editTextNumber.text.toString()).toInt()
            val inputNumberTwo = (editTextNumber2.text.toString()).toInt()
            if (editTextNumber.text.isNullOrEmpty()
                && editTextNumber2.text.isNullOrEmpty()
                && !validator.isNumberValid(inputNumberOne)
                && !validator.isNumberValid(inputNumberTwo)){
                Toast.makeText(this, "One of inputs is invalid", Toast.LENGTH_LONG).show()
            } else {
                progressBar.visibility = View.VISIBLE
                viewModel.result(inputNumberOne, inputNumberTwo)
                viewModel.inputData.observe(this){
                    textView.text = it.toString()
                }
                progressBar.visibility = View.INVISIBLE
                textView.visibility = View.VISIBLE
                textView.text = viewModel.inputData.toString()
            }
        }
    }

}