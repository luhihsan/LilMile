package com.example.coba_lilmile

import java.util.UUID

data class DataPengguna(
    val id: String = UUID.randomUUID().toString(),
    val username: String,
    val password: String,
    val email: String
)
