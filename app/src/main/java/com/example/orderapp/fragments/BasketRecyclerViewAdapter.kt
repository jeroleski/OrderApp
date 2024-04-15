package com.example.orderapp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.orderapp.R
import com.example.orderapp.types.OrderProduct

class BasketRecyclerViewAdapter(
    private val context: Context,
    private val products: List<OrderProduct>)
    : RecyclerView.Adapter<BasketProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BasketProductViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_basket_product, parent, false))

    override fun getItemCount() = products.count()

    override fun onBindViewHolder(holder: BasketProductViewHolder, position: Int) {
        val product = products[position]
        holder.tvProductName.text = product.product.name
        holder.tvProductQuantity.text = context.getString(R.string.basket_quantity, product.quantity)
        holder.tvProductPrize.text = context.getString(R.string.basket_prize, product.totalPrize())
    }
}

class BasketProductViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val tvProductName: TextView = view.findViewById(R.id.tvProductName)
    val tvProductQuantity: TextView = view.findViewById(R.id.tvProductQuantity)
    val tvProductPrize: TextView = view.findViewById(R.id.tvProductPrize)
}
