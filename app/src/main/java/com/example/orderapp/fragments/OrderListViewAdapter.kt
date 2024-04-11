package com.example.orderapp.fragments

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import com.example.orderapp.R

class OrderListViewAdapter(
    val context: Context,
    val groupList: List<String>,
    val orderCollection: Map<String, List<String>>)
    : BaseExpandableListAdapter() {

    override fun getGroupCount() = orderCollection.size

    override fun getChildrenCount(groupPosition: Int) = orderCollection[groupList[groupPosition]]?.size ?: -1

    override fun getGroup(groupPosition: Int) = groupList[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int) = orderCollection[groupList[groupPosition]]?.get(childPosition) ?: ""

    override fun getGroupId(groupPosition: Int) = groupPosition.toLong()

    override fun getChildId(groupPosition: Int, childPosition: Int) = childPosition.toLong()

    override fun hasStableIds() = true

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val groupName = getGroup(groupPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.group_item, null)
        }

        //TODO inflate correctly
        val tvProductName: TextView = view.findViewById(R.id.order)
        tvProductName.text = groupName

        return view
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        val model = getChild(groupPosition, childPosition)

        val view: View = if (convertView != null) convertView
        else {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            inflater.inflate(R.layout.child_item, null)
        }

        //TODO inflate correctly
        val tvProductName: TextView = view.findViewById(R.id.model)
        tvProductName.text = model

        //TODO popup action handling

        return view
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int) = true
}