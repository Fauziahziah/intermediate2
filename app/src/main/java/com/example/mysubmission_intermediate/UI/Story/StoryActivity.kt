package com.example.mysubmission_intermediate.UI.Story


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.onNavDestinationSelected
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.databinding.ActivityStoryBinding


class StoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStoryBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val navfragment = supportFragmentManager.findFragmentById(R.id.nav_fragment) as NavHostFragment
        navController = navfragment.navController

        navController.addOnDestinationChangedListener{
            controller, destination, arguments ->
            binding.toolbar.title = navController.currentDestination?.label
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }
}



