package com.example.orderapp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.TextView
import com.example.orderapp.R
import com.example.orderapp.types.Category
import com.example.orderapp.types.Guest
import com.example.orderapp.types.Product

class MenuListViewAdapter(
    private val context: Context,
    private val groupList: List<Category>,
    private val menuCollection: Map<String, List<Product>>)
    : BaseExpandableListAdapter() {

    override fun getGroupCount() = menuCollection.size

    override fun getChildrenCount(groupPosition: Int) = menuCollection[groupList[groupPosition].name]?.size ?: -1

    override fun getGroup(groupPosition: Int) = groupList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = menuCollection[groupList[groupPosition].name]?.get(childPosition) as Product

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val category = getGroup(groupPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.fragment_menu_category, null)
        }

        //TODO inflate correctly
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        tvCategoryName.text = category.name

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val product = getChild(groupPosition, childPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.fragment_menu_product, null)
        }

        //TODO inflate correctly
        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        tvProductName.text = product.name
        val tvProductPrize: TextView = view.findViewById(R.id.tvProductPrize)
        tvProductPrize.text = product.prize.toString()
        val tvInBasket: TextView = view.findViewById(R.id.tvProductQuantity)
        tvInBasket.text = Guest.count(product).toString()

        val btnRmvProduct: Button = view.findViewById(R.id.btnRmvProduct)
        btnRmvProduct.setOnClickListener {
            Guest.removeProduct(product)
            tvInBasket.text = Guest.count(product).toString()
        }

        val btnAddProduct: Button = view.findViewById(R.id.btnAddProduct)
        btnAddProduct.setOnClickListener {
            Guest.addProduct(product)
            tvInBasket.text = Guest.count(product).toString()
        }

        //TODO popup action handling

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true
}