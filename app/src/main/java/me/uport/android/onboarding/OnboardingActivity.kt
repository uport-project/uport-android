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
import android.support.v7.app.AppCompatActivity
import me.uport.android.R
import org.koin.android.ext.android.inject

class OnboardingActivity : AppCompatActivity() {

    private val onboarding : Onboarding by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (onboarding.canShowDashboard()) {
            finish()
            return
        }

        setContentView(R.layout.activity_onboarding)

    }
}
