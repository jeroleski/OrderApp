package com.example.orderapp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.orderapp.R
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct

class OrderListViewAdapter(
    private val context: Context,
    private val groupList: List<Order>,
    private val orderCollection: Map<String, List<OrderProduct>>)
    : BaseExpandableListAdapter() {

    override fun getGroupCount() = orderCollection.size

    override fun getChildrenCount(groupPosition: Int) = orderCollection[groupList[groupPosition].id.toString()]?.size ?: -1

    override fun getGroup(groupPosition: Int) = groupList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = orderCollection[groupList[groupPosition].id.toString()]?.get(childPosition) as OrderProduct
    override fun getGroupId(groupPosition: Int) = getGroup(groupPosition).id.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = getChild(groupPosition, childPosition).product.id.toLong()

    override fun hasStableIds() = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val order = getGroup(groupPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.fragment_orders_order, null)
        }

        val tvOrderTable: TextView = view.findViewById(R.id.tvOrderTable)
        tvOrderTable.text = order.tableNumber.toString()

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val orderProduct = getChild(groupPosition, childPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.fragment_orders_product, null)
        }

        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        tvProductName.text = orderProduct.product.name
        val tvProductQuantity: TextView = view.findViewById(R.id.tvProductPrize)
        tvProductQuantity.text = orderProduct.quantity.toString()

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true
}