package com.test.android44_ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.test.android44_ex01.DataClass.Companion.fruitList
import com.test.android44_ex01.databinding.ActivityFruitBinding

class FruitActivity : AppCompatActivity() {

    lateinit var activityFruitBinding: ActivityFruitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFruitBinding = ActivityFruitBinding.inflate(layoutInflater)
        setContentView(activityFruitBinding.root)

        activityFruitBinding.run {
            for(fruit in fruitList) {
                Log.d("lion","${fruit.name}")
            }

            val position = intent.getIntExtra("position",0)

            Log.d("lion","$position")

            textViewFruitName.text = fruitList[position].name
            textViewFruitNumber.text = fruitList[position].number.toString()
            textViewFruitFrom.text = fruitList[position].region

            buttonMain.run {
                setOnClickListener {
                    finish()
                }
            }
        }
    }
}