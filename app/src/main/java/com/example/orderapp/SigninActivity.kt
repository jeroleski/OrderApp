package com.example.orderapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)

        val numTable: EditText = findViewById(R.id.numTable)
        val btn: Button = findViewById(R.id.btnNext)
        btn.setOnClickListener {
            //Acquire table number
            Guest.table = numTable.text.toString().toUInt()
            //Switch to menu activity
            val menuIntent = Intent(this, MenuActivity::class.java)
            startActivity(menuIntent)
        }
    }
}
