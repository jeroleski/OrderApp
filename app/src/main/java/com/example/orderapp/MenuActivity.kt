package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnTable: Button = findViewById(R.id.btnTable)
        btnTable.text = "Table Number\n${Guest.table}"
        btnTable.setOnClickListener {
            //Switch to signin activity
            val signinActivit = Intent(this, SigninActivity::class.java)
            startActivity(signinActivit)
        }

        val btnBasket: Button = findViewById(R.id.btnBasket)
        btnBasket.setOnClickListener {
            //Switch to basket activity
            val basketIntent = Intent(this, BasketActivity::class.java)
            startActivity(basketIntent)
        }


        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.productContainer, ProductFragment()).commit()

    }
}