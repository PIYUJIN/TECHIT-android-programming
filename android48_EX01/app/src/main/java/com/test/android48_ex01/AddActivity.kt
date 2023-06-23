package com.test.android48_ex01

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.DatePicker
import com.test.android48_ex01.DataClass.Companion.personList
import com.test.android48_ex01.databinding.ActivityAddBinding
import java.util.Calendar
import kotlin.concurrent.thread

class AddActivity : AppCompatActivity() {

    lateinit var activityAddBinding: ActivityAddBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityAddBinding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(activityAddBinding.root)

        val genderList = arrayOf(
            "남자", "여자"
        )

        val hobbyList = arrayOf(
            "영화감상", "독서", "헬스", "골프", "낚시", "당구", "게임"
        )

        val hobbyCheckArray = BooleanArray(hobbyList.size) { i -> false }

        activityAddBinding.run{
            editTextName.requestFocus()

            thread {
                SystemClock.sleep(500)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(editTextName, 0)
            }

            buttonDate.setOnClickListener {
                val calendar = Calendar.getInstance()

                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val pickerDialog = DatePickerDialog(this@AddActivity,{ datePicker: DatePicker, i: Int, i1: Int, i2: Int ->
                    buttonDate.text = "${i} 년 ${i1 + 1} 월 ${i2} 일"
                },year,month,day)

                pickerDialog.show()
            }

            buttonGender.setOnClickListener {
                val builder = AlertDialog.Builder(this@AddActivity)
                builder.setTitle("성별")
                builder.setNegativeButton("취소", null)

                builder.setItems(genderList){ dialogInterface: DialogInterface, i: Int ->
                    buttonGender.text = genderList[i]
                }
                builder.show()
            }

            buttonHobby.setOnClickListener {
                val builder = AlertDialog.Builder(this@AddActivity)
                builder.setTitle("취미")
                builder.setMultiChoiceItems(hobbyList,hobbyCheckArray){ dialogInterface: DialogInterface, i: Int, b: Boolean ->
                    hobbyCheckArray[i] = b
                }

                builder.setNegativeButton("취소",null)
                builder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                    var c1 = 0
                    for(idx in 0 until hobbyCheckArray.size){
                        if(hobbyCheckArray[idx] == true){
                            if(c1 == 0){
                                buttonHobby.text = "${hobbyList[idx]}"
                                c1++
                            } else {
                                c1++
                            }
                        }
                    }

                    if(c1 > 1){
                        buttonHobby.append(" 외 ${c1 - 1}개")
                    }

                    if(c1 == 0){
                        buttonHobby.text = "취미 없음"
                    }
                }
                builder.show()
            }

            buttonApply.setOnClickListener {
                if(editTextName.text.isEmpty()) {
                    val builder = AlertDialog.Builder(this@AddActivity)
                    builder.setTitle("이름 입력 오류")
                    builder.setMessage("이름을 입력해주세요.")
                    builder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                        editTextName.requestFocus()
                    }
                    builder.show()
                    return@setOnClickListener
                }

                // 날짜
                if(buttonDate.text == "날짜 입력"){
                    val builder = androidx.appcompat.app.AlertDialog.Builder(this@AddActivity)
                    builder.setTitle("날짜 입력 오류")
                    builder.setMessage("날짜를 입력해주세요")
                    builder.setPositiveButton("확인", null)
                    builder.show()
                    return@setOnClickListener
                }
                // 성별
                if(buttonGender.text == "성별"){
                    val builder = androidx.appcompat.app.AlertDialog.Builder(this@AddActivity)
                    builder.setTitle("성별 입력 오류")
                    builder.setMessage("성별을 입력해주세요")
                    builder.setPositiveButton("확인", null)
                    builder.show()
                    return@setOnClickListener
                }


                val name = editTextName.text.toString()
                val date = buttonDate.text.toString()
                val gender = buttonGender.text.toString()
                val hobbyInputList = mutableListOf<String>()
                for (i in 0 until hobbyCheckArray.size) {
                    if(hobbyCheckArray[i]==true) {
                        hobbyInputList.add(hobbyList[i])
                    }
                }

                personList.add(PersonInfo(name,date,gender,hobbyInputList))

                finish()
            }

            buttonCancel.setOnClickListener {
                finish()
            }
        }
    }
}