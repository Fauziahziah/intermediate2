package com.example.mysubmission_intermediate.UI

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mysubmission_intermediate.MainActivity
import com.example.mysubmission_intermediate.R
import com.example.mysubmission_intermediate.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModelFactory: ViewModelFactory
    private val signUpViewModel: SignUpViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAction()
        setupViewModel()
        setActionSignUp()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupAction() {
        binding.apply {
            btnBack.setOnClickListener{
                val intent = Intent(this@SignUpFragment.requireContext(), MainActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun setupViewModel() {
        viewModelFactory = ViewModelFactory.getInstance(requireActivity())

    }

    private fun setActionSignUp() {
        binding.btnSignUp.setOnClickListener {
            val email = binding.etEmailEdit.text.toString()
            val username = binding.etUsernameEdit.text.toString()
            val password = binding.etPasswordEdit.text.toString()

            when {
                email.isEmpty() -> {
                    binding.etUsernameEdit.error = getString(R.string.message_name_error)
                }
                username.isEmpty() -> {
                    binding.etEmailEdit.error = getString(R.string.message_email_error)
                }
                password.isEmpty() -> {
                    binding.etPasswordEdit.error = getString(R.string.message_password_error)
                }

                else -> {
                    setId()
                    showToast()
                    intentFragment()
                }
            }
        }
    }

    private fun intentFragment() {
        signUpViewModel.registerResponse.observe( viewLifecycleOwner) { response ->
            if (!response.error) {
                requireActivity().run {
                    parentFragmentManager.beginTransaction()
                        .replace(R.id.SignUpUp, SignInFragment())
                        .addToBackStack("back_signin_fragment")
                        .commit()
                    finish()
                }
            }
            binding.btnSignUp.visibility = View.GONE

        }
    }


    private fun setId() {
        binding.apply {
            signUpViewModel.registerAccount(
                etUsernameEdit.text.toString(),
                etEmailEdit.text.toString(),
                etPasswordEdit.text.toString()
            )
        }
    }

    private fun showToast() {
        signUpViewModel.toastText.observe(viewLifecycleOwner) { toastText ->
            Toast.makeText(activity, toastText, Toast.LENGTH_SHORT
            ).show()
        }
    }



}


