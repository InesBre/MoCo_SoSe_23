package com.example.barcodebites.core.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Attribute(
    @PrimaryKey val attributeName: String
)