package com.arpit.onlinestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnLoginSignup.setOnClickListener{
            var signUpIntent = Intent(this@MainActivity,SignUp::class.java)
            startActivity(signUpIntent)
            finish()
        }
        btnLoginLogin.setOnClickListener {
            val loginURL = "http://192.168.43.29/OnlineStoreApp/login_app_users.php?email="+
                    edtEmail.text.toString() + "&pass="+edtPassword.text.toString()

            val requestQ = Volley.newRequestQueue(this@MainActivity)
            val stringRequest = StringRequest(Request.Method.GET,loginURL, Response.Listener
            {
                response ->
                if(response.equals("The user does exist"))
                {
                    Person.email = edtEmail.text.toString()
                    Toast.makeText(this@MainActivity,"Login Successfully!!",Toast.LENGTH_SHORT)
                    val HomeIntent = Intent(this@MainActivity,HomeScreen::class.java)
                    startActivity(HomeIntent)
                }
                else
                {
                    val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                    dialogBuilder.setTitle("Message")
                    dialogBuilder.setMessage(response)
                    dialogBuilder.create().show()
                }
            },Response.ErrorListener {
                error ->

                val dialogBuilder = AlertDialog.Builder(this@MainActivity)
                dialogBuilder.setTitle("Message")
                dialogBuilder.setMessage(error.message)
                dialogBuilder.create().show()
            })
            requestQ.add(stringRequest)
        }
    }
}