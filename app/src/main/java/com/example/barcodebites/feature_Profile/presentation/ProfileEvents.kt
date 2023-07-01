package com.example.barcodebites.feature_Profile.presentation

sealed class ProfileEvents {
    data class Change(
        val isDelete: Boolean,
        val prefName: String
    ): ProfileEvents()
}