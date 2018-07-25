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

package me.uport.android.onboarding

import android.content.SharedPreferences
import android.support.annotation.VisibleForTesting
import me.uport.sdk.Uport
import org.koin.standalone.KoinComponent
import java.lang.reflect.Modifier.PRIVATE
import org.koin.standalone.inject

class Onboarding(private val uportSDK: Uport = Uport) : KoinComponent {

    private val prefs: SharedPreferences by inject(ONBOARDING_PREFS)

    fun getState(): State {
        var state = State.BLANK
        do {
            if (hasAcceptedTOS())
                state = State.ACCEPTED_TOS
            else break

            if (hasDefaultAccount())
                state = State.DEFAULT_ACCOUNT_EXISTS
            else break

        } while (false)

        return state
    }

    //TODO: return the true value
    fun hasAcceptedTOS(): Boolean = true //prefs.getBoolean(HAS_ACCEPTED_TOS, false)

    fun markTosAccepted(accepted: Boolean = true) {
        prefs.edit().putBoolean(HAS_ACCEPTED_TOS, accepted).apply()
    }

    fun hasDefaultAccount(): Boolean = (uportSDK.defaultAccount != null)

    fun canShowDashboard(): Boolean {
        return getState().ordinal >= READY_TO_USE.ordinal
    }

    fun clearUser() {
        markTosAccepted(false)
//        uportSDK.deleteAccount() //not implemented yet
    }

    enum class State {
        //has never been used, should return here after identity resets
        BLANK,

        //user has accepted the terms of service
        ACCEPTED_TOS,

        //an account has been created/restored
        DEFAULT_ACCOUNT_EXISTS,
    }

    companion object {

        @VisibleForTesting(otherwise = PRIVATE)
        const val ONBOARDING_PREFS = "onboarding"

        @VisibleForTesting(otherwise = PRIVATE)
        const val HAS_ACCEPTED_TOS = "tos_accepted"

        /**
         * used to determine if the current onboarding state is good enough to proceed to dashboard
         */
        val READY_TO_USE = State.DEFAULT_ACCOUNT_EXISTS
    }
}