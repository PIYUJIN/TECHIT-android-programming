package com.test.httpjson_ex01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.test.httpjson_ex01.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    val serverAddress = "https://a.4cdn.org/boards.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        activityMainBinding.run {
            textView.visibility = View.INVISIBLE
            textViewData.visibility = View.INVISIBLE
            divider.visibility = View.INVISIBLE

            button.setOnClickListener {
                textView.visibility = View.VISIBLE
                textViewData.visibility = View.VISIBLE
                divider.visibility = View.VISIBLE

                thread {
                    val url = URL(serverAddress)
                    val httpURLConnection = url.openConnection() as HttpURLConnection

                    val inputStreamReader = InputStreamReader(httpURLConnection.inputStream, "UTF-8")
                    val bufferedReader = BufferedReader(inputStreamReader)

                    var str:String? = null
                    val stringBuffer = StringBuffer()

                    do {
                        str = bufferedReader.readLine()
                        if(str != null) {
                            stringBuffer.append(str)
                        }
                    } while(str != null)

                    val data = stringBuffer.toString()

                    runOnUiThread{
                        textViewData.text = ""
                    }

                    val root = JSONObject(data)

                    val boardsArray = root.getJSONArray("boards")

                    for(idx in 0 until boardsArray.length()) {
                        val boardsObject = boardsArray.getJSONObject(idx)
                        val board = boardsObject.getString("board")
                        val title = boardsObject.getString("title")
                        val pages = boardsObject.getInt("pages")
                        val image_limit = boardsObject.getInt("image_limit")

                        val cooldowns = boardsObject.getJSONObject("cooldowns")
                        val threads = cooldowns.getInt("threads")
                        val replies = cooldowns.getInt("replies")
                        val images = cooldowns.getInt("images")

                        runOnUiThread {
                            textViewData.append("board : $board\n")
                            textViewData.append("title : $title\n")
                            textViewData.append("pages : $pages\n")
                            textViewData.append("image_limit : $image_limit\n")
                            textViewData.append("[cooldowns]\n")
                            textViewData.append("threads : $threads\n")
                            textViewData.append("replies : $replies\n")
                            textViewData.append("images : $images\n\n")
                        }
                    }
                }
            }
        }
    }
}