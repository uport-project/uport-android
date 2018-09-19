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


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_create_or_recover.*
import me.uport.android.R
import org.koin.android.architecture.ext.sharedViewModel

/**
 * Shows the option to create a new identity or recover an existing one
 *
 */
class CreateOrRecoverFrag : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val viewModel : OnboardingProgressViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_or_recover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_create.setOnClickListener {
            viewModel.requestNewAccount()
            navController.navigate(R.id.action_createOrRecoverScreen_to_onboardingProgress)
        }

        btn_recover.setOnClickListener {
            navController.navigate(R.id.action_createOrRecoverScreen_to_recoverScreen)
        }
    }

}
