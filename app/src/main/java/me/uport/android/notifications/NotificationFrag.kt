/*
 * Copyright (c) 2018. uPort
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.uport.android.notifications

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_notification_list.*
import me.uport.android.R
import me.uport.android.notifications.NotificationListAdapter.NotificationClickListener

/**
 * A fragment representing a list of [Notification]s.
 */
class NotificationFrag : Fragment(), NotificationClickListener {

    private lateinit var viewModel: NotificationListViewModel
    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notification_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationListViewModel::class.java)

        val adapter = NotificationListAdapter(this)
        notificationList.adapter = adapter

        viewModel.notifications.observe(this, Observer { notifications ->
            //submitList requires a new list so ensure a clone is created every time
            adapter.submitList(notifications?.toMutableList())
        })
    }

    override fun onNotificationClick(item: Notification) {
        val params = Bundle().apply {
            putString(NOTIFICATION_ID, item.id)
        }
        navController.navigate(R.id.action_notification_list_to_notification_details, params)
    }

    companion object {
        private const val NOTIFICATION_ID: String = "notification_id"
    }

}
