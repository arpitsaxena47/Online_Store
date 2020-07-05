package com.arpit.onlinestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_cart_products.*

class CartProductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_products)

        var cartProductsUrl = "http://192.168.43.29/OnlineStoreApp/fetch_temporary_order.php?email=${Person.email}"
        var cartProductsList = ArrayList<String>()
        var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
        var jsonAR = JsonArrayRequest(Request.Method.GET,cartProductsUrl,null,Response.Listener {
            response ->
            for(joIndex in 0.until(response.length()))//id, name, price, email, amount
            {
                cartProductsList.add("${response.getJSONObject(joIndex).getInt("id")} \n " +
                        "${response.getJSONObject(joIndex).getString("name")} \n ${response.getJSONObject(joIndex).getInt("price")}" +
                        "\n ${response.getJSONObject(joIndex).getString("email")} \n" +
                        " ${response.getJSONObject(joIndex).getInt("amount")} \n")

            }
            var cartProductsAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,cartProductsList)
            cartPoductsListView.adapter = cartProductsAdapter
        },Response.ErrorListener {
            error ->
            val dialogBuilder = AlertDialog.Builder(this@CartProductsActivity)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQ.add(jsonAR)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.cart_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item?.itemId == R.id.continueShoppingItem)
        {
            var intent = Intent(this, HomeScreen::class.java)
            startActivity(intent)
        }
        else
            if(item?.itemId == R.id.declineOrderItem)
            {
                var declineUrl = "http://192.168.43.29/OnlineStoreApp/decline_order.php?email=${Person.email}"
                var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
                var stringRequest = StringRequest(Request.Method.GET, declineUrl,Response.Listener {
                    response ->
                    var intent = Intent(this,HomeScreen::class.java)
                    startActivity(intent)
                    finish()
                },Response.ErrorListener {
                    error ->

                })
                requestQ.add(stringRequest)
            }
        else
                if(item?.itemId == R.id.verifyOrderItem)
                {
                    var verifyOrderUrl = "http://192.168.43.29/OnlineStoreApp/verify_order.php?email=${Person.email}"
                    var requestQ = Volley.newRequestQueue(this@CartProductsActivity)
                    var stringRequest = StringRequest(Request.Method.GET,verifyOrderUrl,Response.Listener {
                        response ->

                        var intent = Intent(this,FinalizeShoppingActivity::class.java )
                        Toast.makeText(this,response,Toast.LENGTH_SHORT).show()
                        intent.putExtra("LATEST_INVOICE_NUMBER",response)
                        startActivity(intent)

                    },Response.ErrorListener {
                        error ->
                        val dialogBuilder = AlertDialog.Builder(this@CartProductsActivity )
                        dialogBuilder.setTitle("Message")
                        dialogBuilder.setMessage(error.message)
                        dialogBuilder.create().show()

                    })
                    requestQ.add(stringRequest)
                }
        return super.onOptionsItemSelected(item)
    }
}