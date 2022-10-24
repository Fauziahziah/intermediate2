package com.example.mysubmission_intermediate.UI.Story.Home

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmission_intermediate.MainActivity
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.UI.ViewModelFactory
import com.example.mysubmission_intermediate.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
   private var _binding : FragmentHomeBinding? = null
   private val binding get() = _binding!!
    private lateinit var viewmodelFactory: ViewModelFactory
    private val homeViewModel: HomeViewModel by viewModels { viewmodelFactory }
    private var token = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.etStory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }
        
        setActions()
        setupViewModel()




    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setActions() {
        binding.apply {
            createStory.setOnClickListener(
                Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_addStory)
            )
        }
    }


    private fun setupViewModel() {
        viewmodelFactory = ViewModelFactory.getInstance(requireContext())
        val storyAdapter = StoryAdapter()

        homeViewModel.loadState().observe(viewLifecycleOwner){
            if (!it.isLogin) {
                intentActivity()
            } else {
                homeViewModel.story.observe(viewLifecycleOwner) {
                    storyAdapter.submitData(lifecycle, it)
                    binding.etStory.adapter = storyAdapter
                }
            }
        }
        showToast()
    }
  private fun showLoading() {
      homeViewModel.showLoading.observe(viewLifecycleOwner) {
          binding.viewLoading.visibility = if (it) View.VISIBLE else View.GONE
      }
  }

 private fun showToast() {
     homeViewModel.toastText.observe(viewLifecycleOwner) {  toastText ->
         Toast.makeText(
             requireContext(), toastText, Toast.LENGTH_SHORT
         ).show()
     }
 }
 private fun intentActivity() {
     startActivity((Intent(requireContext(), MainActivity::class.java)))
 }


}