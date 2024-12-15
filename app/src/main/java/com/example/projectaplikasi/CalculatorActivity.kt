package com.example.projectaplikasi

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalculatorActivity : AppCompatActivity() {
    private lateinit var edAge: EditText
    private lateinit var edKg: EditText
    private lateinit var edCm: EditText
    private lateinit var bmiResult: TextView
    private lateinit var btnCalc: Button
    private lateinit var btnReset: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        edAge = findViewById(R.id.edAge)
        edKg = findViewById(R.id.edKg)
        edCm = findViewById(R.id.edCm)
        bmiResult = findViewById(R.id.bmiResult)
        btnCalc = findViewById(R.id.BtnCalc)
        btnReset = findViewById(R.id.BtnReset)


        btnCalc.setOnClickListener {
            calculateBMI()
        }

        btnReset.setOnClickListener {
            resetFields()
        }

        val navBottom: BottomNavigationView = findViewById(R.id.navBottom)
        navBottom.selectedItemId = R.id.nav_kalkulator // Set active item

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.nav_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    true
                }
                R.id.nav_kalkulator -> true // Already on this Activity
                else -> false
            }
        }
    }

    private fun calculateBMI() {
        val ageStr = edAge.text.toString().trim()
        val kgStr = edKg.text.toString().trim()
        val cmStr = edCm.text.toString().trim()

        // Validate inputs
        if (ageStr.isEmpty() || kgStr.isEmpty() || cmStr.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val age = ageStr.toInt()
            val weight = kgStr.toFloat()
            val height = cmStr.toFloat() / 100 // Convert height to meters

            if (age <= 0 || weight <= 0 || height <= 0) {
                Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
                return
            }

            val bmi = weight / (height * height)

            bmiResult.text = String.format("Your BMI: %.2f", bmi)

        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetFields() {
        edAge.text.clear()
        edKg.text.clear()
        edCm.text.clear()
        bmiResult.text = ""
    }
}