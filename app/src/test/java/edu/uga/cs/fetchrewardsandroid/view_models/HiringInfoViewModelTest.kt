package edu.uga.cs.fetchrewardsandroid.view_models

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.whenever
import edu.uga.cs.fetchrewardsandroid.data_models.HiringInfo
import edu.uga.cs.fetchrewardsandroid.hiring_api_Interface.HiringApi
import edu.uga.cs.fetchrewardsandroid.koin_dependency_injection.getNetworkModule
import edu.uga.cs.fetchrewardsexcercise.RxImmediateSchedulerRule
import io.reactivex.Observable
import org.junit.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class HiringInfoViewModelTest: KoinTest {

    @Rule
    @JvmField
    var testSchedulerRule = RxImmediateSchedulerRule()

    @Rule
    @JvmField
    val ruleForLivaData = InstantTaskExecutorRule()

    @Mock
    lateinit var mockHiringApi: HiringApi

    @Mock
    private lateinit var context: Application

    private lateinit var myViewModel:HiringInfoViewModel
    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        startKoin { androidContext(context) ;modules(getNetworkModule()) }
        myViewModel = HiringInfoViewModel(mockHiringApi)
    }

    @After
    fun tearDown() {

    }



    @Test
    fun `Given DataRepository returns data, when fetchData() is called, then update live data`() {

        //Setting how up the mock behaves
        val x = listOf(HiringInfo(684, 1,  "Item 684"))
        whenever(mockHiringApi.getHiringInfo()).thenReturn(Observable.just(x))

        //Fire the test method

        myViewModel.fetchData()

        //Check that our live data is updated
        Assert.assertEquals(x.groupBy { it.listId }, myViewModel.getHiringInfoData().value)
        stopKoin()
    }

    @Test
    fun `Given DataRepository returns data, when fetchData() is called, then live data doesn't update`(){

        val x = listOf(HiringInfo(684, 1,  "Item 684"))
        whenever(mockHiringApi.getHiringInfo()).thenReturn(Observable.error(Throwable()))

        // write initial value to live data object
        myViewModel.getHiringInfoData().value = x.groupBy { it.listId }

        // fire the event
        myViewModel.fetchData()

        // check if the live data is updated or not
        Assert.assertEquals(x.groupBy { it.listId }, myViewModel.getHiringInfoData().value)
        stopKoin()

    }

    @Test
    fun `Check whether live data objects receive values without null or blank values`(){

        myViewModel = HiringInfoViewModel()

        myViewModel.fetchData()

        myViewModel.getHiringInfoData().value?.values?.forEach {
            it.forEach {
                if(it.name.isNullOrBlank()){
                    assert(false)
                }
            }
        }
        stopKoin()

    }

    @Test
    fun `Test weather the values are sorted by the name in individual lists`(){

        myViewModel = HiringInfoViewModel()

        myViewModel.fetchData()



        myViewModel.getHiringInfoData().value?.values?.forEach {
            for(i in 0 until it.size - 1){
                if(AlphanumComparator().compare(it[i],it[i+1]) > 1){
                    assert(false)
                }
            }
        }

        stopKoin()

    }
}