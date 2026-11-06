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

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val etName = findViewById<EditText>(R.id.etRegisterName)
        val etUsername = findViewById<EditText>(R.id.etRegisterUsername)
        val etPassword = findViewById<EditText>(R.id.etRegisterPassword)
        val btnRegisterSubmit = findViewById<Button>(R.id.btnRegisterSubmit)

        btnRegisterSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Mohon isi semua field dengan lengkap!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save credentials to SharedPreferences
            val sharedPrefs = getSharedPreferences("kopi_nyemil_prefs", Context.MODE_PRIVATE)
            sharedPrefs.edit().apply {
                putString("key_name", name)
                putString("key_username", username)
                putString("key_password", password)
                apply()
            }

            Toast.makeText(this, "Registrasi berhasil! Silakan login.", Toast.LENGTH_SHORT).show()

            // Navigate to Login User activity
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("EXTRA_USERNAME", username)
            startActivity(intent)
            finish()
        }
    }
}
