package com.example.listcontacts.data.local

import androidx.room.*

@Dao
interface ContactDao {
    @Insert
    fun insert(contact: DatabaseContact) :Long

/*    @Query("Update set asd WHERE id =")
    fun updateContact(contact: DatabaseContact)*/

    @Query("SELECT * FROM contacts")
    fun selectAll(): List<DatabaseContact>

    @Query("SELECT * FROM contacts where id = :idDataBase")
    fun selectContact(idDataBase: Long): DatabaseContact

    @Query("Delete from contacts where id = :id")
    fun deleteContact(id:Long)

}