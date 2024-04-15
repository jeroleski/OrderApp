package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orderapp.fragments.BasketRecyclerViewAdapter
import com.example.orderapp.types.Guest

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.text = getString(R.string.btn_tableNumber, Guest.table)
        btnTable.setOnClickListener {
            //Switch to table activity
            val tableIntent = Intent(this, TableActivity::class.java)
            startActivity(tableIntent)
        }

        val btnMenu: Button = findViewById(R.id.btnMenu)
        btnMenu.setOnClickListener {
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.listBasket)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = BasketRecyclerViewAdapter(applicationContext, Guest.order)

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            Guest.submitOrder()
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }

        val tvTotalPrize: TextView = findViewById(R.id.tvTotalPrize)
        tvTotalPrize.text = getString(R.string.basket_totalPrize, Guest.totalPrice())

        val tvTotalCount: TextView = findViewById(R.id.tvTotalCount)
        tvTotalCount.text = getString(R.string.basket_totalCount, Guest.totalCount())
    }
}