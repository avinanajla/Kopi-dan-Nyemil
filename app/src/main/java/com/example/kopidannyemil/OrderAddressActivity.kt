package com.example.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OrderAddressActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order_address)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val userName = intent.getStringExtra("EXTRA_USER_NAME") ?: run {
            val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
            sharedPrefs.getString("key_name", "Nama") ?: "Nama"
        }

        val etName = findViewById<EditText>(R.id.etAddressName)
        val etAddress = findViewById<EditText>(R.id.etAddressDetail)
        val etNotes = findViewById<EditText>(R.id.etAddressNotes)
        val btnOrderAndSend = findViewById<Button>(R.id.btnOrderAndSend)

        // Pre-fill name
        etName.setText(userName)

        btnOrderAndSend.setOnClickListener {
            val name = etName.text.toString().trim()
            val address = etAddress.text.toString().trim()
            val notes = etNotes.text.toString().trim()

            if (name.isEmpty() || address.isEmpty() || notes.isEmpty()) {
                Toast.makeText(this, "Semua kolom alamat wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save address temporarily using SharedPreferences
            val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
            sharedPrefs.edit().apply {
                putString("key_order_name", name)
                putString("key_order_address", address)
                putString("key_order_notes", notes)
                apply()
            }

            Toast.makeText(this, "Pesanan sedang diproses...", Toast.LENGTH_SHORT).show()

            // Navigate to OrderConfirmationActivity
            val intent = Intent(this, OrderConfirmationActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", name)
            startActivity(intent)
        }
    }
}
