package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit

class PostRepositoryImpl() : PostRepository {

    private val client = OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS).build()
    private val gson = Gson()
    private val postsType = object : TypeToken<List<Post>>() {}.type
    private val postType = object : TypeToken<Post>() {}.type

    private companion object {
        const val GET = ""
        const val BASE_URL = "http://10.0.2.2:9999"
        val jsonType = "application/json".toMediaType()
    }

    override fun getAllAsync(callback: PostRepository.RepositoryCallback<List<Post>>) {
        val request = Request.Builder().url("${BASE_URL}/api/slow/posts").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    val body = response.body?.string() ?: throw RuntimeException("Body is null")
                    callback.onSuccess(gson.fromJson(body, postsType))
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }

    override fun getByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Post>) {
        val request = Request.Builder().url("${BASE_URL}/api/slow/posts/$id").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseString = response.body?.string() ?: error("Body is null")
                    callback.onSuccess(gson.fromJson(responseString, Post::class.java))
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }

    override fun likePostAsync(id: Long, callback: PostRepository.RepositoryCallback<Boolean>) {
        val request = Request.Builder().post(RequestBody.create(null, ByteArray(0)))
            .url("${BASE_URL}/api/slow/posts/$id/likes").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        callback.onSuccess(true)
                    } else {
                        callback.onError(java.lang.RuntimeException("Ошибка сервера"))
                    }
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }

    override fun unlikePostAsync(id: Long, callback: PostRepository.RepositoryCallback<Boolean>) {
        val request = Request.Builder().delete().url("${BASE_URL}/api/posts/$id/likes").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        callback.onSuccess(true)
                    } else {
                        callback.onError(java.lang.RuntimeException("Ошибка сервера"))
                    }
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }

    override fun removeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Boolean>) {
        val request = Request.Builder().delete().url("${BASE_URL}/api/slow/posts/$id").build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    if (response.isSuccessful) {
                        callback.onSuccess(true)
                    } else {
                        callback.onError(java.lang.RuntimeException("Ошибка сервера"))
                    }
                } catch (e: Exception) {
                    callback.onError(e)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }

    override fun saveAsync(post: Post, callback: PostRepository.RepositoryCallback<Post>) {
        val request = Request.Builder().url("${BASE_URL}/api/posts")
            .post(gson.toJson(post).toRequestBody(jsonType)).build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                try {
                    val body =
                        response.body?.string() ?: throw java.lang.RuntimeException("Body is null")
                    val resultPost = gson.fromJson(body, Post::class.java)
                    callback.onSuccess(resultPost)
                } catch (e: Exception) {
                    callback.onError(e)
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                callback.onError(e)
            }
        })
    }


    override fun share(id: Long) {
        TODO("Not yet implemented")
    }
}