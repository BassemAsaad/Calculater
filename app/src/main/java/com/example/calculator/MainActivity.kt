package com.example.calculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var firstOperand: Double? = null
    private var pendingOperator: String? = null
    private var resetNextInput = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set number buttons
        listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9, binding.buttonDot
        ).forEach { btn ->
            btn.setOnClickListener { appendNumber(btn.text.toString()) }
        }

        // Set operators
        listOf(
            binding.buttonPlus, binding.buttonMinus,
            binding.buttonMultiply, binding.buttonDivide
        ).forEach { btn ->
            btn.setOnClickListener { applyOperator(btn.text.toString()) }
        }

        // Equals & Clear
        binding.buttonEqual.setOnClickListener { calculateResult() }
        binding.buttonClear.setOnClickListener { clearAll() }
    }

    private fun appendNumber(num: String) {
        if (resetNextInput) {
            binding.resultTv.text = ""
            resetNextInput = false
        }

        // prevent multiple dots
        if (num == "." && binding.resultTv.text.contains(".")) return

        if (binding.resultTv.text.toString() == "0" && num != ".") {
            binding.resultTv.text = num
        } else {
            binding.resultTv.append(num)
        }
    }

    private fun applyOperator(op: String) {
        val currentValue = binding.resultTv.text.toString().toDoubleOrNull() ?: return

        firstOperand = if (firstOperand == null) {
            currentValue
        } else if (pendingOperator != null) {
            calculate(firstOperand!!, pendingOperator!!, currentValue)
        } else {
            currentValue
        }

        binding.resultTv.text = formatResult(firstOperand!!)
        pendingOperator = op
        resetNextInput = true
    }

    private fun calculateResult() {
        val currentValue = binding.resultTv.text.toString().toDoubleOrNull() ?: return

        if (firstOperand != null && pendingOperator != null) {
            val result = calculate(firstOperand!!, pendingOperator!!, currentValue)
            binding.resultTv.text = formatResult(result)
            firstOperand = null
            pendingOperator = null
            resetNextInput = true
        }
    }

    private fun calculate(a: Double, operator: String, b: Double): Double {
        return when (operator) {
            "+" -> a + b
            "-" -> a - b
            "×" -> a * b
            "÷" -> if (b != 0.0) a / b else Double.NaN
            else -> b
        }
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) value.toInt().toString() else value.toString().trimEnd('0').trimEnd('.')
    }

    private fun clearAll() {
        binding.resultTv.text = "0"
        firstOperand = null
        pendingOperator = null
        resetNextInput = false
    }
}