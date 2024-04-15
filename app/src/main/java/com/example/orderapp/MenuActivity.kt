package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ExpandableListView
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.fragments.MenuListViewAdapter
import com.example.orderapp.network.MenuDb
import com.example.orderapp.types.Guest

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.text = getString(R.string.btn_tableNumber, Guest.table)
        btnTable.setOnClickListener {
            //Switch to signin activity
            val signinActivity = Intent(this, SigninActivity::class.java)
            startActivity(signinActivity)
        }

        val btnBasket: Button = findViewById(R.id.btnBasket)
        btnBasket.setOnClickListener {
            //Switch to basket activity
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
        }

        val groupList = MenuDb.menu.categories
        val orderCollection = MenuDb.menu.categories.associateBy( { c -> c.name}, { c -> c.products})
        val expandableListView: ExpandableListView = findViewById(R.id.orderList)
        val expandableListAdapter = MenuListViewAdapter(this, groupList, orderCollection)
        expandableListView.setAdapter(expandableListAdapter)
//        expandableListView.setOnChildClickListener(ExpandableListView.OnGroupClickListener {
//            override fun onGroup
//        })
        //TODO fill in ^^
    }
}