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

import com.nhaarman.mockito_kotlin.spy
import com.nhaarman.mockito_kotlin.whenever
import me.uport.android.coreApp
import me.uport.android.fakes.inMemoryPrefs
import me.uport.android.fakes.prepareMockApplication
import me.uport.android.onboarding.Onboarding.Companion.HAS_ACCEPTED_TOS
import me.uport.sdk.Uport
import me.uport.sdk.identity.Account
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.with
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class OnboardingStateTest : KoinTest {

    @Before
    fun `run before every test`() {
        startKoin(coreApp) with prepareMockApplication()

        loadKoinModules(listOf(applicationContext {
            bean<Any> { inMemoryPrefs }
        }))
    }

    @After
    fun `run after every test`() {
        closeKoin()
    }

// FIXME: uncomment for onboarding screens
//    @Test
//    fun `onboarding state is BLANK when user hasn't accepted TOS`() {
//        inMemoryPrefs.edit().remove(HAS_ACCEPTED_TOS).apply()
//        val tested: Onboarding by inject()
//        assertEquals(Onboarding.State.BLANK, tested.getState())
//    }

    @Test
    fun `onboarding state is updated when user accepts TOS`() {
        val tested: Onboarding by inject()

        tested.markTosAccepted()

        assertEquals(Onboarding.State.ACCEPTED_TOS, tested.getState())
    }

    @Test
    fun `onboarding is final after uport default account exists`() {

        loadKoinModules(listOf(applicationContext {
            val uportSDKMock = spy<Uport>()
            whenever(uportSDKMock.defaultAccount).thenReturn(Account.blank)
            bean { uportSDKMock }
        }))

        val tested: Onboarding by inject()
        tested.apply { markTosAccepted() }

        assertEquals(Onboarding.State.DEFAULT_ACCOUNT_EXISTS, tested.getState())

        assertTrue(tested.canShowDashboard())

    }

}