package com.requarter.soccer.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.requarter.soccer.data.model.Fixture
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

    private fun prepareTeamList(): List<Team> {
        val teamList: List<Team>? = teams.value?.data?.shuffled()
        if (teams.value?.data!!.size%2 == 1)
        {
            teamList?.drop(1)
        }
        return teamList!!
    }

    fun createPreviousFixture(): ArrayList<Fixture> {
        val teamList: List<Team>? = prepareTeamList()
        val fixtureList: ArrayList<Fixture> = arrayListOf()
        for (i in teamList?.indices!! step 2)
        {
            val fixtureObject = Fixture(teamList[i]?.name.toString(),teamList[i+1]?.name.toString(),1,3)
            fixtureList.add(fixtureObject)
        }
        return fixtureList
    }

    fun createNextFixture(): ArrayList<Fixture> {
        val teamList: List<Team>? = prepareTeamList()
        val fixtureList: ArrayList<Fixture> = arrayListOf()
        for (i in teamList?.indices!! step 2)
        {
            val fixtureObject = Fixture(teamList[i]?.name.toString(),teamList[i+1]?.name.toString(),null,null)
            fixtureList.add(fixtureObject)
        }
        return fixtureList
    }
}