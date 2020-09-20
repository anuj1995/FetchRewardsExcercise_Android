package edu.uga.cs.fetchrewardsandroid.view_models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import edu.uga.cs.fetchrewardsandroid.data_models.HiringInfo
import edu.uga.cs.fetchrewardsandroid.hiring_api_Interface.HiringApi
import edu.uga.cs.fetchrewardsandroid.koin_dependency_injection.getDIInstance
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import org.jetbrains.annotations.TestOnly

class HiringInfoViewModel():ViewModel() {

    private val hiringInfoData: MutableLiveData<Map<Int, List<HiringInfo>>> = MutableLiveData()
    private lateinit var hiringApi: HiringApi

    @TestOnly
    constructor(hiringApi: HiringApi) : this() {
        this.hiringApi = hiringApi
    }

    fun fetchData(){
        if(!this::hiringApi.isInitialized) hiringApi = getDIInstance()

        hiringApi.getHiringInfo()
            .distinctUntilChanged()
            .toFlowable(BackpressureStrategy.LATEST)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                // filter out the null or blank
                it.filter { !(  it.name.isNullOrBlank() ) }
                    // group by listId and sort the map
                    .groupBy { it.listId }.toSortedMap()
                    // sort each list by it name lexicographically
                    .map {
                        val x = it.value.sortedWith(AlphanumComparator())
                        Pair(it.key, x)
                    }.associateBy ({it.first},{it.second})
            }.subscribeBy(
                onError = { Log.d("fetchData ViewModel","on Error"); it.printStackTrace()} ,
                onNext = { hiringInfoData.value = it ; println(it)},
                onComplete = {Log.d("fetchData ViewModel","on complete") })
            .addTo(CompositeDisposable())
    }

    fun getHiringInfoData() = this.hiringInfoData
}