package com.example.asteroidmining

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityAsteroidDetailsBinding

class AsteroidDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAsteroidDetailsBinding
    companion object{
        const val PARAMETER_DENSITY = "parameter_density"
        const val PARAMETER_MASS = "parameter_mass"
        const val PARAMETER_VOLUME = "parameter_volume"
        const val PARAMETER_PRICE = "parameter_price"
        const val PARAMETER_PURITY = "parameter_purity"
    }
    private var density : Double = 0.0
    private var mass : Double = 0.0
    private var volume : Double = 0.0
    private var price : Double = 0.0
    private var purity : Double = 50.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsteroidDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setImage()
        setListeners()
    }

    private fun setImage() {
        if (volume > 1000000 ){
            binding.ivCatcher.setImageResource(R.drawable.a_catcher)
        }else{
            binding.ivCatcher.setImageResource(R.drawable.a_catcher_sheperd)
        }
    }

    private fun setData() {
        density = intent.getDoubleExtra("parameter_density", 0.0)
        mass = intent.getDoubleExtra("parameter_mass", 0.0)
        volume = intent.getDoubleExtra("parameter_volume", 0.0)
        price = intent.getDoubleExtra("parameter_price", 0.0)
        purity = intent.getDoubleExtra("parameter_purity", 0.0)

        binding.tvNDensityElement.text = density.toString()
        binding.tvNMassElement.text = mass.toString()
        binding.tvNVolumeElement.text = volume.toString()
        binding.tvNPrice.text = price.toString()
        recalculatePrice()
    }

    private fun recalculatePrice() {
        if (checkValidData()){
            this.purity /= 100.0
            var total = (this.price*this.mass*this.purity).toLong()

            when (total){
                in 1..999999 -> {
                    binding.tvNTotal.text = total.toString()
                }

                in 999999..999999999 -> {
                    total /= 1000000
                    binding.tvFinalPrice.setText(R.string.total_m)
                }

                in 999999999..999999999999 -> {
                    total /= 1000000000
                    binding.tvFinalPrice.setText(R.string.total_b)
                }

                in 999999999999..999999999999999 -> {
                    total /= 1000000000000
                    binding.tvFinalPrice.setText(R.string.total_t)
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