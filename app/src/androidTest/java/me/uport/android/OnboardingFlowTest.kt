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

import android.support.annotation.IdRes
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.NoActivityResumedException
import android.support.test.espresso.ViewInteraction
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.typeText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.widget.EditText
import android.widget.TextView
import junit.framework.Assert.assertTrue
import me.uport.android.onboarding.Onboarding
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.inject
import org.koin.test.KoinTest


@RunWith(AndroidJUnit4::class)
class OnboardingFlowTest : KoinTest {

    @JvmField
    @Rule
    val activityRule = ActivityTestRule(NavHostActivity::class.java, true, false)

    @Before
    fun run_before_every_test() {

        val onboarding: Onboarding by inject()
        onboarding.clearUser()

        activityRule.launchActivity(null)
    }


    @Test
    fun application_startup_with_blank_state_leads_to_onboarding_flow() {
        onView(withId(R.id.onboarding_nav_host_frag)).check(matches(isDisplayed()))
    }

    @Test
    fun walkthrough_account_recovery_happy_path() {

        onView(withId(R.id.btn_accept)).perform(click())
        onView(withId(R.id.btn_recover)).perform(click())

        onEditTextWithinTilWithId(R.id.phrase_edit).perform(typeText("vessel ladder alter error federal sibling chat ability sun glass valve picture"))

        onView(withId(R.id.btn_next)).perform(click())

        onView(withText(R.string.identity_creation_success)).check(matches(isDisplayed()))
    }

    @Test
    fun walkthrough_account_recovery_rejects_incomplete_phrases() {

        onView(withId(R.id.btn_accept)).perform(click())
        onView(withId(R.id.btn_recover)).perform(click())

        onEditTextWithinTilWithId(R.id.phrase_edit).perform(typeText("this is a wrong phrase"))
        onErrorViewWithinTilWithId(R.id.phrase_edit).check(matches(withText("phrase incomplete")))

        onView(withId(R.id.btn_next)).perform(click())

        //phrase editor is still displayed
        onView(withId(R.id.phrase_edit)).check(matches(isDisplayed()))
    }

    @Test
    fun walkthrough_account_creation() {
        onView(withId(R.id.btn_accept)).perform(click())
        onView(withId(R.id.btn_create)).perform(click())

        onView(withText(R.string.identity_creation_success)).check(matches(isDisplayed()))
    }

    @Test
    fun walkthrough_successful_onboarding_leads_to_dashboard() {
        onView(withId(R.id.btn_accept)).perform(click())
        onView(withId(R.id.btn_create)).perform(click())
        onView(withId(R.id.btn_continue)).perform(click())
        //goes to dashboard
        onView(withId(R.id.nav_host_frag)).check(matches(isDisplayed()))
        try {
            //and does not go back
            pressBack()
        } catch (ex: Exception) {
            assertTrue(ex is NoActivityResumedException)
        }
    }

    fun onEditTextWithinTilWithId(@IdRes textInputLayoutId: Int): ViewInteraction {
        //assuming TextInputLayout has only one EditText
        return onView(allOf(
                isDescendantOfA(withId(textInputLayoutId)),
                isAssignableFrom(EditText::class.java))
        )
    }

    fun onErrorViewWithinTilWithId(@IdRes textInputLayoutId: Int): ViewInteraction {
        //assuming TextInputLayout has only one descendant of type TextView that is not also EditText
        return onView(allOf(
                isDescendantOfA(withId(textInputLayoutId)),
                not(isAssignableFrom(EditText::class.java)),
                isAssignableFrom(TextView::class.java)))
    }
}