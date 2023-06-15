package com.test.android37_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android37_ex01.databinding.ActivityMainBinding
import com.test.android37_ex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val studentList = mutableListOf<StudentInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            recyclerViewResult.run {
                adapter = RecyclerAdapter()

                layoutManager  = LinearLayoutManager(this@MainActivity)
            }

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->

                    val name = editTextName.text.toString()
                    val age = editTextAge.text.toString().toInt()
                    val korean = editTextKorean.text.toString().toInt()

                    studentList.add(StudentInfo(name,age,korean))

//                    val adpater = recyclerViewResult.adapter as RecyclerAdapter
//                    adpater.notifyDataSetChanged()

                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    false
                }
            }
        }

    }

    inner class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {
            // View의 주소값을 담을 변수
            var textViewName:TextView
            var textViewAge:TextView
            var textViewKorean: TextView

            init{
                textViewName = rowBinding.textViewName
                textViewAge = rowBinding.textViewAge
                textViewKorean = rowBinding.textViewKorean
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
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
        }
    }

    data class StudentInfo(var name:String, var age:Int, var korean:Int)
}