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

import android.app.Application
import me.uport.android.fakes.prepareMockApplication
import org.junit.Test
import org.koin.android.ext.koin.with
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.StandAloneContext.stopKoin
import org.koin.test.KoinTest
import org.koin.test.checkModules

class DependencyDryRun : KoinTest {

    @Test
    fun `should do a dry run`() {

        val ctx: Application by lazy { prepareMockApplication() }
        startKoin(coreApp) with ctx

        //this fails using koin 1.0.1
        //check https://github.com/InsertKoinIO/koin/issues/232
        // and perhaps https://github.com/InsertKoinIO/koin/issues/241

        checkModules(coreApp)

        stopKoin()
    }
}