package com.example.kopidannyemil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
        val savedName = sharedPrefs.getString("key_name", "Nama") ?: "Nama"

        val tvGreeting = findViewById<TextView>(R.id.tvProfileGreeting)
        val etName = findViewById<EditText>(R.id.etProfileName)
        val btnSave = findViewById<Button>(R.id.btnProfileSave)

        tvGreeting.text = "Halo $savedName,"
        etName.setText(savedName)

        btnSave.setOnClickListener {
            val newName = etName.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(this, "Nama tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save updated name to SharedPreferences
            sharedPrefs.edit().putString("key_name", newName).apply()
            Toast.makeText(this, "Profile berhasil disimpan!", Toast.LENGTH_SHORT).show()

            // Navigate back to Home with updated name
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", newName)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        // Bottom Navigation
        val navHome = findViewById<TextView>(R.id.navHome)
        val navOrder = findViewById<TextView>(R.id.navOrder)

        navHome.setOnClickListener {
            val name = etName.text.toString().trim().ifEmpty { savedName }
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", name)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        navOrder.setOnClickListener {
            val name = etName.text.toString().trim().ifEmpty { savedName }
            val intent = Intent(this, OrderAddressActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", name)
            startActivity(intent)
            finish()
        }
    }
}
