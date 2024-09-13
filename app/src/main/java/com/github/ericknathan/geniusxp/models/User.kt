package com.github.ericknathan.geniusxp.models

data class User(
    val id: Int,
    val name: String,
    val email: String,
    val avatarUrl: String?,
    val description: String?,
    val interests: String?
)
