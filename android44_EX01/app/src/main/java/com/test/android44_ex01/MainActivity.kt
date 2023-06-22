package com.test.android44_ex01

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.test.android44_ex01.DataClass.Companion.fruitList
import com.test.android44_ex01.databinding.ActivityMainBinding
import com.test.android44_ex01.databinding.RowBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            recyclerView.run {
                adapter = RecyclerViewAdapter()

                layoutManager = LinearLayoutManager(this@MainActivity)
            }
        }
    }

    // 다른 activity에서 입력 후 돌아온 경우 실행
    override fun onResume() {
        super.onResume()
        // recyclerView 갱신
        val adapter = activityMainBinding.recyclerView.adapter as RecyclerViewAdapter
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.main_menu,menu)

        return super.onCreateOptionsMenu(menu)
    }


    // option menu 클릭했을 때 동작하는 함수
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.itemAdd -> {
                val addIntent = Intent(this@MainActivity,AddActivity::class.java)
                startActivity(addIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // recyclerView
    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolderClass>() {
        inner class ViewHolderClass(rowBinding: RowBinding) : RecyclerView.ViewHolder(rowBinding.root) {

            var textViewRow : TextView

            init {
                textViewRow = rowBinding.textViewRow

                // 1개의 항목 클릭했을 때 동작하는 함수
                rowBinding.root.setOnClickListener {
                    val fruitIntent = Intent(this@MainActivity,FruitActivity::class.java)
                    // 사용자가 터치한 항목의 순서값 전달
                    fruitIntent.putExtra("position",adapterPosition)
                    Log.d("lion","$adapterPosition")
                    startActivity(fruitIntent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            var rowBinding = RowBinding.inflate(layoutInflater)
            var ViewHolder = ViewHolderClass(rowBinding)

            var params = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT
            )

            rowBinding.root.layoutParams = params

            return ViewHolder
        }

        override fun getItemCount(): Int {
            return fruitList.size
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.textViewRow.text = fruitList[position].name
        }
    }
}

