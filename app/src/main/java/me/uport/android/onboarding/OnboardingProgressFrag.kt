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

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.uport.android.R
import me.uport.android.databinding.FragmentOnboardingProgressBinding
import org.koin.android.architecture.ext.sharedViewModel

class OnboardingProgressFrag : Fragment() {

    private val viewModel: OnboardingProgressViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentOnboardingProgressBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_onboarding_progress,
                container,
                false)
        binding.model = viewModel

        return binding.root
    }


}
