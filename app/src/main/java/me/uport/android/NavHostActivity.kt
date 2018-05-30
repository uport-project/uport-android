package me.uport.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_nav_host.*

class NavHostActivity : AppCompatActivity() {

    private val navController by lazy { (navHostFrag as NavHostFragment).navController }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav_host)
        setSupportActionBar(toolbar)

        NavigationUI.setupActionBarWithNavController(this, navController)

    }

    override fun onSupportNavigateUp()
            = (navHostFrag as NavHostFragment).navController.navigateUp()

}
