package com.test.android14_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android14_ex01.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run{
            button.run{
                setOnClickListener {
                    val inputNumber = editTextNumber.text.toString().toInt()

                    textView.text = ""

                    for(a1 in 1..9){
                        textView.append("$inputNumber X $a1 = ${inputNumber * a1}\n")
                    }
                }
            }
        }
    }
}