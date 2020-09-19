package edu.uga.cs.fetchrewardsexcercise.HiringApiInterface

import edu.uga.cs.fetchrewardsexcercise.DataModels.HiringInfo
import io.reactivex.Observable
import retrofit2.http.GET

interface HiringApi {
    @GET("hiring.json")
    fun getHiringInfo(): Observable<List<HiringInfo>>
}