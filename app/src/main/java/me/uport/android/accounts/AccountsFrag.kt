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

package me.uport.android.accounts

import android.arch.lifecycle.Observer
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_accounts.*
import me.uport.android.R
import me.uport.android.interactors.ItemClickListener
import me.uport.sdk.identity.Account
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * A fragment representing a list of [Account]s.
 */
class AccountsFrag : Fragment(), ItemClickListener<Account> {

    private val viewModel: AccountsViewModel by viewModel()
    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_accounts, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val adapter = AccountsListAdapter(this)
        accountList.adapter = adapter
        accountList.adapter?.notifyDataSetChanged()

        viewModel.accounts.observe(this, Observer { accounts ->
            //submitList requires a new list so ensure a clone is created every time so I'm calling toMutableList
            adapter.submitList(accounts?.toMutableList()?.toList())
        })
    }

    override fun onItemClick(item: Account) {
        val params = Bundle().apply {
            putString(ACCOUNT_HANDLE, item.handle)
        }
        navController.navigate(R.id.action_view_account_details, params)

        val clipboard = context?.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("address", item.deviceAddress)
        clipboard.primaryClip = clip

        Toast.makeText(context, "address copied to clipboard", Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val ACCOUNT_HANDLE: String = "account_handle"
    }

}
