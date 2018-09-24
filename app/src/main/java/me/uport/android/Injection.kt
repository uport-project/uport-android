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
import me.uport.android.accounts.AccountsViewModel
import me.uport.android.dashboard.DashboardViewModel
import me.uport.android.onboarding.Onboarding
import me.uport.android.onboarding.Onboarding.Companion.ONBOARDING_PREFS
import me.uport.android.onboarding.OnboardingProgressViewModel
import me.uport.android.onboarding.RecoverSeedViewModel
import me.uport.android.profile.UserProfileViewModel
import me.uport.sdk.Uport
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext

val uportSDK = applicationContext {
    bean { Uport.Configuration().setApplicationContext(androidApplication()) }
    bean {
        Uport.initialize(get())
        Uport
    }
}

val onboardingModule = applicationContext {
    viewModel { OnboardingProgressViewModel(androidApplication(), get()) }
    viewModel { RecoverSeedViewModel() }
}

val dashboardModule = applicationContext {
    viewModel { DashboardViewModel() }
    viewModel { AccountsViewModel(get()) }
}

val userModule = applicationContext {
    viewModel { UserProfileViewModel() }
}

val coreModule = applicationContext {

    bean(ONBOARDING_PREFS) { androidApplication().getSharedPreferences(ONBOARDING_PREFS, MODE_PRIVATE) }

    bean { Onboarding(get()) }

}

val coreApp = listOf(coreModule, uportSDK, onboardingModule, dashboardModule, userModule)