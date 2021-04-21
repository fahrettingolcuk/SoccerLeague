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
import kotlin.random.Random

class TeamViewModel(private val mainRepository: MainRepository): ViewModel() {
    private val teams = MutableLiveData<Resource<List<Team>>>()
    private val compositeDisposable = CompositeDisposable()

    init {
        fetchTeams()
    }

    private fun fetchTeams() {
        teams.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getTeams()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ teamList ->
                    teams.postValue(Resource.success(teamList))
                }, { throwable ->
                    teams.postValue(Resource.error("Something Went Wrong", null))
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun getTeams(): LiveData<Resource<List<Team>>> {
        return teams
    }

    private fun prepareTeamList(): List<Team> {
        var teamList: List<Team>? = teams.value?.data?.shuffled()
        if (teams.value?.data!!.size % 2 == 1)
        {
            teamList = teamList?.drop(1)
        }
        return teamList!!
    }

    fun createPreviousFixture(): ArrayList<Fixture> {
        val teamList: List<Team>? = prepareTeamList()
        val fixtureList: ArrayList<Fixture> = arrayListOf()
        for (i in teamList?.indices!! step 2)
        {
            val fixtureObject = Fixture(teamList[i]?.name.toString(),teamList[i+1]?.name.toString(),
                Random.nextInt(0,10),Random.nextInt(0,10))
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