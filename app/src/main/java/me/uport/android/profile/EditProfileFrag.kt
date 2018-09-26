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

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.uport.android.R
import me.uport.android.databinding.FragmentEditProfileBinding
import org.koin.android.architecture.ext.viewModel
import android.app.Activity
import android.view.inputmethod.InputMethodManager


class EditProfileFrag : Fragment() {

    private val userModel: UserProfileViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentEditProfileBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_edit_profile,
                container, false)

        binding.setLifecycleOwner(this)
        binding.userModel = userModel

        //oh android, why do I need to tell you to do this?
        binding.profileName.onFocusChangeListener = View.OnFocusChangeListener { textBox: View, hasFocus: Boolean ->
            if (!hasFocus) {
                val imm = textBox.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(textBox.windowToken, 0)
            }
        }

        return binding.root
    }

}
