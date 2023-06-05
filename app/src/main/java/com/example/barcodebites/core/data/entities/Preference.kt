package com.example.barcodebites.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Preferences(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "attribute") val attribute: String
)

@Entity
data class FoodAttribute(
    @PrimaryKey(autoGenerate = true)
    val attributeId: Int,
    val attributeName: String,
)

//darstellung M:N Beziehung
@Entity(primaryKeys = ["preferenceId", "attributeId"])
data class PreferenceAttributeCrossRef(
    val preferenceId: Int,
    val attributeId: Int
)
data class PreferenceWithAttributes(
    @Embedded val preference: FoodPreference,
    @Relation(
        parentColumn = "preferenceId",
        entityColumn = "attributeId",
        associateBy = Junction(PreferenceAttributeCrossRef::class)
    )
    val preferenceFoodAttributes: List<FoodAttribute>
)