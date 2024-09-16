package com.github.ericknathan.geniusxp.models

data class Event(
    val id: Long,
    val name: String,
    val description: String,
    val eventType: String,
    val imageUrl: String,
)
