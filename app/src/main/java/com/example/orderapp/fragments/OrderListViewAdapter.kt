package com.example.orderapp.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.Button
import android.widget.ExpandableListView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import com.example.orderapp.OrdersActivity
import com.example.orderapp.R
import com.example.orderapp.network.DbInterface
import com.example.orderapp.types.Order
import com.example.orderapp.types.OrderProduct

class OrderListViewAdapter(
    private val context: Context,
    private val groupList: List<Order>,
    private val orderCollection: Map<String, List<OrderProduct>>)
    : BaseExpandableListAdapter() {

    override fun getGroupCount() = orderCollection.size

    override fun getChildrenCount(groupPosition: Int) = orderCollection[groupList[groupPosition].documentId]?.size ?: -1

    override fun getGroup(groupPosition: Int) = groupList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = orderCollection[groupList[groupPosition].documentId]?.get(childPosition) as OrderProduct
    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

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
            inflater.inflate(R.layout.fragment_orders_order, parent, false)
        }

        val tvOrderTable: TextView = view.findViewById(R.id.tvOrderTable)
        tvOrderTable.text = order.tableNumber.toString()
        val btnDone: Button = view.findViewById(R.id.btnDone)
        btnDone.setOnClickListener {
            DbInterface().removeOrder(order)
            // reload orders activity
            val ordersIntent = Intent(context, OrdersActivity::class.java)
            startActivity(context, ordersIntent, null)
        }

        val expandableListView: ExpandableListView = with(context as Activity) {
            this.findViewById(R.id.listOrders)
        }
        //override default expansion
        view.setOnClickListener {
            // Check if the ExpandableListView is currently expanded
            if (expandableListView.isGroupExpanded(groupPosition)) {
                // If it's expanded, collapse it
                expandableListView.collapseGroup(groupPosition)
            } else {
                // If it's collapsed, expand it
                expandableListView.expandGroup(groupPosition)
            }
        }

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
            inflater.inflate(R.layout.fragment_orders_product, parent, false)
        }

        val tvProductName: TextView = view.findViewById(R.id.tvProductName)
        tvProductName.text = orderProduct.product.name
        val tvProductQuantity: TextView = view.findViewById(R.id.tvProductPrize)
        tvProductQuantity.text = orderProduct.quantity.toString()

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true
}