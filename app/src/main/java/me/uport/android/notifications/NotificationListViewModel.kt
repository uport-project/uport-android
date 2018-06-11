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
