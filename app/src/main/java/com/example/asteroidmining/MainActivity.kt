package com.example.asteroidmining

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton
import kotlin.math.PI

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

        var previousValue = 50f

        binding.sliderRadius.addOnChangeListener { slider, value, fromUser ->
            if (value != previousValue) {

            }
            var radius: Int = binding.sliderRadius.value.toInt()
            var volume: Int = ((4 / 3) * PI * (radius * radius * radius)).toInt()
            binding.tvNVolumeElement.text = volume.toString()
            binding.tvSelectRadius.text = radius.toString()
            previousValue = value
        }

        listenersSliders()
    }

    private fun listenersSliders() {
        var previousValue = 50f
        var previousValue2 = 50f

        binding.sliderRadius.addOnChangeListener { slider, value, fromUser ->
            var radius: Int = binding.sliderRadius.value.toInt()
            var volume: Int = ((4 / 3) * PI * (radius * radius * radius)).toInt()
            binding.tvNVolumeElement.text = volume.toString()
            binding.tvSelectRadius.text = radius.toString()
        }

        binding.sliderPurity.addOnChangeListener { slider, value, fromUser ->
            var purity: Int = binding.sliderPurity.value.toInt()
            binding.tvSelectPurity.text = purity.toString()
        }
    }

    private fun listenersButtons() {

        binding.bSelectElementAgain.setOnClickListener {
            binding.linlaySelected.visibility = View.GONE
            binding.gbtnElements.visibility = View.VISIBLE
            binding.gbtnElements2.visibility = View.VISIBLE
            binding.gbtnElements3.visibility = View.VISIBLE
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

             binding.tvNDensityElement.text = density.toString()

             binding.gbtnElements.visibility = View.GONE
             binding.gbtnElements2.visibility = View.GONE
             binding.gbtnElements3.visibility = View.GONE
             binding.linlaySelected.visibility = View.VISIBLE

             val mass = calculateMassRadiusVolume(density)

             binding.tvNPrice.text = price.toString()
             recalculatePrice(price, mass)
        }
    }

    private fun calculateMassRadiusVolume(density : Double) : Double {
        val density = density
        var radius: Int = binding.sliderRadius.value.toInt()
        var volume: Int = ((4 / 3) * PI * (radius * radius * radius)).toInt()
        binding.tvNVolumeElement.text = volume.toString()
        var mass: Double = density * volume
        binding.tvNMassElement.text = mass.toString()
        return mass;
    }

    private fun recalculatePrice(price : Double, mass : Double) {
        if (checkValidData()){
            var purity = binding.sliderPurity.value.toDouble()
            purity /= 100
            var total = (price*mass*purity).toLong()

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
                !binding.tvNVolumeElement.equals(R.string.temp)
}