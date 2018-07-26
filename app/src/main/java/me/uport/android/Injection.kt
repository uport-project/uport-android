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

package me.uport.android

import android.content.Context.MODE_PRIVATE
import me.uport.android.onboarding.Onboarding
import me.uport.android.onboarding.Onboarding.Companion.ONBOARDING_PREFS
import me.uport.sdk.Uport
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val uportSDK = applicationContext {
    bean { Uport.Configuration().setApplicationContext(androidApplication()) }
    bean {
        Uport.initialize(get())
        Uport
    }
}

val coreModule = applicationContext {

    bean(ONBOARDING_PREFS) { androidApplication().getSharedPreferences(ONBOARDING_PREFS, MODE_PRIVATE) }

    bean { Onboarding(get()) }
}

val coreApp = listOf(coreModule, uportSDK)