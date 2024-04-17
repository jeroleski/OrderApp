package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.fragments.MenuListViewAdapter
import com.example.orderapp.network.DbWrapper
import com.example.orderapp.types.Guest

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.text = getString(R.string.btn_tableNumber, Guest.table)
        btnTable.setOnClickListener {
            //Switch to table activity
            val tableIntent = Intent(this, TableActivity::class.java)
            startActivity(tableIntent)
        }

        val btnBasket: Button = findViewById(R.id.btnFilter)
        btnBasket.setOnClickListener {
            //Switch to basket activity
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
        }

        val groupList = DbWrapper.menu.categories
        val menuCollection = DbWrapper.menu.categories.associateBy({ c -> c.name }, { c -> c.products })
        val expandableListView: ExpandableListView = findViewById(R.id.listMenu)
        val expandableListAdapter = MenuListViewAdapter(this, groupList, menuCollection)
        expandableListView.setAdapter(expandableListAdapter)
        var lastExpandedPosition = -1
        expandableListView.setOnGroupExpandListener { i ->
            if (lastExpandedPosition != -1 && i != lastExpandedPosition)
                expandableListView.collapseGroup(lastExpandedPosition)
            lastExpandedPosition = i
        }
    }
}