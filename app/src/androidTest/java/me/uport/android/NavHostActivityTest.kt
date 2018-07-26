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


import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.DrawerActions
import android.support.test.espresso.contrib.NavigationViewActions
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.rule.GrantPermissionRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView.ViewHolder
import me.uport.sdk.Uport
import me.uport.sdk.identity.Account
import org.hamcrest.Matchers.allOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.applicationContext
import org.koin.standalone.StandAloneContext.loadKoinModules


@LargeTest
@RunWith(AndroidJUnit4::class)
class NavHostActivityTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(NavHostActivity::class.java, true, false)

    @Rule
    @JvmField
    var mGrantPermissionRule: GrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.CAMERA")

    @Before
    fun run_before_every_test() {
        val mockUport = Uport
        mockUport.defaultAccount = Account.blank
        loadKoinModules(listOf(applicationContext {
            bean { mockUport }
        }))
    }

    @Test
    fun walkThroughSomeAppScreens() {

        activityRule.launchActivity(null)

        clickOnTab("Verifications")
        clickOnTab("Accounts")
        clickOnTab("Contacts")

        //go to user profile
        onView(withId(R.id.profile_container)).perform(click())

        //go to profile edit
        onView(withId(R.id.profile_container)).perform(click())

        pressBack()

        pressBack()

        //start scanner
        onView(withId(R.id.fab)).perform(click())

        pressBack()

        //go to notifications screen
        onView(withId(R.id.btn_notifications)).perform(click())

        //go to first notification
        onView(withId(R.id.notificationList))
                .perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        pressBack()

        pressBack()


        //open nav drawer
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.aboutScreen))
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.advancedScreen))
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.recoveryScreen))
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.backupScreen))
        pressBack()

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.tryUportScreen))
        pressBack()

    }

    private fun clickOnTab(tabText: String) {
        val matcher = allOf(withText(tabText),
                isDescendantOfA(withId(R.id.tabs)))
        onView(matcher).perform(click())
    }
}
