package com.test.android14_ex02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android14_ex02.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run{
            buttonPlus.run{
                setOnClickListener {
                    val number1 = editTextNumber1.text.toString().toInt()
                    val number2 = editTextNumber2.text.toString().toInt()

                    textViewResult.text = "$number1 + $number2 = ${number1 + number2}"
                }
            }
            buttonMinus.run{
                setOnClickListener {
                    val number1 = editTextNumber1.text.toString().toInt()
                    val number2 = editTextNumber2.text.toString().toInt()

                    textViewResult.text = "$number1 - $number2 = ${number1 - number2}"
                }
            }
            buttonMultiply.run{
                setOnClickListener {
                    val number1 = editTextNumber1.text.toString().toInt()
                    val number2 = editTextNumber2.text.toString().toInt()

                    textViewResult.text = "$number1 * $number2 = ${number1 * number2}"
                }
            }
            buttonDivide.run{
                setOnClickListener {
                    val number1 = editTextNumber1.text.toString().toInt()
                    val number2 = editTextNumber2.text.toString().toInt()

                    textViewResult.text = "$number1 / $number2 = ${number1 / number2}"
                }
            }
        }
    }
}