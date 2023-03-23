package com.example.listcontacts

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.listcontacts.data.local.ContactDao
import com.example.listcontacts.data.local.DatabaseContact

@Database(
    entities = [
        DatabaseContact::class
    ],
    version = 2
)
abstract class ContactsDatabase : RoomDatabase() {
    abstract fun getContactDao(): ContactDao
}