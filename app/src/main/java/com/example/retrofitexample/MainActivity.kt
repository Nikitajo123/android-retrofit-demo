package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val txtResult = findViewById<TextView>(R.id.txtResult)
        val btnLoad = findViewById<Button>(R.id.btnLoad)

        btnLoad.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val response = RetrofitInstance.api.getPost()

                    if (response.isSuccessful) {
                        val post = response.body()
                        txtResult.text = "ID: ${post?.id}\nTitle: ${post?.title}"
                    } else {
                        txtResult.text = "Server error: ${response.code()}"
                    }

                } catch (e: Exception) {
                    txtResult.text = "Exception occurred: ${e.localizedMessage}"
                }
            }
        }
    }
}
