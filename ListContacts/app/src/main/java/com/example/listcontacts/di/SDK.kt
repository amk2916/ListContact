package com.example.listcontacts.di

import android.content.Context
import androidx.room.Room
import com.example.listcontacts.ContactRepository
import com.example.listcontacts.ContactRepositoryImpl
import com.example.listcontacts.ContactsDatabase

class SDK(
    private val context: Context
) {

    private val db = Room.databaseBuilder(
        context,
        ContactsDatabase::class.java,
        "database_c"
    ).allowMainThreadQueries()
        .build()

    val contactRepository: ContactRepository = ContactRepositoryImpl(db.getContactDao())
}