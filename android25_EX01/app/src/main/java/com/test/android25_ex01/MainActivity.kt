package com.test.android25_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import com.test.android25_ex01.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val movieList = mutableListOf<MovieInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {

            editTextSubject.requestFocus()

            thread {
                SystemClock.sleep(500)
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(currentFocus,0)
            }
            seekBarFair.run{
                // 사용자가 seekbar를 움직이는 경우
                setOnSeekBarChangeListener(object : OnSeekBarChangeListener{
                    override fun onProgressChanged(
                        seekBar: SeekBar?,
                        progress: Int,
                        fromUser: Boolean
                    ) {
                        // 현재 seekbar의 값으로 실제 가격을 계산하여 출력
                        textViewFair.text = "요금 : ${progress * 100 + 5000}원"
                    }

                    override fun onStartTrackingTouch(seekBar: SeekBar?) {
                        // TODO("Not yet implemented")
                    }

                    override fun onStopTrackingTouch(seekBar: SeekBar?) {
                        // TODO("Not yet implemented")
                    }

                } )
            }

            editTextDirectorName.run {
                setOnEditorActionListener { v, actionId, event ->

                    val subject = editTextSubject.text.toString()
                    val fair = seekBarFair.progress * 100 + 5000
                    val grade = when(ChipGroupGrade.checkedChipId){
                        R.id.chipGrade1 -> "전체"
                        R.id.chipGrade2 -> "12세"
                        R.id.chipGrade3 -> "15세"
                        R.id.chipGrade4 -> "성인"
                        else -> ""
                    }
                    var rate = ratingBarStar.rating
                    var directorname = editTextDirectorName.text.toString()

                    val movieInfo = MovieInfo(subject,fair,grade,rate,directorname)
                    movieList.add(movieInfo)

                    editTextSubject.setText("")
                    seekBarFair.progress = 0
                    ChipGroupGrade.check(R.id.chipGrade1)
                    ratingBarStar.rating = 0.0f
                    editTextDirectorName.setText("")

                    editTextSubject.requestFocus()

                    false
                }
            }

            buttonShowResult.run {
                setOnClickListener{
                    textViewShowResult.text = ""

                    for (movie in movieList) {
                        textViewShowResult.run {
                            append("영화 제목 : ${movie.subject}\n")
                            append("요금 : ${movie.fair}원\n")
                            append("등급 : ${movie.grade}\n")
                            append("별점 : ${movie.rating}\n")
                            append("감독 이름 : ${movie.directorName}\n\n")
                        }
                    }

                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
                    currentFocus!!.clearFocus()
                }
            }
        }
    }
}

data class MovieInfo(var subject:String, var fair:Int, var grade:String, var rating:Float, var directorName:String)