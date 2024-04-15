package com.example.orderapp

import android.os.Bundle
import android.widget.ExpandableListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.orderapp.fragments.OrderListViewAdapter
import com.example.orderapp.network.MenuDb

class OrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_orders)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val groupList: List<String> = MenuDb.menu.categories.map { c -> c.name }
        val orderCollection: Map<String, List<String>> = MenuDb.menu.categories.associateBy( {c -> c.name}, {c -> c.products.map { p -> p.name }})
        val expandableListView: ExpandableListView = findViewById(R.id.orderList)
        val expandableListAdapter = OrderListViewAdapter(this, groupList, orderCollection)
        expandableListView.setAdapter(expandableListAdapter)
//        expandableListView.setOnChildClickListener(ExpandableListView.OnGroupClickListener {
//            override fun onGrou
//        })

      }
}