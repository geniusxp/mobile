package com.github.ericknathan.geniusxp.models

data class Lecture(
    val id: Long,
    val name: String,
    val description: String,
    val date: String,
    val speaker: Speaker,
)