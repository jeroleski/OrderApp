package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.fragments.OrderListViewAdapter
import com.example.orderapp.network.OrderDb

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.setOnClickListener {
            //Switch to signin activity
            val signinIntent = Intent(this, SigninActivity::class.java)
            startActivity(signinIntent)
        }

        val btnFilters: Button = findViewById(R.id.btnFilter)
        btnFilters.setOnClickListener {
            //Switch to filter activity
            val filterIntent = Intent(this, FilterActivity::class.java)
            startActivity(filterIntent)
        }

        val groupList = OrderDb.getFilteredOrders()
        val orderCollection = OrderDb.getFilteredOrders().associateBy({ o -> o.id.toString() }, { o -> o.products })
        val expandableListView: ExpandableListView = findViewById(R.id.orderList)
        val expandableListAdapter = OrderListViewAdapter(this, groupList, orderCollection)
        expandableListView.setAdapter(expandableListAdapter)
//        expandableListView.setOnChildClickListener(ExpandableListView.OnGroupClickListener {
//            override fun onGroup
//        })
        //TODO fill in ^^
      }
}