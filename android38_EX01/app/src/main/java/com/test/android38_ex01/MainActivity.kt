package com.test.android38_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android38_ex01.databinding.ActivityMainBinding
import com.test.android38_ex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val studentList = mutableListOf<StudentInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.editTextName.visibility = View.INVISIBLE
        activityMainBinding.editTextAge.visibility = View.INVISIBLE
        activityMainBinding.editTextKorean.visibility = View.INVISIBLE
        activityMainBinding.recyclerView.visibility = View.INVISIBLE

        activityMainBinding.run {

            recyclerView.run {
                adapter = RecyclerAdapter()

                layoutManager = LinearLayoutManager(this@MainActivity)

            }

            buttonAdd.run {
                setOnClickListener{
                    editTextName.visibility = View.VISIBLE
                    editTextAge.visibility = View.VISIBLE
                    editTextKorean.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE

                    // 입력칸 모두 비우기
                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    // 포커스 생성
                    editTextName.requestFocus()

                    // 키보드 생성
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(editTextName,0)
                }
            }

            buttonShow.run {
                setOnClickListener {

                    // 키보드 제거
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

                    // 포커스 제거
                    currentFocus!!.clearFocus()

                    editTextName.visibility = View.GONE
                    editTextAge.visibility = View.GONE
                    editTextKorean.visibility = View.GONE
                    recyclerView.visibility = View.VISIBLE
                }
            }

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->
                    recyclerView.visibility = View.INVISIBLE

                    var name = editTextName.text.toString()
                    var age = editTextAge.text.toString().toInt()
                    var korean = editTextKorean.text.toString().toInt()

                    studentList.add(StudentInfo(name,age,korean))

                    val adpater = recyclerView.adapter as RecyclerAdapter
                    adpater.notifyDataSetChanged()


                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    editTextName.requestFocus()

                    true
                }
            }
        }
    }

    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {

            var textViewName: TextView
            var textViewAge: TextView
            var textViewKorean: TextView
            var buttonRowRemove: Button

            init {
                textViewName = rowBinding.textViewName
                textViewAge = rowBinding.textViewAge
                textViewKorean = rowBinding.textViewKorean
                buttonRowRemove = rowBinding.buttonRemove
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)

            val viewHolderClass = ViewHolderClass(rowBinding)

            var params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            // position 번째 객체에서 값 추출
            val (name, age, korean) = studentList[position]

            holder.textViewName.text = name
            holder.textViewAge.text = "${age}살"
            holder.textViewKorean.text = "${korean}점"

            holder.buttonRowRemove.run {
                setOnClickListener {
                    studentList.removeAt(position)

                    val adpater = activityMainBinding.recyclerView.adapter as RecyclerAdapter
                    adpater.notifyDataSetChanged()
                }
            }

        }
    }
}

data class StudentInfo(var name:String, var age:Int, var korean:Int)