package com.example.projectaplikasi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputBinding
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectaplikasi.databinding.ActivityMainBinding
import com.google.android.gms.common.internal.Objects
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var workoutRecyclerView: RecyclerView
    private lateinit var workoutArrayList : ArrayList<Workout>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        workoutRecyclerView = findViewById(R.id.view1)
        workoutRecyclerView.layoutManager = LinearLayoutManager(this)
        workoutRecyclerView.setHasFixedSize(true)

        workoutArrayList = arrayListOf<Workout>()
        getUserData()

        val navBottom: BottomNavigationView = findViewById(R.id.navBottom)
        navBottom.selectedItemId = R.id.nav_home

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Already on this Activity
                R.id.nav_map -> {
                    startActivity(Intent(this, MapActivity::class.java))
                    true
                }
                R.id.nav_kalkulator -> {
                    startActivity(Intent(this, CalculatorActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }




    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("Workout")

        workoutArrayList.clear()

        dbref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (workoutSnapshot in snapshot.children){
                        val workout = workoutSnapshot.getValue(Workout::class.java)
                        workout?.let {
                            workoutArrayList.add(it)
                        }
                    }
                    workoutRecyclerView.adapter = WorkoutAdapter(workoutArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("FirebaseError", "Error: ${error.message}")
                Toast.makeText(
                    this@MainActivity,
                    "Failed to fetch data: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }


        })
    }
}