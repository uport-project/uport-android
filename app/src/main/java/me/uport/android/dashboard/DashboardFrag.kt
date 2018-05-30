package me.uport.android.dashboard

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.dashboard_fragment.*
import me.uport.android.R

class DashboardFrag : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        NavigationUI.setupActionBarWithNavController(
                activity as AppCompatActivity,
                navController)

        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        // TODO: Use the ViewModel - determine default user, accounts, claims, contacts and bind the appropriate views to the viewmodel

        fabView.setOnClickListener(::navigateToScanner)
    }

    fun navigateToScanner(v: View) {
        navController.navigate(R.id.action_dashboard_to_scanner)
    }
}
