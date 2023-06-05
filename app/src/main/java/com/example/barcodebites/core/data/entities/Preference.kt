package com.example.barcodebites.core.data.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class Preference(
    @PrimaryKey val preferenceName: String,
    @ColumnInfo(name = "attribute") val attribute: String
)

@Entity(primaryKeys = ["preferenceName", "attributeName"])
data class PreferenceAttributeCrossRef(
    val preferenceName: String,
    val attributeName: String
)

data class PreferenceWithAttributes(
    @Embedded val preference: Preference,
    @Relation(
        parentColumn = "preferenceName",
        entityColumn = "attributeName",
        associateBy = Junction(PreferenceAttributeCrossRef::class)
    )
    val preferenceAttributes: List<Attribute>
)

@Entity(primaryKeys = ["email", "preferenceName"])
data class UserPreferenceCrossRef(
    val email: String, //PK key user table
    val preferenceName: String //PK key preference table
)

//only query what preference user has, not was user have preference
data class UserWithPreferences(
    @Embedded val user: User,
    @Relation(
        parentColumn = "email",
        entityColumn = "preferenceName",
        associateBy = Junction(UserPreferenceCrossRef::class) //lets room know in which table to find infos
    )
    val userPreferences: List<UserWithPreferences>
)

data class UserWithPreferencesWithAttributes(
    @Embedded val user: User,
    @Relation(
        entity = User::class,
        parentColumn = "email",
        entityColumn = "preferenceName"
    )
    val userPreferencesWithAttributes: List<PreferenceWithAttributes>
)