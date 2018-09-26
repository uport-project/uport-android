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

import android.arch.lifecycle.Observer
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.InputType
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.NavHostFragment
import me.uport.android.R
import me.uport.android.databinding.FragmentRecoverSeedBinding
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class RecoverSeedFrag : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }
    private val recoveryModel: RecoverSeedViewModel by viewModel()
    private val onboardingModel: OnboardingProgressViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentRecoverSeedBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_recover_seed,
                container,
                false)

        binding.model = recoveryModel
        recoveryModel.phrase.observe(this, Observer<String> {
            recoveryModel.processPhrase(it ?: "")
        })

        binding.btnNext.setOnClickListener {
            checkPhraseAndNavigate()
        }

        binding.phraseEdit.editText?.apply {
            setOnEditorActionListener { _, _, _ ->
                checkPhraseAndNavigate()
            }
            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_ENTER) {
                    checkPhraseAndNavigate()
                } else {
                    false
                }
            }
            imeOptions = EditorInfo.IME_ACTION_NEXT
            setRawInputType(InputType.TYPE_CLASS_TEXT)
        }

        return binding.root
    }

    private fun checkPhraseAndNavigate(): Boolean {
        return if (recoveryModel.isPhraseComplete.get()) {
            onboardingModel.requestNewAccount(recoveryModel.phrase.value)
            navController.navigate(R.id.action_recoverScreen_to_onboardingProgress)
            true
        } else {
            false
        }
    }

}
