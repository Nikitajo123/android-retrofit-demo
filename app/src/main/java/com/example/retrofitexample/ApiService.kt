package com.example.retrofitexample

import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("posts/{id}")
    suspend fun getPostById(@Path("id") id: Int): Response<Post>

    @GET("posts")
    suspend fun getAllPosts(): Response<List<Post>>

    @GET("posts")
    suspend fun getPostsByUser(@Query("userId") userId: Int): Response<List<Post>>

    @POST("posts")
    suspend fun createPost(@Body post: Post): Response<Post>

    @PUT("posts/{id}")
    suspend fun updatePost(
        @Path("id") id: Int,
        @Body post: Post
    ): Response<Post>

    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int): Response<Unit>
}

