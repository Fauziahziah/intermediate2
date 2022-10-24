package com.example.mysubmission_intermediate

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.example.mysubmission_intermediate.databinding.ActivityMainBinding
import com.example.mysubmission_intermediate.UI.SignUpFragment
import com.example.mysubmission_intermediate.UI.SignInFragment
import com.example.mysubmission_intermediate.UI.Story.Home.HomeViewModel
import com.example.mysubmission_intermediate.UI.Story.StoryActivity
import com.example.mysubmission_intermediate.UI.ViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val homeViewModel: HomeViewModel by viewModels {
            factory
        }

        homeViewModel.loadState().observe(this) {
            if(it.token != "" && it.isLogin){
                startActivity(Intent(this, StoryActivity::class.java))
                finish()
            }
        }
        setupView()
        setupAction()
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupAction() {
       binding.btnGoSignIn.setOnClickListener{
           supportFragmentManager.beginTransaction()
               .replace(R.id.testing, SignInFragment())
               .commit()
            binding.btnGoSignIn.visibility = View.GONE
            binding.btnGoSignUp.visibility = View.GONE

        }

        binding.btnGoSignUp.setOnClickListener{
            supportFragmentManager.beginTransaction()
                .replace(R.id.testing, SignUpFragment())
                .commit()
            binding.btnGoSignIn.visibility = View.GONE
            binding.btnGoSignUp.visibility = View.GONE
        }
    }

    override fun onBackPressed()  {
         startActivity(Intent(this, MainActivity::class.java))
         super.onBackPressed()
         finish()
    }

}