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

