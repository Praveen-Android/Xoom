package com.praveen.xoom.ui

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.praveen.xoom.data.Response
import com.praveen.xoom.database.CountryDetails
import com.praveen.xoom.repo.XoomRepository
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * XoomViewModel class to delegate the requests to repository and send the getResponse to the view using LiveData
 */
open class XoomViewModel @Inject constructor(private val mXoomRepository: XoomRepository): ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    //Live Date object and function for communication between view and viewmodel
    private val response: MutableLiveData<Response<List<CountryDetails>>> = MutableLiveData()

    fun getResponse(): MutableLiveData<Response<List<CountryDetails>>> {
        return response
    }

    /**
     * fetch the list of all xoom countries. Data could be coming from DB if there is any else an api call would be made
     */
    fun fetchCountriesList() {
        compositeDisposable.add(mXoomRepository.fetchCountryCatalog()
                .doOnSubscribe { response.postValue(Response.loading()) }
                .subscribe(
                        { countryDetailsList: List<CountryDetails>? -> run {
                            Log.d(javaClass.simpleName, "fetch country list successful")
                            response.postValue(Response.success(countryDetailsList!!))
                        }
                        },
                        { throwable: Throwable? -> run {
                            Log.d(javaClass.simpleName, "fetch country list failed")
                            response.postValue(Response.error(throwable))
                        }
                        }
                )
        )
    }

    /**
     * Fetch the list of all xoom countries by remote api call. This is called when refresh is clicked on the UI
     */
    fun fetchCountriesListFromRemote() {
        compositeDisposable.add(mXoomRepository.fetchCountryCatalogFromApi()
                .doOnSubscribe { response.postValue(Response.loading()) }
                .subscribe(
                        { countryDetailsList: List<CountryDetails>? -> run {
                            Log.d(javaClass.simpleName, "fetch country list successful")
                            response.postValue(Response.success(countryDetailsList!!))
                        }
                        },
                        { throwable: Throwable? -> run {
                            Log.d(javaClass.simpleName, "fetch country list failed")
                            response.postValue(Response.error(throwable))
                        }
                        }
                )
        )
    }


    fun saveFavoriteSelection(code: String) {
        mXoomRepository.saveFavorite(code)
    }

    /**
     * Save favorite selection to Shared Preferences
     */
    fun removeFavoriteSelection(code: String) {
        mXoomRepository.deleteFavorite(code)
    }

    //clean up the subscriptions when view model is destroyed
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}