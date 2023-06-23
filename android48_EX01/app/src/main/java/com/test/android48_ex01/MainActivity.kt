package com.test.android48_ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.test.android48_ex01.DataClass.Companion.personList
import com.test.android48_ex01.databinding.ActivityMainBinding
import com.test.android48_ex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            recyclerVIewInfo.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@MainActivity)
            }

            buttonAdd.setOnClickListener {
                val addIntent = Intent(this@MainActivity,AddActivity::class.java)
                startActivity(addIntent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        // recyclerView 갱신
        activityMainBinding.recyclerVIewInfo.adapter?.notifyDataSetChanged()
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass> () {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {

            var texViewName:TextView

            init {
                texViewName = rowBinding.textViewRow

                rowBinding.root.setOnClickListener {
                    val resultIntent = Intent(this@MainActivity,ResultActivity::class.java)
                    resultIntent.putExtra("position", adapterPosition)
                    startActivity(resultIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val rowBinding = RowBinding.inflate(layoutInflater)
            val ViewHolder = ViewHolderClass(rowBinding)

            val params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return personList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.texViewName.text = personList[position].name
        }
    }
}