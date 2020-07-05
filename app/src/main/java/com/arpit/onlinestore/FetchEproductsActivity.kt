package com.arpit.onlinestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_fetch_eproducts.*

class FetchEproductsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetch_eproducts)

        val selectedBrand = intent.getStringExtra("BRAND")
        txtBrandName.text = "Products Of $selectedBrand"

        val productList = ArrayList<EProduct>()
        val productUrl = "http://192.168.43.29/OnlineStoreApp/fetch_eproducts.php?brand=$selectedBrand"
        val requestQ = Volley.newRequestQueue(this@FetchEproductsActivity)

        val jsonAR = JsonArrayRequest(Request.Method.GET,productUrl,null,Response.Listener
        {response ->

            for(productJOIndex in 0.until(response.length()))
            {
                productList.add(EProduct(response.getJSONObject(productJOIndex).getInt("id"),response.getJSONObject(productJOIndex).getString("name")
                ,response.getJSONObject(productJOIndex).getInt("price"),response.getJSONObject(productJOIndex).getString("picture")))
            }

            val pAdapter = EProductAdapter(this@FetchEproductsActivity,productList)
            fetchProductRV.layoutManager = LinearLayoutManager(this@FetchEproductsActivity)
            fetchProductRV.adapter = pAdapter

        },Response.ErrorListener
        {error ->
            val dialogBuilder = AlertDialog.Builder(this@FetchEproductsActivity)
            dialogBuilder.setTitle("Message")
            dialogBuilder.setMessage(error.message)
            dialogBuilder.create().show()
        })
        requestQ.add(jsonAR)
    }
}