package me.uport.android.notifications

/**
 * represents a notification item, like a transaction request or a pending attestation
 */
data class Notification(

        val id: String,

        val title: String = "",

        val details: String = ""

) {
    companion object {
        val blank = Notification("")
    }
}