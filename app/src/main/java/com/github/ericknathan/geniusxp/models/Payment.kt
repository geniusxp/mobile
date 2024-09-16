package com.github.ericknathan.geniusxp.models

data class Payment(
    val id: Long,
    val paymentMethod: String,
    val status: String,
    val totalPrice: Long,
    val dueDate: String,
    val paymentDate: String?,
)