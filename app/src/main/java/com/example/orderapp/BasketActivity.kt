package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

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
            //Switch to basket activity
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
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
    }
}