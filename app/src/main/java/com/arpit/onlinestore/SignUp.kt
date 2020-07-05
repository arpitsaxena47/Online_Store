package com.arpit.onlinestore

import android.app.DownloadManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUp : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btnSignupLogin.setOnClickListener {
            var loginIntent = Intent(this@SignUp,MainActivity::class.java)
            startActivity(loginIntent)
            finish()
        }

        btnSignUpSignup.setOnClickListener {


                if(edtPasswordSignup.text.toString().equals(edtConfirmPassword.text.toString()))
                     {

                        val signUpURL =
                            "http://192.168.43.29/OnlineStoreApp/join_new_user.php?email=" +
                                    edtEmailSignup.text.toString() + "&username=" + edtUsernameSignUp.text.toString() +
                                    "&pass=" + edtPasswordSignup.text.toString()

                         val requestQ = Volley.newRequestQueue(this@SignUp)
                         val stringRequest = StringRequest(Request.Method.GET, signUpURL,Response.Listener
                         { response ->
                             if(response.equals("A user with this email already exist"))
                             {
                                 val dialogBuilder = AlertDialog.Builder(this@SignUp)
                                 dialogBuilder.setTitle("Message")
                                 dialogBuilder.setMessage(response)
                                 dialogBuilder.create().show()
                             }
                             else
                             {
                                 Person.email = edtEmailSignup.text.toString()

                                 val dialogBuilder = AlertDialog.Builder(this@SignUp)
                                 dialogBuilder.setTitle("Message")
                                 dialogBuilder.setMessage(response)
                                 dialogBuilder.create().show()
                                 val HomeIntent = Intent(this@SignUp,HomeScreen::class.java)
                                 startActivity(HomeIntent)
                             }
                         }, Response.ErrorListener { error ->
                             val dialogBuilder = AlertDialog.Builder(this@SignUp)
                             dialogBuilder.setTitle("Message")
                             dialogBuilder.setMessage(error.message)
                             dialogBuilder.create().show()
                         })
                         requestQ.add(stringRequest)

                    }
                else
                {
                    val dialogBuilder = AlertDialog.Builder(this@SignUp)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage("Password Mismatch")
                    dialogBuilder.create().show()
                }


        }
    }
}