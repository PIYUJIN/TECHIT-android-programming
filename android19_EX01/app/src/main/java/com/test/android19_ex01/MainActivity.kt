package com.test.android19_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import com.test.android19_ex01.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var imm: InputMethodManager

    val studentList = mutableListOf<StudentInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        activityMainBinding.run {
            editTextName.requestFocus()
            thread {
                SystemClock.sleep(500)
                imm.showSoftInput(currentFocus,0)
            }

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->
                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString().toInt()
                    val Temp1 = RadioGroupGender.checkedRadioButtonId
                    var gender = if(Temp1==R.id.radioButtonWoman) {
                        "여자"
                    } else {
                        "남자"
                    }
                    val korean = editTextKorean.text.toString().toInt()
                    val studentInfo = StudentInfo(name,age,gender,korean)

                    if(checkBoxGame.isChecked){
                        studentInfo.addHobby("게임")
                    }
                    if(checkBoxSoccer.isChecked){
                        studentInfo.addHobby("축구")
                    }
                    if(checkBoxMovie.isChecked){
                        studentInfo.addHobby("영화감상")
                    }

                    // 학생 정보 저장
                    studentList.add(studentInfo)

                    // 입력 내용 초기화
                    editTextName.setText("")
                    editTextAge.setText("")
                    checkBoxGame.isChecked = false
                    checkBoxSoccer.isChecked = false
                    checkBoxMovie.isChecked = false
                    RadioGroupGender.check(R.id.radioButtonMan)
                    editTextKorean.setText("")

                    // 커서 이동
                    editTextName.requestFocus()

                    false
                }
            }

            buttonResult.run {

                setOnClickListener {
                    // 키보드를 내리고 포커스 제거
                    // 포커스부터 제거하는 경우 currentFocus가 없어 오류 발생
                    if(currentFocus != null) {
                        // windowToken = 안드로이드 OS가 내부적으로 사용하는 값
                        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                        currentFocus!!.clearFocus()
                    }

                    textViewResult.text = ""

                    // 국어 점수를 누적할 변수
                    var koreanTotal = 0

                    // 학생의 수 만큼 반복
                    for(studentInfo in studentList){
                        textViewResult.append("이름 : ${studentInfo.name}\n")
                        textViewResult.append("나이 : ${studentInfo.age}\n")

                        textViewResult.append("취미\n")
                        for(hobby in studentInfo.hobby){
                            textViewResult.append("${hobby}\n")
                        }

                        textViewResult.append("성별  : ${studentInfo.gender}\n")
                        textViewResult.append("국어점수 : ${studentInfo.korean}\n\n")

                        koreanTotal += studentInfo.korean
                    }
                    textViewResult.append("\n")
                    textViewResult.append("국어 총점 : ${koreanTotal}\n")
                    textViewResult.append("국어 평균 : ${koreanTotal / studentList.size}\n\n")
                }
            }
        }
    }
}


// 학생 정보를 데이터 클래스
data class StudentInfo(var name:String, var age:Int, var gender:String, var korean:Int) {
    val hobby = mutableListOf<String>()

    fun addHobby(a1:String){
        hobby.add(a1)
    }
}