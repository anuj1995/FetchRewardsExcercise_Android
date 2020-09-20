package edu.uga.cs.fetchrewardsandroid.hiring_api_Interface

import edu.uga.cs.fetchrewardsandroid.data_models.HiringInfo
import io.reactivex.Observable
import retrofit2.http.GET

interface HiringApi {
    @GET("hiring.json")
    fun getHiringInfo(): Observable<List<HiringInfo>>
}