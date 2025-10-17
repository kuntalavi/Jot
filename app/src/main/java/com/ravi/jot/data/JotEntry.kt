package com.ravi.jot.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jotEntry")
data class JotEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String = "",
    val content: String = "",
    val e: String = "",
    val month: String = "",
    val date: Int = 0,
    val photoURI: String?
)
