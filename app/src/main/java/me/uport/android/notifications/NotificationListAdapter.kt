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

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import me.uport.android.R

/**
 * Differentiating adapter for the list of notifications.
 */
class NotificationListAdapter(private val notificationClickListener: NotificationClickListener)
    : ListAdapter<Notification, NotificationListAdapter.NotificationViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationListAdapter.NotificationViewHolder {
        val containerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_notification, parent, false)

        return NotificationListAdapter.NotificationViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: NotificationListAdapter.NotificationViewHolder, position: Int) {
        val notification = getItem(position)

        with(holder) {
            containerView.tag = notification
            containerView.setOnClickListener(::onItemClick)
            titleView.text = notification.title
            detailView.text = notification.details
        }
    }

    private fun onItemClick(v: View) {
        val item = v.tag as Notification
        notificationClickListener.onNotificationClick(item)
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Notification> = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(old: Notification, new: Notification) =
                    (old.id == new.id)

            override fun areContentsTheSame(old: Notification, new: Notification) =
                    (old == new)
        }
    }


    class NotificationViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        val titleView: TextView = containerView.findViewById(R.id.title)
        val detailView: TextView = containerView.findViewById(R.id.details)

    }

    interface NotificationClickListener {
        fun onNotificationClick(item: Notification)
    }
}

