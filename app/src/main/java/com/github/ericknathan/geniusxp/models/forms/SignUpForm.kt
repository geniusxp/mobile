package com.github.ericknathan.geniusxp.models.forms

data class SignUpForm(
    val name: String,
    val email: String,
    val password: String,
    val cpf: String,
    val birthDate: String
)
