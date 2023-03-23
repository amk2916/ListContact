package com.example.listcontacts.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.util.TableInfo

@Entity(
    tableName = "contacts",
//    indices = [
//        Index(
//            value = ["num_position"],
//            unique = true
//        )
//    ]
)
data class DatabaseContact(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "second_name") val secondName: String,
    @ColumnInfo(name = "num_phone") val nPhone: String,
   // @ColumnInfo(name = "num_position", index = true) val numPosition: Int
)