package com.praveen.xoom.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.praveen.xoom.R

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.xoom_title)
    }

    /**
     * helper method to add a fragment to an activity so as to avoid code repetition
     */
    fun addFragmentToActivity(fragmentManager: FragmentManager,
                              fragment: Fragment,
                              frameId: Int,
                              tag: String) {

        val transaction = fragmentManager.beginTransaction()
        transaction.replace(frameId, fragment, tag)
        transaction.commit()
    }
}