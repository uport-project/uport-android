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

package me.uport.android.scanner


import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.NavHostFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import me.uport.android.R

/**
 * A wrapper around [IntentIntegrator] that allows
 * the QR scanner to be plugged into a Navigation graph
 *
 */
class ScannerFragment : Fragment() {

    private val navController by lazy { NavHostFragment.findNavController(this) }

    override fun onResume() {
        super.onResume()
        initiateScan()
    }

    private fun initiateScan() {

        IntentIntegrator.forSupportFragment(this)
                .setPrompt(getString(R.string.scan_barcode_prompt))
                .setOrientationLocked(false)
                .setBeepEnabled(false)
                .setBarcodeImageEnabled(false)
                .initiateScan()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                navController.popBackStack()
            } else {
                interpretResult(result)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun interpretResult(result: IntentResult) {

        val uri = result.contents
        Toast.makeText(context, uri, Toast.LENGTH_SHORT).show()

        //TODO: actually parse the results

        navController.popBackStack()
    }

}
