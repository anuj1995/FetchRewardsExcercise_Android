package edu.uga.cs.fetchrewardsandroid.KoinDependencyInjection

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import edu.uga.cs.fetchrewardsexcercise.HiringApiInterface.HiringApi
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

fun getNetworkModule(): Module {

    fun provideOkhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(Cache(File(context.cacheDir, "http"), 10 * 1024 * 1024L))
            .connectTimeout(2, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            //.addNetworkInterceptor(get())
            .build()
    }

    fun provideRetrofit(client: OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

    }

    fun provideGson(): Gson {return GsonBuilder().create()}

    fun provideHiringApi(retrofit: Retrofit): HiringApi {
        return  retrofit.create(HiringApi::class.java)
    }

    val networkModule = module(createdAtStart = true) {

        // Gson instance creation
        single {provideGson() }

        // OkHtttpClient creation
        single{ provideOkhttpClient(androidContext()) }

        // Retrofit creation
        single{ provideRetrofit(get(), get()) }

        // Restapi creation
        single { provideHiringApi(get()) }

    }
    return networkModule

}

/*auxiliary function that wraps Koin Component and gives out the desired instance*/
inline fun <reified T> getDIInstance(): T {
    return object : KoinComponent {
        val value: T by inject()
    }.value
}