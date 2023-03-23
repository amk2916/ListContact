package com.example.listcontacts.domain

data class Contact(
    val id: Long = -1,
    val name: String,
    val secondName: String,
    val numPhone: String
)
