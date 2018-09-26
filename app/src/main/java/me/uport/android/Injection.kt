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
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val uportSDK = module {
    single { Uport.Configuration().setApplicationContext(androidApplication()) }
    single {
        Uport.initialize(get())
        Uport
    }
}

val onboardingModule = module {
    viewModel { OnboardingProgressViewModel(androidApplication(), get()) }
    viewModel { RecoverSeedViewModel() }
}

val dashboardModule = module {
    viewModel { DashboardViewModel() }
    viewModel { AccountsViewModel(get()) }
}

val userModule = module {
    viewModel { UserProfileViewModel(androidApplication()) }
}

val coreModule = module {

    single(ONBOARDING_PREFS) { androidApplication().getSharedPreferences(ONBOARDING_PREFS, MODE_PRIVATE) }

    single { Onboarding(get()) }

}

val coreApp = listOf(coreModule, uportSDK, onboardingModule, dashboardModule, userModule)