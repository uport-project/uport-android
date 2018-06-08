package me.uport.android.verifications


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import me.uport.android.R

/**
 * TODO: add a recyclerview with Verifications that navigate to VerificationDetails
 */
class VerificationsFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verifications, container, false)
    }


}
