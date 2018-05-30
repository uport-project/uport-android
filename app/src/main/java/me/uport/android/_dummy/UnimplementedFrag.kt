package me.uport.android._dummy

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.uport.android.R
import me.uport.android.notifications.Notification

/**
 * A fragment representing a list of [Notification]s.
 */
class UnimplementedFrag : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_unimplemented, container, false)
    }

}