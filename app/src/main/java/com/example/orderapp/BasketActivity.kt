package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.orderapp.fragments.ProductFragment
import com.example.orderapp.types.Guest

class BasketActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.text = "Table Number\n${Guest.table}"
        btnTable.setOnClickListener {
            //Switch to signin activity
            val signinActivit = Intent(this, SigninActivity::class.java)
            startActivity(signinActivit)
        }

        val btnMenu: Button = findViewById(R.id.btnMenu)
        btnMenu.setOnClickListener {
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }

        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            //TODO Submit order
            //TODO save receipt to gallery
            OrderDb.submitOrder()
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }

        val tvTotal: TextView = findViewById(R.id.tvTotal)
        tvTotal.text = "Total prize: ${Guest.totalPrice()} - Number of products: ${Guest.totalCount()}"

//        val fragmentManager = supportFragmentManager
//        fragmentManager.beginTransaction().replace(R.id.categoryContainer, ProductFragment.newInstance()).commit()
    }
}