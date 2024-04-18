package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.fragments.OrderListViewAdapter
import com.example.orderapp.types.Waiter

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.setOnClickListener {
            //Switch to table activity
            val tableIntent = Intent(this, TableActivity::class.java)
            startActivity(tableIntent)
        }

        val btnFilters: Button = findViewById(R.id.btnFilter)
        btnFilters.setOnClickListener {
            //Switch to filter activity
            val filterIntent = Intent(this, FilterActivity::class.java)
            startActivity(filterIntent)
        }

        val groupList = Waiter.getFilteredOrders()
        val orderCollection = Waiter.getFilteredOrders().associateBy({ o -> o.documentId }, { o -> o.orderProducts })
        val expandableListView: ExpandableListView = findViewById(R.id.listOrders)
        val expandableListAdapter = OrderListViewAdapter(this, groupList, orderCollection)
        expandableListView.setAdapter(expandableListAdapter)
        var lastExpandedPosition = -1
        expandableListView.setOnGroupExpandListener { i ->
            if (lastExpandedPosition != -1 && i != lastExpandedPosition)
                expandableListView.collapseGroup(lastExpandedPosition)
            lastExpandedPosition = i
        }
    }
}