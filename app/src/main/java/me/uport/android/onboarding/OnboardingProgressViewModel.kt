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

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.databinding.ObservableBoolean
import android.databinding.ObservableInt
import android.view.View
import androidx.navigation.Navigation
import kotlinx.coroutines.experimental.launch
import me.uport.android.R
import me.uport.sdk.Uport
import me.uport.sdk.core.Networks

class OnboardingProgressViewModel(
        app: Application,
        private val uportSDK: Uport
) : AndroidViewModel(app) {

    val isLoading = ObservableBoolean()
    val isCancellable = ObservableBoolean()
    val progressText = ObservableInt()
    val titleText = ObservableInt().apply { set(R.string.title_mobile_identity) }

    fun requestNewAccount(seed: String? = null) {
        launch {
            try {
                isLoading.set(true)
                progressText.set(R.string.identity_creation_in_progress)

                uportSDK.createAccount(Networks.rinkeby, seed)

                isLoading.set(false)
                progressText.set(R.string.identity_creation_success)
            } catch (err: Exception) {
                isLoading.set(false)
                progressText.set(R.string.identity_creation_error)
            }
        }
    }


    fun goToDashboard(v: View) {
        val navController = Navigation.findNavController(v)
        navController.navigate(R.id.go_to_dashboard)
        (v.context as Activity).finish()
    }

    fun goBack(v: View) {
        val navController = Navigation.findNavController(v)
        navController.popBackStack()
    }
}
