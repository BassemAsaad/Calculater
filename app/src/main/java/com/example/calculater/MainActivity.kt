package com.example.calculater

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.calculater.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var operator: String = ""
    private var result: String = ""
    private var rhs: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

    @SuppressLint("SetTextI18n")
    fun onButtonClick(view: View) {
        binding.resultTv.text = ""
        binding.resultTv.append((view as Button).text)
    }

    fun onOperatorClick(view: View) {
        //first case
        val clickOperator = view as Button
        if (operator.isEmpty()) {
            result = binding.resultTv.getText().toString()
            operator = clickOperator.text.toString()
            binding.resultTv.text = null
        } else {
            //rhs= right hand side
            rhs = binding.resultTv.getText().toString()
            result = calculate(result, operator, rhs)

            operator = clickOperator.text.toString()
            binding.resultTv.text = null
        }
    }

    private fun calculate(lhs: String, operator: String, rhs: String): String {
        val num1 = lhs.toDouble()
        val num2 = rhs.toDouble()
        var res: Double? = null
        when (operator) {
            "+" -> {
                res = num1 + num2
            }
            "-" -> {
                res = num1 - num2
            }

            "X" -> {
                res = num1 * num2
            }

            "/" -> {
                res = num1 / num2
            }
        }
        return res.toString() + ""


    }


    fun onEqualClick(view: View) {
        rhs = binding.resultTv.getText().toString()
        result = calculate(result, operator, rhs)
        binding.resultTv.text = result
        operator = ""
        result = ""
    }
}