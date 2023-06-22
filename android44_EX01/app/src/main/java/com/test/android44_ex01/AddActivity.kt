package com.test.android44_ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.test.android44_ex01.DataClass.Companion.fruitList
import com.test.android44_ex01.databinding.ActivityAddBinding
import kotlin.concurrent.thread

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding

    val spinnerData = arrayOf(
        "수박","사과","귤"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        activityAddBinding.run {

            spinnerType.run {
                val adapter1 = ArrayAdapter<String> (
                    this@AddActivity,android.R.layout.simple_spinner_item,spinnerData
                )

                adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                adapter = adapter1
            }

            editTextNumber.requestFocus()

            thread{
                SystemClock.sleep(500)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editTextNumber,0)
            }

            editTextFrom.run {
                setOnEditorActionListener { v, actionId, event ->
                    val type = spinnerData[spinnerType.selectedItemPosition]
                    val number = editTextNumber.text.toString().toInt()
                    val region = text.toString()

                    fruitList.add(Fruit(type,number,region))

                    finish()

                    false
                }
            }

            // 버튼 클릭시 화면 종료
            buttonComplete.run {
                setOnClickListener {
                    finish()
                }
            }
        }
    }
}