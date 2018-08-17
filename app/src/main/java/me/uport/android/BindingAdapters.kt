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

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.design.widget.TextInputLayout
import android.widget.ImageView
import android.widget.TextView


@BindingAdapter("srcCompat")
fun ImageView.setImageUri(imageUri: String?) = imageUri
        ?.let { setImageURI(Uri.parse(it)) }
        ?: setImageURI(null)

@BindingAdapter("srcCompat")
fun ImageView.setImageUri(imageUri: Uri) = setImageURI(imageUri)

@BindingAdapter("srcCompat")
fun ImageView.setDrawable(drawable: Drawable) = setImageDrawable(drawable)

@BindingAdapter("srcCompat")
fun ImageView.setImageRes(@DrawableRes resource: Int) = setImageResource(resource)

@BindingAdapter("android:text")
fun TextView.setStringResource(@StringRes res: Int) = setText(res)

@BindingAdapter("errorText")
fun TextInputLayout.setErrorText(err: String?) {
    this.error = err ?: ""
}