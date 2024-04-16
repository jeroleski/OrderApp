package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.network.DbWrapper
import com.example.orderapp.network.MenuDb
import com.example.orderapp.network.OrderDb
import com.example.orderapp.network.menuMockData
import com.example.orderapp.types.Guest

class TableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        val numTable: EditText = findViewById(R.id.numTable)
        val btnNext: Button = findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            //Acquire table number
            Guest.table = numTable.text.toString().toIntOrNull() ?: 0
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }

        val btnServer: Button = findViewById(R.id.btnServer)
        btnServer.setOnClickListener {
            //Switch to menu activity
            val ordersIntent = Intent(this, OrdersActivity::class.java)
            startActivity(ordersIntent)
        }

        dbSetup()
    }

    private fun dbSetup() {
        val db = DbWrapper()
        db.initialize(this)

        for (category in menuMockData().categories) {
            for (product in category.products) {
                db.addMenuProduct(product, category.name)
            }
        }

        MenuDb.menu = db.getMenu()
        OrderDb.orders = db.getOrders()
    }
}
