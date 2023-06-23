package com.test.android48_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.test.android48_ex01.DataClass.Companion.personList
import com.test.android48_ex01.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    lateinit var activityResultBinding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityResultBinding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(activityResultBinding.root)

        activityResultBinding.run{

            val position = intent.getIntExtra("position", 0)

            textViewName.text = personList[position].name
            textViewDate.text = personList[position].date
            textViewGender.text = personList[position].gender

            for(s1 in personList[position].hobbyList){
                textViewHobby.append("${s1}\n")
            }

            if(personList[position].hobbyList==null) {
                textViewHobby.text = "취미 없음"
            }

            buttonToMain.setOnClickListener {
                finish()
            }
        }

    }
}