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

package me.uport.android.profile

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel;

class UserProfileViewModel : ViewModel() {
    val userProperties = MutableLiveData<Map<String, String>>().apply {
        value = mapOf(
                avatarUrl to "",
                username to "Lorem R Daneel",
                "email" to "lorem.daneel@foundation.universe",
                "location" to "Lorem Ipsum Trantor City"
        )
    }

    companion object {
        //a few usual properties
        const val avatarUrl = "avatarUrl"
        const val username = "username"
        const val email = "email"
    }
}
