package com.arpit.onlinestore

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.fragment.app.DialogFragment
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


class AmountFragment : android.app.DialogFragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var fragmentView =  inflater.inflate(R.layout.fragment_amount, container, false)

        var edtEnterAmount = fragmentView.findViewById<EditText>(R.id.edtAmount)
        var btnAddToCart = fragmentView.findViewById<ImageView>(R.id.imgAddToCart)

        btnAddToCart.setOnClickListener {

            val ptoUrl = "http://192.168.43.29/OnlineStoreApp/insert_temporary_order.php?email=${Person.email}&product_id=${Person.addToCartProductid}&" +
                    "amount=${edtEnterAmount.text}"
            var requestQ = Volley.newRequestQueue(activity)
            var stringRequest = StringRequest(Request.Method.GET,ptoUrl,Response.Listener {
                response ->
                var intent = Intent(activity,CartProductsActivity::class.java)
                startActivity(intent)
            },Response.ErrorListener {
                error ->
            })
            requestQ.add(stringRequest)
        }
        return fragmentView
    }


}