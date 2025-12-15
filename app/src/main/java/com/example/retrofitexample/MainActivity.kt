package com.example.retrofitexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var txtResult: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnLoadSingle: Button
    private lateinit var btnLoadAll: Button
    private lateinit var btnCreate: Button
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var btnError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        setupClickListeners()
    }

    private fun initViews() {
        txtResult = findViewById(R.id.txtResult)
        progressBar = findViewById(R.id.progressBar)
        btnLoadSingle = findViewById(R.id.btnLoadSingle)
        btnLoadAll = findViewById(R.id.btnLoadAll)
        btnCreate = findViewById(R.id.btnCreate)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
        btnError = findViewById(R.id.btnError)
    }

    private fun setupClickListeners() {
        btnLoadSingle.setOnClickListener { loadSinglePost() }
        btnLoadAll.setOnClickListener { loadAllPosts() }
        btnCreate.setOnClickListener { createPost() }
        btnUpdate.setOnClickListener { updatePost() }
        btnDelete.setOnClickListener { deletePost() }
        btnError.setOnClickListener { demonstrateError() }
    }

    private fun loadSinglePost() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val response = RetrofitInstance.api.getPostById(1)
                handleResponse(response) { post ->
                    txtResult.text = buildString {
                        append("=== Single Post ===\n\n")
                        append("ID: ${post.id}\n")
                        append("User ID: ${post.userId}\n")
                        append("Title: ${post.title}\n\n")
                        append("Body:\n${post.body}")
                    }
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun loadAllPosts() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val response = RetrofitInstance.api.getAllPosts()
                handleResponse(response) { posts ->
                    txtResult.text = buildString {
                        append("=== All Posts (${posts.size}) ===\n\n")
                        posts.take(5).forEach { post ->
                            append("${post.id}. ${post.title}\n\n")
                        }
                        if (posts.size > 5) {
                            append("... and ${posts.size - 5} more posts")
                        }
                    }
                    showToast("Loaded ${posts.size} posts successfully")
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun createPost() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val newPost = Post(
                    userId = 1,
                    id = 0,
                    title = "My New Post from Android",
                    body = "This is a test post created using Retrofit POST request."
                )

                val response = RetrofitInstance.api.createPost(newPost)
                handleResponse(response) { createdPost ->
                    txtResult.text = buildString {
                        append("=== POST Created ===\n\n")
                        append("ID: ${createdPost.id}\n")
                        append("Title: ${createdPost.title}\n\n")
                        append("Body:\n${createdPost.body}")
                    }
                    showToast("Post created with ID: ${createdPost.id}")
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun updatePost() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val updatedPost = Post(
                    userId = 1,
                    id = 1,
                    title = "Updated Post Title",
                    body = "This post has been updated using PUT request."
                )

                val response = RetrofitInstance.api.updatePost(1, updatedPost)
                handleResponse(response) { post ->
                    txtResult.text = buildString {
                        append("=== PUT Updated ===\n\n")
                        append("ID: ${post.id}\n")
                        append("Title: ${post.title}\n\n")
                        append("Body:\n${post.body}")
                    }
                    showToast("Post updated successfully")
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun deletePost() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val response = RetrofitInstance.api.deletePost(1)
                if (response.isSuccessful) {
                    txtResult.text = buildString {
                        append("=== DELETE Successful ===\n\n")
                        append("Post with ID 1 has been deleted.\n")
                        append("Response code: ${response.code()}")
                    }
                    showToast("Post deleted successfully")
                } else {
                    txtResult.text = "Delete failed: ${response.code()} - ${response.message()}"
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun demonstrateError() {
        lifecycleScope.launch {
            showLoading(true)
            try {
                val response = RetrofitInstance.api.getPostById(99999)
                handleResponse(response) { post ->
                    txtResult.text = "Post: ${post.title}"
                }
            } catch (e: IOException) {
                handleNetworkError(e)
            } catch (e: Exception) {
                handleGeneralError(e)
            } finally {
                showLoading(false)
            }
        }
    }

    private fun <T> handleResponse(response: Response<T>, onSuccess: (T) -> Unit) {
        when {
            response.isSuccessful && response.body() != null -> {
                onSuccess(response.body()!!)
            }
            response.code() == 404 -> {
                txtResult.text = buildString {
                    append("=== Error 404 ===\n\n")
                    append("Resource not found.\n")
                    append("The requested data does not exist on the server.")
                }
            }
            response.code() >= 500 -> {
                txtResult.text = buildString {
                    append("=== Server Error ${response.code()} ===\n\n")
                    append("The server encountered an error.\n")
                    append("Please try again later.")
                }
            }
            else -> {
                txtResult.text = buildString {
                    append("=== Error ${response.code()} ===\n\n")
                    append("Message: ${response.message()}\n")
                    append("Something went wrong with the request.")
                }
            }
        }
    }

    private fun handleNetworkError(e: IOException) {
        txtResult.text = buildString {
            append("=== Network Error ===\n\n")
            append("Unable to connect to server.\n\n")
            append("Possible causes:\n")
            append("• No internet connection\n")
            append("• Server is down\n")
            append("• Request timeout\n\n")
            append("Error: ${e.localizedMessage}")
        }
        showToast("Network error occurred")
    }

    private fun handleGeneralError(e: Exception) {
        txtResult.text = buildString {
            append("=== Exception Occurred ===\n\n")
            append("Type: ${e.javaClass.simpleName}\n\n")
            append("Message:\n${e.localizedMessage}")
        }
        showToast("An error occurred")
    }

    private fun showLoading(isLoading: Boolean) {
        progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        btnLoadSingle.isEnabled = !isLoading
        btnLoadAll.isEnabled = !isLoading
        btnCreate.isEnabled = !isLoading
        btnUpdate.isEnabled = !isLoading
        btnDelete.isEnabled = !isLoading
        btnError.isEnabled = !isLoading
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}