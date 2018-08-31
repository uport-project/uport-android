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

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.uport.android.R
import me.uport.android.interactors.ItemClickListener
import me.uport.sdk.identity.Account

/**
 * Differentiating adapter for the list of accounts.
 */
class AccountsListAdapter(private val itemClickListener: ItemClickListener<Account>)
    : ListAdapter<Account, AccountsListAdapter.AccountViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val containerView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_account, parent, false)

        containerView.setOnClickListener(::onItemClick)

        return AccountViewHolder(containerView)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        val account = getItem(position)

        with(holder) {
            containerView.tag = account
            titleView.text = account.deviceAddress
//            icon.text = resolveIcon(account)
        }
    }

    private fun onItemClick(v: View) {
        val item = v.tag as Account
        itemClickListener.onItemClick(item)
    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Account> = object : DiffUtil.ItemCallback<Account>() {
            override fun areItemsTheSame(old: Account, new: Account) =
                    (old.handle == new.handle)

            override fun areContentsTheSame(old: Account, new: Account) =
                    (old == new)
        }
    }


    class AccountViewHolder(val containerView: View) : RecyclerView.ViewHolder(containerView) {

        val icon: ImageView = containerView.findViewById(R.id.icon)
        val titleView: TextView = containerView.findViewById(R.id.title)

    }
}

