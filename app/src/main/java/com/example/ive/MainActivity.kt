package com.example.ive

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bt = findViewById<TextView>(R.id.text)
        bt.setOnClickListener {
            Toast.makeText(this,"Massage",Toast.LENGTH_SHORT).show()
        }
    }
}