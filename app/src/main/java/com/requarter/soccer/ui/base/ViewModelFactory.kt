package com.requarter.soccer.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.requarter.soccer.data.api.ApiHelper
import com.requarter.soccer.data.repository.MainRepository
import com.requarter.soccer.ui.main.viewmodel.TeamViewModel

class ViewModelFactory(private val apiHelper: ApiHelper) : ViewModelProvider.Factory
{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TeamViewModel::class.java)) {
            return TeamViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}