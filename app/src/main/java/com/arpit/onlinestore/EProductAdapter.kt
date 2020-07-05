package com.arpit.onlinestore

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.e_product_row.view.*


class EProductAdapter(var context : Context, var arrayList:ArrayList<EProduct>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val productView = LayoutInflater.from(context).inflate(R.layout.e_product_row,parent,false)
        return ProductViewHolder(productView)
    }

    override fun getItemCount(): Int {

        return arrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ProductViewHolder).initializeRowUIComponents(arrayList.get(position).id,arrayList.get(position).name,
            arrayList.get(position).price,arrayList.get(position).pictureName)
    }

    inner class  ProductViewHolder(pView: View)
        :RecyclerView.ViewHolder(pView)
    {
        fun initializeRowUIComponents(id: Int,name:String,price:Int,picName:String)
        {
            itemView.txtId.text = "id-"+id
            itemView.txtName.text = "name-"+name
            itemView.txtPrice.text = "price-"+price

            var picUrl = "http://192.168.43.29/OnlineStoreApp/osimages/"
            picUrl = picUrl.replace(" ","%20")
            Picasso.get().load(picUrl + picName).into(itemView.imgPicture)

            itemView.imgAdd.setOnClickListener {

                Person.addToCartProductid = id
                var amountFragment = AmountFragment()
                var fragmentManager = (itemView.context as Activity).fragmentManager
                amountFragment.show(fragmentManager,"TAG")
            }
        }
    }

}