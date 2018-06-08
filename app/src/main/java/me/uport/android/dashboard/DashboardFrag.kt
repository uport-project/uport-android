package me.uport.android.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_dashboard.*
import me.uport.android.R
import me.uport.android._dummy.UnimplementedFrag
import me.uport.android.accounts.AccountsFrag
import me.uport.android.contacts.ContactsFrag
import me.uport.android.verifications.VerificationsFrag

class DashboardFrag : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private val navController by lazy { NavHostFragment.findNavController(this) }
    private var mSectionsPagerAdapter: SectionsPagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        // TODO: Use the ViewModel - determine default user, accounts, claims, contacts and bind the appropriate views to the viewmodel

        mSectionsPagerAdapter = SectionsPagerAdapter(fragmentManager!!)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

        fabView.setOnClickListener(::navigateToScanner)
        btnNotifications.setOnClickListener({ v ->
            navController.navigate(R.id.action_dashboard_to_notifications)
        })
    }

    private fun navigateToScanner(v: View) {
        navController.navigate(R.id.action_dashboard_to_scanner)
    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

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

}
