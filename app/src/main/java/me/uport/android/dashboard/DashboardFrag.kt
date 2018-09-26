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

package me.uport.android.dashboard

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.*
import androidx.navigation.fragment.NavHostFragment
import me.uport.android.R
import me.uport.android._dummy.UnimplementedFrag
import me.uport.android.accounts.AccountsFrag
import me.uport.android.contacts.ContactsFrag
import me.uport.android.databinding.FragmentDashboardBinding
import me.uport.android.profile.UserProfileViewModel
import me.uport.android.verifications.VerificationsFrag
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFrag : Fragment() {

    private val viewModel: DashboardViewModel by viewModel()
    private val userModel: UserProfileViewModel by sharedViewModel()

    private val navController by lazy { NavHostFragment.findNavController(this) }
    private var tabPagerAdapter: TabPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentDashboardBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_dashboard,
                container, false)

        binding.setLifecycleOwner(this)
        binding.userModel = userModel

        tabPagerAdapter = TabPagerAdapter(childFragmentManager)

        binding.container.adapter = tabPagerAdapter
        binding.container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(binding.tabs))
        binding.tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(binding.container))

        binding.fab.setOnClickListener { _ -> navController.navigate(R.id.action_dashboard_to_scanner) }
        binding.profileContainer.setOnClickListener { _ -> navController.navigate(R.id.action_dashboard_to_userProfileScreen) }
        binding.btnNotifications.setOnClickListener { _ -> navController.navigate(R.id.action_dashboard_to_notifications) }

        return binding.root
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     * TODO: perhaps replace this with bottom navigation tabs
     */
    inner class TabPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> VerificationsFrag()
                1 -> AccountsFrag()
                2 -> ContactsFrag()
                else -> UnimplementedFrag()
            }
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
    }

}
