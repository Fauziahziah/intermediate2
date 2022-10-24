package com.example.mysubmission_intermediate.UI.Story.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mysubmission_intermediate.Model.UserModel
import com.example.mysubmission_intermediate.Remote.Repository
import kotlinx.coroutines.launch

class MapsViewModel(private val pref: Repository) : ViewModel() {
    val StoriesResponse get() = pref.StoriesResponse
    val toastText get() = pref.toastText

    fun getStoriesWithLocation(token: String) {
        viewModelScope.launch {
            pref.getStoriesWithLocation(token)
        }
    }

    fun loadState(): LiveData<UserModel> {
        return pref.loadState()

    }
}