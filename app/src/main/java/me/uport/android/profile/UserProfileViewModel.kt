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

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ProcessLifecycleOwner
import android.content.Context.MODE_PRIVATE


class UserProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("profile_data", MODE_PRIVATE)

    val userProperties = MutableLiveData<Map<String, String>>()
            .apply {
                val map = emptyMap<String, String>().toMutableMap()
                prefs.all.forEach { (k, v) ->
                    if (v is String) {
                        map[k] = v
                    }
                }
                value = map
            }.also {
                //persist to prefs
                it.observe(ProcessLifecycleOwner.get(), Observer { props ->
                    val storage = prefs.edit()
                    props?.forEach { prop ->
                        storage.putString(prop.key, prop.value)
                    }
                    storage.apply()
                })

                //subscribe to prefs
                prefs.registerOnSharedPreferenceChangeListener { preferences, key ->
                    val newValue = preferences.getString(key, "")
                    val newMap = it.value!!.toMutableMap().apply {
                        this[key] = newValue
                    }
                    it.value = newMap
                }
            }

    /**
     * exposes userProperties["username"] for bidirectional data-binding
     */
    var editableName: String
        get() {
            val value = userProperties.value!![username] ?: ""
            println("getting name=$value")
            return value
        }
        set(newValue) {
            val newMap = userProperties.value!!.toMutableMap().apply {
                this[username] = newValue
            }
            println("setting name=$newValue")
            userProperties.value = newMap
        }


    companion object {

        //a few usual properties
        const val avatarUrl = "avatarUrl"
        const val username = "username"
        const val email = "email"
    }
}
