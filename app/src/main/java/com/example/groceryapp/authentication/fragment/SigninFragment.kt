package com.example.groceryapp.authentication.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groceryapp.R
import com.example.groceryapp.authentication.viewmodel.AuthenticationViewmodel
import com.example.groceryapp.base.utils.Validation.isValidNumber
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import me.ibrahimsn.lib.CirclesLoadingView


class  SigninFragment : Fragment() {


    val authenticationViewmodel: AuthenticationViewmodel by activityViewModels()
    private lateinit var newuser_link: TextView
    private lateinit var email_text: TextView
    private lateinit var signin_number_text: EditText
    private lateinit var googleBtn: AppCompatButton
    private lateinit var forward_icon: ImageButton
    lateinit var loader: CirclesLoadingView
    lateinit var gso: GoogleSignInOptions
    lateinit var gsc: GoogleSignInClient
    lateinit var sign_in_edit_text: EditText
    private val RC_SIGN_IN = 1


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_signin, container, false)
        newuser_link = view.findViewById(R.id.newuser_link)
        email_text = view.findViewById(R.id.email_text)
        signin_number_text = view.findViewById(R.id.sign_in_edit_text)
        forward_icon = view.findViewById(R.id.forward_icon)
        loader = view.findViewById(R.id.loader)
        googleBtn = view.findViewById(R.id.googleBtn)
        sign_in_edit_text = view.findViewById(R.id.sign_in_edit_text)

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        gsc = GoogleSignIn.getClient(requireContext(), gso)

        googleBtn.setOnClickListener {
            googleSignIn()
        }

        authenticationViewmodel.liveData.observe(viewLifecycleOwner
        ) {
            if (it > 0) {
                authenticationViewmodel.otp = it
                requireActivity().supportFragmentManager.beginTransaction()
                    .add(R.id.container, OtpFragment::class.java, null)
                    .addToBackStack(null)
                    .commit()
            }
        }

        newuser_link.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SignupFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }

        email_text.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, SigninEmailFragment::class.java, null)
                .addToBackStack(null)
                .commit()
        }

        forward_icon.setOnClickListener {
            requireActivity().currentFocus?.let { view ->
                val imm =requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
            validateData()
        }

        return view.rootView
    }

    private fun validateData() {
        if (signin_number_text.text.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Please enter your mobile number", Toast.LENGTH_SHORT).show()
        } else {
            val mobileNum = signin_number_text.text.toString().trim()
            authenticationViewmodel.mobile= mobileNum
            if (!mobileNum.isValidNumber()) {
                Toast.makeText(requireContext(), "Please enter valid mobile number", Toast.LENGTH_SHORT).show()
                return
            }
            loader.visibility = View.VISIBLE
            authenticationViewmodel.getOtp(requireActivity(), mobileNum, this)
        }
    }

    private fun googleSignIn() {
        val signInIntent = gsc.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == -1) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                task.getResult(ApiException::class.java)

                loader.visibility=View.VISIBLE
                authenticationViewmodel.loginGoogle(requireActivity(), task.result.email.toString(),task.result.displayName.toString(), this )

            } catch (e: ApiException) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        authenticationViewmodel.liveData.postValue(0)
    }
}