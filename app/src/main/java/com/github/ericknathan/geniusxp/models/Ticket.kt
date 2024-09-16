package com.github.ericknathan.geniusxp.models

import java.io.Serializable

data class Ticket(
    val id: Long,
    val dateOfUse: String?,
    val issuedDate: String,
    val ticketNumber: String,
    val payment: Payment,
    val coupon: String?,
    val ticketType: TicketType,
    val event: Event,
) : Serializable
