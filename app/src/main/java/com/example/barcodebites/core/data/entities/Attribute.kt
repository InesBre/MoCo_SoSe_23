package com.example.barcodebites.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attributes(
    @PrimaryKey val name: String
)