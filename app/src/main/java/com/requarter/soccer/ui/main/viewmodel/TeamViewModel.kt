package com.requarter.soccer.ui.main.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.requarter.soccer.data.model.Team
import com.requarter.soccer.data.repository.MainRepository
import com.requarter.soccer.utils.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TeamViewModel(private val mainRepository: MainRepository): ViewModel() {
    private val teams = MutableLiveData<Resource<List<Team>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchUsers()
    }

    private fun fetchUsers() {
        teams.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ userList ->
                    teams.postValue(Resource.success(userList))
                }, { throwable ->
                    teams.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getUsers(): LiveData<Resource<List<Team>>> {
        return teams
    }
    private val myTeams = ArrayList<Team>()
    fun createPreviousFixture() {
        teams.value?.data?.forEach { it -> Log.e("team1",it.toString()) }
    }
}