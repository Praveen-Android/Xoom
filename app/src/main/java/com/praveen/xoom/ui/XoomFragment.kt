package com.praveen.xoom.ui

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.*
import android.widget.Toast
import com.praveen.xoom.R
import com.praveen.xoom.XoomApplication
import com.praveen.xoom.data.Response
import com.praveen.xoom.data.Status
import com.praveen.xoom.database.CountryDetails
import kotlinx.android.synthetic.main.layout_xoom_fragment.*
import javax.inject.Inject

class XoomFragment: Fragment(), OnFavoriteSelected {

    @Inject
    lateinit var mViewModelFactory: XoomViewModelFactory

    private lateinit var mViewModel:XoomViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        XoomApplication.appComponent.inject(this)
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(XoomViewModel::class.java)

        mViewModel.getResponse().observe(this, Observer<Response<List<CountryDetails>>>{
            if (it != null) this.processResponse(it.status, it.data, it.error)})

        mViewModel.fetchCountriesList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_xoom_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        country_list_view.layoutManager = LinearLayoutManager(activity)
        country_list_view.addItemDecoration(DividerItemDecoration(activity, VERTICAL))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_refresh, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.refresh -> {
                progress_view.visibility = View.VISIBLE
                country_list_view.visibility = View.INVISIBLE
                mViewModel.fetchCountriesListFromRemote()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun processResponse(status: Status, data: List<CountryDetails>?, error:String?){
        when(status) {
            Status.LOADING -> {
                progress_view.visibility = View.VISIBLE
                country_list_view.visibility = View.INVISIBLE
            }

            Status.SUCCESS -> handleSuccessResponse(data)

            Status.ERROR -> handleErrorResponse(error)
        }
    }

    // invoked when the data fetch is successful with a list of CountryDetails to display on the UI
    private fun handleSuccessResponse(data:List<CountryDetails>?) {
        progress_view.visibility = View.INVISIBLE
        country_list_view.visibility = View.VISIBLE
        country_list_view.scheduleLayoutAnimation()
        country_list_view.adapter = data?.let { XoomAdapter( this, it) }

    }

    // invoked when data fetch failed
    private fun handleErrorResponse(error: String?) {
        progress_view.visibility = View.INVISIBLE
        country_list_view.visibility = View.VISIBLE
        Toast.makeText(activity, getString(R.string.api_error_text), Toast.LENGTH_SHORT).show()
    }

    // invoked when user favorites a country
    override fun saveFavorite(countryCode: String?) {
        countryCode?.let { mViewModel.saveFavoriteSelection(it) }
    }

    // invoked when user removes a country as favorite
    override fun removeFavorite(countryCode: String?) {
        countryCode?.let { mViewModel.removeFavoriteSelection(it) }
    }
}