package com.praveen.xoom.ui

import android.os.Bundle
import com.praveen.xoom.R

class XoomActivity : BaseActivity() {

    companion object {
        const val XOOM_FRAGMENT_TAG = "XOOM_FRAG_TAG"
    }

    private var mXoomFragment: XoomFragment? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_xoom)

        mXoomFragment = supportFragmentManager.findFragmentByTag(XOOM_FRAGMENT_TAG) as? XoomFragment
        mXoomFragment?:let { mXoomFragment = XoomFragment() }

        showXoomFragment()
    }

    private fun showXoomFragment() {
        mXoomFragment?.let {
            addFragmentToActivity(fragmentManager = supportFragmentManager, fragment = it,
                    frameId = R.id.activity_root, tag = XOOM_FRAGMENT_TAG)
        }
    }
}
