package com.test.android40_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.text.Layout
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutParams
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android40_ex01.databinding.ActivityMainBinding
import com.test.android40_ex01.databinding.RowBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    var studentList = mutableListOf<StudentInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)


        // RecyclerView에 context menu 등록
        registerForContextMenu(activityMainBinding.recyclerView)

        activityMainBinding.run {
            layoutInput.visibility = View.GONE
            layoutResult.visibility = View.GONE

            editTextKorean.run {
                setOnEditorActionListener { v, actionId, event ->

                    var name = editTextName.text.toString()
                    var age = editTextAge.text.toString().toInt()
                    var korean = editTextKorean.text.toString().toInt()

                    studentList.add(StudentInfo(name,age,korean))

                    editTextName.setText("")
                    editTextAge.setText("")
                    editTextKorean.setText("")

                    editTextName.requestFocus()

                    val adapter = recyclerView.adapter as RecyclerViewAdapter
                    adapter.notifyDataSetChanged()

                    false
                }
            }

            recyclerView.run {
                adapter = RecyclerViewAdapter()
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.itemShow -> {
                activityMainBinding.layoutInput.visibility = View.VISIBLE
                activityMainBinding.layoutResult.visibility = View.GONE

                activityMainBinding.editTextName.requestFocus()
                thread {
                    SystemClock.sleep(100)
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(currentFocus, 0)
                }
            }

            R.id.itemResult -> {
                activityMainBinding.layoutInput.visibility = View.GONE
                activityMainBinding.layoutResult.visibility = View.VISIBLE

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
                currentFocus?.clearFocus()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : ViewHolder(rowBinding.root) {

            var textViewName : TextView
            var textViewAge : TextView
            var textViewKorean : TextView

            init {
                textViewName = rowBinding.textViewName
                textViewAge = rowBinding.textViewAge
                textViewKorean = rowBinding.textViewKorean

                // 항목 하나의 View에 컨텍스트 메뉴 생성 이벤트
                // rowBinding.root : 현재 번째의 항목 전체
                rowBinding.root.setOnCreateContextMenuListener { menu, v, menuInfo ->
                    menu.setHeaderTitle("${studentList[adapterPosition].name}")
                    menuInflater.inflate(R.menu.context_menu, menu)

                    menu[0].setOnMenuItemClickListener {
                        // 현재 항목 번째 삭제
                        studentList.removeAt(adapterPosition)

                        // recyclerView 갱신
                        this@RecyclerViewAdapter.notifyDataSetChanged()

                        false
                    }
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowBinding.inflate(layoutInflater)
            var viewHolderClass = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT,
            )

            rowBinding.root.layoutParams = params

            return viewHolderClass
        }

        override fun getItemCount(): Int {
            return studentList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {

            val (name,age,korean) = studentList[position]

            holder.textViewName.text = name
            holder.textViewAge.text = "${age}살"
            holder.textViewKorean.text = "${korean}점"
        }
    }
}

data class StudentInfo(var name: String, var age: Int, var korean: Int)