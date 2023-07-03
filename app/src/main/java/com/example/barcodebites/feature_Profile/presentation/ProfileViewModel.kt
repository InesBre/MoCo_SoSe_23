package com.example.barcodebites.feature_Profile.presentation


import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodebites.core.data.entities.Preference
import com.example.barcodebites.feature_History.presentation.HistoryState
import com.example.barcodebites.feature_Profile.data.ProfileRepositoryImplementation
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: ProfileRepositoryImplementation) :
    ViewModel() {
    private val _state = mutableStateOf(ProfileState())
    val state: State<ProfileState> = _state

    fun getPreferencesList() = viewModelScope.launch {
        val user = repository.getUser()
        if(user === null){
            _state.value = state.value.copy(EMPTY = true, PREFERENCES = listOf())
        }
        else{
            val userPrefs = repository.getUserPreferences(repository.getUser()!!)
            val list = repository.preferencesList.entries.map { it ->
                val name = it.key
                val triple = it.value
                val checked = userPrefs.map { x -> x.preferenceName }.contains(name)
                name to Triple(triple.first, triple.second, mutableStateOf(checked))
            }
            _state.value = state.value.copy(EMPTY = false, PREFERENCES = list)
        }
    }

    fun onEvent(event: ProfileEvents) {
        when (event) {
            is ProfileEvents.Change -> {
                viewModelScope.launch {
                    val email = repository.getUser()!!
                    if (event.isDelete) {
                        repository.deletePreference(email, event.prefName)
                    } else {
                        repository.insertPreference(Preference(
                            preferenceName = event.prefName,
                            userEmail = email
                        ))
                    }
                }
            }
        }
    }
}