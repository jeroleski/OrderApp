package com.example.orderapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.orderapp.databinding.FragmentProductRowBinding

/**
 * [RecyclerView.Adapter] that can display a [Product].
 */
class ProductRecyclerViewAdapter(
    private val values: List<Product>
) : RecyclerView.Adapter<ProductRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentProductRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = values[position]
        holder.tvProductName.text = product.name
        holder.tvProductPrize.text = product.prize.toString()
        holder.tvInBasket.text = Guest.count(product).toString()

        holder.btnRmv.setOnClickListener {
            Guest.removeProduct(product)
            holder.tvInBasket.text = Guest.count(product).toString()
        }

        holder.btnAdd.setOnClickListener {
            Guest.addProduct(product)
            holder.tvInBasket.text = Guest.count(product).toString()
        }
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentProductRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvProductName: TextView = binding.tvProductName
        val tvProductPrize: TextView = binding.tvProductPrize
        val tvInBasket: TextView = binding.tvInBasket
        val btnRmv: Button = binding.btnRmv
        val btnAdd: Button = binding.btnAdd

        override fun toString(): String {
            return super.toString() + " '${tvProductName.text}'"
        }
    }

}