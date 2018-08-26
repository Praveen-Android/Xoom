package com.praveen.xoom.api

import com.praveen.xoom.data.XoomResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface XoomService {

    /**
     * get catalog of all countries from Xoom mobile REST API
     */
    @GET("/catalog/v2/countries")
    @Headers("Content-type: application/json")
    fun requestXoomCountryCatalog(): Observable<XoomResponse>

    /**
     * get catalog of countries given the page size from Xoom mobile REST API
     */
    @GET("/catalog/v2/countries")
    @Headers("Content-type: application/json")
    fun requestXoomCountryCatalog(
            @Query("page_size") pageSize: Int
    ): Observable<XoomResponse>


    /**
     * get Countries catalog from Xoom mobile REST API give page number and page size
     */
    @GET("/catalog/v2/countries")
    @Headers("Content-type: application/json")
    fun requestXoomCountryCatalog(
            @Query("page") page: Int,
            @Query("page_size") pageSize: Int
    ): Observable<XoomResponse>
}