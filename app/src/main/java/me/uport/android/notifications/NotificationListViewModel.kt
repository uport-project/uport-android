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

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class NotificationListViewModel : ViewModel() {

    val notifications = MutableLiveData<MutableList<Notification>>().apply {
        value = emptyList<Notification>().toMutableList()
    }

    init {
        for (idx in 0..15) {
            addNotification(Notification("dummy serial ${idx * 2}", title = "Lorem ipsum Tx request  ${idx * 2}", details = "Lorem ipsum uPort demo"))
            addNotification(Notification("dummy serial ${idx * 2 + 1}", title = "Lorem ipsum Attestation received ${idx * 2 + 1}"))
        }
    }

    fun addNotification(notification: Notification) {
        notifications.value?.add(notification)
        //refresh observers
        notifications.value = notifications.value
    }

}
