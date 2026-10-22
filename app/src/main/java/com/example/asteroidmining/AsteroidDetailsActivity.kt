package com.example.asteroidmining

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityAsteroidDetailsBinding

class AsteroidDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAsteroidDetailsBinding
    companion object{
        const val PARAMETER_DENSITY = "parameter_density"
        const val PARAMETER_MASS ="parameter_mass"
        const val PARAMETER_VOLUME ="parameter_volume"
        const val PARAMETER_PRICE ="parameter_price"
        const val PARAMETER_PURITY ="parameter_purity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsteroidDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setComponents()
        setListeners()
    }

    private fun setComponents() {
        binding.tvNPrice.text = PARAMETER_PRICE
        binding.tvNDensityElement.text = PARAMETER_DENSITY
        binding.tvNVolumeElement.text = PARAMETER_VOLUME
        binding.tvMass.text = PARAMETER_MASS
        recalculatePrice()
    }

    private fun recalculatePrice() {
        if (checkValidData()){
            var purity = PARAMETER_PURITY.toDouble()
            purity /= 100.0
            var total = (PARAMETER_PRICE.toDouble()*PARAMETER_MASS.toDouble()*purity).toLong()

            when (total){
                in 1..999999 -> {
                    binding.tvNTotal.text = total.toString()
                }

                in 999999..999999999 -> {
                    total /= 1000000
                    binding.tvFinalPrice.setText(R.string.total_m)
                    binding.tvNTotal.text = total.toString()
                }

                in 999999999..999999999999 -> {
                    total /= 1000000000
                    binding.tvFinalPrice.setText(R.string.total_b)
                    binding.tvNTotal.text = total.toString()
                }

                in 999999999999..999999999999999 -> {
                    total /= 1000000000000
                    binding.tvFinalPrice.setText(R.string.total_t)
                    binding.tvNTotal.text = total.toString()
                }
                else -> {
                    val toast = Toast.makeText(this,R.string.too_large, Toast.LENGTH_LONG)
                    toast.show()
                    binding.tvNTotal.setText(R.string.exceeded)
                }

            }
            binding.tvNTotal.text = total.toString()

        }
    }

    private fun checkValidData(): Boolean =
        !binding.tvNDensityElement.equals(R.string.temp) &&
                !binding.tvNMassElement.equals(R.string.temp) &&
                binding.tvNVolumeElement.equals(R.string.temp) &&
                binding.tvNPrice.equals(R.string.temp)

    private fun setListeners() {
        binding.bBackDetails.setOnClickListener {
            Toast.makeText(this, R.string.going_home, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}