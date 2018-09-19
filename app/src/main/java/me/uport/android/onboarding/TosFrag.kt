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


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_tos.*
import me.uport.android.R
import org.koin.android.ext.android.inject
import java.io.InputStreamReader


/**
 * A simple [Fragment] subclass.
 *
 */
class TosFrag : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }
    val onboarding: Onboarding by inject()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tos_text.text = readFromAssets(view.context, "tos.txt")
        btn_accept.setOnClickListener {
            onboarding.markTosAccepted()
            navController.navigate(R.id.action_tosScreen_to_createOrRecoverScreen)
        }
    }

    private fun readFromAssets(context: Context, filename: String): String {
        val reader = InputStreamReader(context.assets.open(filename))
        val fullText = reader.readText()
        reader.close()
        return fullText
    }


}
