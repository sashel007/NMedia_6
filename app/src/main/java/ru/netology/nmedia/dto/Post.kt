package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val authorAvatar: String,
    val content: String,
    val published: Int,
    val likedByMe: Boolean,
    val likes: Int = 0
//    var sharings: Int,
//    var video: String? = ""
) 
