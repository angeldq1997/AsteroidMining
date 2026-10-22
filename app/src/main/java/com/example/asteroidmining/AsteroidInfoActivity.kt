package com.example.asteroidmining

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.asteroidmining.databinding.ActivityAsteroidInfoBinding

class AsteroidInfoActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAsteroidInfoBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsteroidInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        binding.bBackInfo.setOnClickListener {
            Toast.makeText(this, R.string.going_home, Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.mbTypeC.setOnClickListener {
            binding.tvAsteroids
        }
    }


}