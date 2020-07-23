package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    // Check iof the last pressed key is a number / Decimal
    var lastNum : Boolean = false
    var lastDot : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    // This onDigit function is added to the number buttons' property
    fun onDigit(view: View) {
        // Append the text from the selected button to the text view
        tvInput.append((view as Button).text)
        // Change the lastNum flag to true
        lastNum = true
    }

    // The onClear function is added to the CLR button's property
    fun onClear(view: View) {
        // Clear the screen
        tvInput.setText("")
        // Reset flags
        lastDot = false
        lastNum = false
    }

    // on Dot is applied to the Decimal point button
    fun onDot(view: View) {
        // There can only be one decimal point number for a number
        if (lastNum && !lastDot) {
            tvInput.append(".")
            lastNum = false
            lastDot = true
        }
    }

    // Check if an operator is already added
    private fun isOperAdded(value: String) : Boolean {
        return if(value.startsWith("-")) {
            // Ignore if the starting number is a negative value
            false
        } else {
            // Return true if an operation is already added
            value.contains("+") || value.contains("-") || value.contains("*") || value.contains("/")
        }
    }

    // Appends the applied operation, unless an operation is already present
    fun onOperator(view: View) {
        if (lastNum && !isOperAdded(tvInput.text.toString())) {
            tvInput.append((view as Button).text)
            lastNum = false
            lastDot = false
        }
    }

    // Private function to remove the 0s after decimal value.
    private fun clearExtraZeros(result: String) : String {
        var value = result

        if (result.contains(".0")) {
            value = result.substring(0, result.length - 2)
        }
        return value
    }

    // Perform the final calculations
    fun onEquals(view: View) {
        if (lastNum) {
            // Read the text value
            var tvValue = tvInput.text.toString()
            var prefix = ""

            try {
                // Check if the string starts with a minus.
                // This helps avoid errors when splitting strings using operator
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }

                // Calculate
                if (tvValue.contains("-")) {
                    // Subtraction
                    // Split the tvInput string to the left and right of the operator
                    val splitValue = tvValue.split("-")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    // Re-apply minus sign if the first number is a negative
                    if (!prefix.isEmpty()) {
                        first = prefix + first
                    }
                    // Final calculation
                    tvInput.text = clearExtraZeros((first.toDouble() - second.toDouble()).toString())
                } else if (tvValue.contains("+")) {
                    //Addition
                    // Split the tvInput string to the left and right of the operator
                    val splitValue = tvValue.split("+")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    // Re-apply minus sign if the first number is a negative
                    if (!prefix.isEmpty()) {
                        first = prefix + first
                    }
                    // Final calculation
                    tvInput.text = clearExtraZeros((first.toDouble() + second.toDouble()).toString())
                } else if (tvValue.contains("*")) {
                    // Multiplication
                    // Split the tvInput string to the left and right of the operator
                    val splitValue = tvValue.split("*")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    // Re-apply minus sign if the first number is a negative
                    if (!prefix.isEmpty()) {
                        first = prefix + first
                    }
                    // Final calculation
                    tvInput.text = clearExtraZeros((first.toDouble() * second.toDouble()).toString())
                } else if (tvValue.contains("/")) {
                    // Division
                    // Split the tvInput string to the left and right of the operator
                    val splitValue = tvValue.split("/")
                    var first = splitValue[0]
                    var second = splitValue[1]

                    // Re-apply minus sign if the first number is a negative
                    if (!prefix.isEmpty()) {
                        first = prefix + first
                    }
                    // Final calculation
                    tvInput.text = clearExtraZeros((first.toDouble() / second.toDouble()).toString())
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }


        }
    }

}