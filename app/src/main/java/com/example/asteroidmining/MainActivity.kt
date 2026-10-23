package com.example.asteroidmining

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import kotlin.math.PI

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private var density : Double = 0.0
    private var mass : Double = 0.0
    private var volume : Double = 0.0
    private var price : Double = 0.0
    private var radius : Double = 500.0
    private var purity : Double = 50.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()
    }

    private fun setListeners() {
        listenersButtons()
        listenersSliders()
    }

    private fun listenersSliders() {
        var previousValue = 50f
        var previousValue2 = 50f

        binding.sliderRadius.addOnChangeListener { slider, value_x, fromUser ->
            if (value_x != previousValue) {
                radius = binding.sliderRadius.value.toDouble()
                volume = ((4 / 3) * PI * (radius * radius * radius))
                binding.tvSelectRadius.text = radius.toString()
                binding.tvNVolumeElement.text = volume.toString()
                recalculateMass()
                recalculatePrice()
                previousValue = value_x
            }
        }

        binding.sliderPurity.addOnChangeListener { slider, value_y, fromUser ->
            if (value_y != previousValue2) {
                purity = binding.sliderPurity.value.toDouble()
                binding.tvSelectPurity.text = purity.toString()
                recalculateMass()
                recalculatePrice()
                previousValue2 = value_y
            }
        }
    }

    private fun listenersButtons() {

        binding.bSelectElementAgain.setOnClickListener {
            binding.linlaySelected.visibility = View.GONE
            binding.dataCard.visibility = View.GONE
            binding.gbtnElements.visibility = View.VISIBLE
            binding.gbtnElements2.visibility = View.VISIBLE
            binding.gbtnElements3.visibility = View.VISIBLE
        }

        binding.bSeeDetails.setOnClickListener {
            Toast.makeText(this, R.string.open_details, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AsteroidDetailsActivity::class.java).apply {
                intent.putExtra(AsteroidDetailsActivity.PARAMETER_MASS, mass)
                intent.putExtra(AsteroidDetailsActivity.PARAMETER_VOLUME, volume)
                intent.putExtra(AsteroidDetailsActivity.PARAMETER_PRICE, price)
                intent.putExtra(AsteroidDetailsActivity.PARAMETER_PURITY, purity)
                intent.putExtra(AsteroidDetailsActivity.PARAMETER_DENSITY, density)
            }
            startActivity(intent)
        }

        binding.bSeeInfo.setOnClickListener {
            Toast.makeText(this, R.string.open_info, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, AsteroidInfoActivity::class.java).apply { }
            startActivity(intent)
        }

        elementButtons()
    }

    private fun elementButtons() {
        configButton(binding.bMagnesium, 1.738, 1.79, R.drawable.mg)
        configButton(binding.bAluminium, 2.698 , 2.32 , R.drawable.al)
        configButton(binding.bSilicon, 2.3296, 1.70, R.drawable.si)
        configButton(binding.bTitanium,4.5 , 11.1, R.drawable.ti)
        configButton(binding.bChromium, 7.15,9.4 , R.drawable.cr)
        configButton(binding.bIron, 7.874, 0.424, R.drawable.fe)
        configButton(binding.bCobalt, 8.86,32.8 , R.drawable.co)
        configButton(binding.bNickel, 8.912, 13.9, R.drawable.ni)
        configButton(binding.bCopper,8.933 , 6.00, R.drawable.cu)
        configButton(binding.bSilver, 10.501,521.0 , R.drawable.ag)
        configButton(binding.bGold, 19.282, 75430.0, R.drawable.au)
        configButton(binding.bTungsten, 19.3, 35.3, R.drawable.w)

    }

    private fun configButton(button: MaterialButton, density : Double, price : Double, image : Int) {
         button.setOnClickListener{
             binding.ivElement.setImageResource(image)
             binding.gbtnElements.visibility = View.GONE
             binding.gbtnElements2.visibility = View.GONE
             binding.gbtnElements3.visibility = View.GONE
             binding.linlaySelected.visibility = View.VISIBLE
             binding.dataCard.visibility = View.VISIBLE

             this.density = density
             this.price = price

             recalculateMass()
             recalculatePrice()
        }
    }

    private fun recalculateMass() {
        binding.tvNDensityElement.text = this.density.toString()
        binding.tvNPrice.text = this.price.toString()

        this.volume = ((4 / 3) * PI * (radius * radius * radius))
        this.mass = this.density * this.volume
        binding.tvNVolumeElement.text = this.volume.toString()
        binding.tvNMassElement.text = this.mass.toString()
    }

    private fun recalculatePrice() {
        if (checkValidData()){
            this.purity = binding.sliderPurity.value.toDouble()
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
                !binding.tvNVolumeElement.equals(R.string.temp) &&
                !binding.tvNPrice.equals(R.string.temp)
}