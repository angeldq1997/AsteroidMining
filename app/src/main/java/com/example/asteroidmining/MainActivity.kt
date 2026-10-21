package com.example.asteroidmining

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        listenersButtons()
    }

    private fun listenersButtons() {
        binding.gbtnElements.setOnClickListener {
            binding.gbtnElements.visibility = View.GONE
            binding.gbtnElements2.visibility = View.GONE
            binding.gbtnElements3.visibility = View.GONE
            binding.linlaySelected.visibility = View.VISIBLE
        }

        binding.gbtnElements2.setOnClickListener {
            binding.gbtnElements.visibility = View.GONE
            binding.gbtnElements2.visibility = View.GONE
            binding.gbtnElements3.visibility = View.GONE
            binding.linlaySelected.visibility = View.VISIBLE
        }

        binding.gbtnElements3.setOnClickListener {
            binding.gbtnElements.visibility = View.GONE
            binding.gbtnElements2.visibility = View.GONE
            binding.gbtnElements3.visibility = View.GONE
            binding.linlaySelected.visibility = View.VISIBLE
        }

        binding.bSelectElementAgain.setOnClickListener {
            binding.linlaySelected.visibility = View.GONE
            binding.gbtnElements.visibility = View.VISIBLE
            binding.gbtnElements2.visibility = View.VISIBLE
            binding.gbtnElements3.visibility = View.VISIBLE
        }

        configButton(binding.bMagnesium, R.string.e_letter_magnesium.toString())
    }

    private fun configButton(button: Button, density : String) {
         button.setOnClickListener{
             binding.tvNDensityElement.text = density
             recalculatePrice()
        }
    }

    private fun recalculatePrice() {
        if (checkValidData()){
            var price : Double = binding.tvNPrice.toString().toDouble()
            var mass : Double = binding.tvNMassElement.toString().toDouble()
            var total : Double = price*mass
            when (total){
                in 1.0..999999.99 -> {
                    binding.tvNTotal.text = total.toString()
                }

                in 999999.99..9999999.99 -> {
                    total /= 1000.0
                    var totalText : String = "$total M"
                    binding.tvNTotal.text = totalText
                }

                in 9999999.99..999999999.99 -> {
                    total /= 1000000.0
                    var totalText : String = "$total B"
                    binding.tvNTotal.text = totalText
                }

                in 999999999.99..999999999999.99 -> {
                    total /= 1000000.0
                    var totalText : String = "$total T"
                    binding.tvNTotal.text = totalText
                }
                else -> binding.tvNTotal.setText(R.string.exceeded)
            }
            binding.tvNTotal.text = total.toString()

        }
    }

    private fun checkValidData(): Boolean =
        !binding.tvNDensityElement.equals(R.string.temp) &&
                !binding.tvNMassElement.equals(R.string.temp) &&
                !binding.tvNVolumeElement.equals(R.string.temp)
}