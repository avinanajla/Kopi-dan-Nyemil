package com.example.kopidannyemil

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(android.R.id.content)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Get user name passed from LoginActivity
        val userName = intent.getStringExtra("EXTRA_USER_NAME") ?: "Nama"
        val tvGreeting = findViewById<TextView>(R.id.tvHomeGreeting)
        tvGreeting.text = "Halo $userName,"

        // Setup RecyclerView with 5 dummy products matching wireframe
        val rvFoodList = findViewById<RecyclerView>(R.id.rvFoodList)
        rvFoodList.layoutManager = LinearLayoutManager(this)

        val foodList = listOf(
            FoodItem("Kopi Susu Gula Aren", "• Espresso, susu, gula aren", "• Rp 22.000"),
            FoodItem("Donat Cokelat", "• Donat empuk, glaze cokelat", "• Rp 12.000"),
            FoodItem("Sourdough Loaf", "• Roti fermentasi alami", "• Rp 35.000"),
            FoodItem("Cappuccino", "• Espresso, foam susu lembut", "• Rp 25.000"),
            FoodItem("Matcha Latte", "• Matcha Jepang, susu segar", "• Rp 27.000")
        )

        rvFoodList.adapter = FoodAdapter(foodList)

        // Bottom Navigation setup
        val navOrder = findViewById<TextView>(R.id.navOrder)
        val navProfile = findViewById<TextView>(R.id.navProfile)

        navOrder.setOnClickListener {
            val intent = Intent(this, OrderAddressActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", userName)
            startActivity(intent)
        }

        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            intent.putExtra("EXTRA_USER_NAME", userName)
            startActivity(intent)
        }
    }
}
