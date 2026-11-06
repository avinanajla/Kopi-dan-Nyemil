package com.example.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_confirmation)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val userName = intent.getStringExtra("EXTRA_USER_NAME") ?: run {
            val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
            sharedPrefs.getString("key_name", "Nama") ?: "Nama"
        }

        val tvGreeting = findViewById<TextView>(R.id.tvConfirmGreeting)
        tvGreeting.text = "Halo $userName,"

        val btnConfirmOk = findViewById<Button>(R.id.btnConfirmOk)
        btnConfirmOk.setOnClickListener {
            // Return back to HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", userName)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finishAffinity()
        }
    }
}
