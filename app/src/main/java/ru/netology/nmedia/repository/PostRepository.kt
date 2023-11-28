package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun getAllAsync(callback: RepositoryCallback<List<Post>>)
    fun getByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun likePostAsync(id: Long, callback: RepositoryCallback<Boolean>)
    fun unlikePostAsync(id: Long, callback: RepositoryCallback<Boolean>)
    fun removeByIdAsync(id: Long, callback: RepositoryCallback<Boolean>)
    fun saveAsync(post: Post, callback: RepositoryCallback<Post>)
    fun share(id: Long)

    interface RepositoryCallback<T> {
        fun onSuccess(result: T)
        fun onError(e: Exception)
    }
}
