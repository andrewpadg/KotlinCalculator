package com.example.calculator

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.objecthunter.exp4j.ExpressionBuilder
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    lateinit var txtInput: TextView

    lateinit var txtResult: TextView

    var lastNumeric: Boolean = false

    var stateError: Boolean = false

    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txtInput = findViewById(R.id.current)
        txtResult = findViewById(R.id.result)
    }

    fun onDigit(view: View) {
        if (!txtResult.text.equals("")) {
            txtResult.text = ""
            txtInput.text = ""
        }
        if (stateError) {
            txtInput.text = (view as Button).text
            stateError = false
        } else {
            txtInput.append((view as Button).text)
        }

        lastNumeric = true
        lastDot = false
    }

    fun onDecimalPoint(view: View) {
        if (!txtResult.text.equals("")) {
            txtResult.text = ""
            txtInput.text = ""
        }
        if (lastNumeric && !stateError && !lastDot) {
            txtInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        if (!txtResult.text.equals("")) {
            txtResult.text = ""
            txtInput.text = ""
        }
        if (lastNumeric && !stateError) {
            txtInput.append((view as Button).text)
            lastNumeric = false
            lastDot = false
        }
    }

    fun onClear(view: View) {
        txtInput.text = ""
        txtResult.text = ""
        lastNumeric = false
        stateError = false
        lastDot = false
    }

    fun onEqual(view: View) {
        if (lastNumeric && !stateError) {
            val txt = txtInput.text.toString()
            val expression = ExpressionBuilder(txt).build()

            try {
                val result = expression.evaluate()
                txtResult.text = result.toString()
                lastDot = true
                lastNumeric = false
            } catch (ex: ArithmeticException) {
                txtResult.text = "Error"
                stateError = true
                lastNumeric = false
            }
        }
    }
}
