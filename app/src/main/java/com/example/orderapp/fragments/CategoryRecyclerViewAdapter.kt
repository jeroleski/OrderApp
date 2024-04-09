package com.example.orderapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import com.example.orderapp.databinding.FragmentCategoryColBinding

/**
 * [RecyclerView.Adapter] that can display a [Category].
 * TODO: Replace the implementation with code for your data type.
 */
class CategoryRecyclerViewAdapter(
    private val values: List<Category>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<CategoryRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentCategoryColBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = values[position]

        holder.btnHeader.text = category.name
        holder.btnHeader.setOnClickListener {
            val productList = holder.productContainer
            if (productList.visibility == View.VISIBLE)
                productList.visibility = View.GONE
            else
                productList.visibility = View.VISIBLE
        }

        fragmentManager.beginTransaction().replace(holder.productContainer.id, ProductFragment.newInstance(category)).commit()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentCategoryColBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val btnHeader: Button = binding.btnHeader
        val productContainer: FragmentContainerView = binding.productContainer

        override fun toString(): String {
            return super.toString() + " '" + btnHeader.text + "'"
        }
    }

}