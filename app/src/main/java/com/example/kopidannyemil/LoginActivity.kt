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

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etUsername = findViewById<EditText>(R.id.etLoginUsername)
        val etPassword = findViewById<EditText>(R.id.etLoginPassword)
        val btnLoginSubmit = findViewById<Button>(R.id.btnLoginSubmit)

        // Pre-fill username if passed from registration
        val passedUsername = intent.getStringExtra("EXTRA_USERNAME")
        if (!passedUsername.isNullOrEmpty()) {
            etUsername.setText(passedUsername)
        }

        btnLoginSubmit.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon masukkan username dan password!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
            val savedUsername = sharedPrefs.getString("key_username", null)
            val savedPassword = sharedPrefs.getString("key_password", null)
            val savedName = sharedPrefs.getString("key_name", "Nama")

            // Validate against saved data (or fallback default admin/admin for testing convenience)
            val isValid = (username == savedUsername && password == savedPassword) ||
                    (savedUsername == null && username == "admin" && password == "admin")

            if (isValid) {
                val displayName = if (username == savedUsername) savedName else username
                Toast.makeText(this, "Login berhasil, Halo $displayName!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("EXTRA_USER_NAME", displayName ?: "Nama")
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Username atau password salah!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
