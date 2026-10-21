package com.example.asteroidmining

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.asteroidmining.databinding.ActivityMainBinding
import kotlin.math.PI
import kotlin.math.pow

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
        binding.bSelectElementAgain.setOnClickListener {
            binding.linlaySelected.visibility = View.GONE
            binding.gbtnElements.visibility = View.VISIBLE
            binding.gbtnElements2.visibility = View.VISIBLE
            binding.gbtnElements3.visibility = View.VISIBLE
        }

        elementButtons()
    }

    private fun elementButtons() {
        configButton(binding.bMagnesium, R.string.e_density_magnesium.toString(), R.string.e_price_magnesium.toString(), R.drawable.mg)
        configButton(binding.bAluminium, R.string.e_density_aluminium.toString(), R.string.e_price_aluminium.toString(), R.drawable.al)
        configButton(binding.bSilicon, R.string.e_density_silicon.toString(), R.string.e_price_silicon.toString(), R.drawable.si)
        configButton(binding.bTitanium, R.string.e_density_titanium.toString(), R.string.e_price_titanium.toString(), R.drawable.ti)
        configButton(binding.bChromium, R.string.e_density_chromium.toString(), R.string.e_price_chromium.toString(), R.drawable.cr)
        configButton(binding.bIron, R.string.e_density_iron.toString(), R.string.e_price_iron.toString(), R.drawable.fe)
        configButton(binding.bCobalt, R.string.e_density_cobalt.toString(), R.string.e_price_cobalt.toString(), R.drawable.co)
        configButton(binding.bNickel, R.string.e_density_nickel.toString(), R.string.e_price_nickel.toString(), R.drawable.ni)
        configButton(binding.bCopper, R.string.e_density_copper.toString(), R.string.e_price_copper.toString(), R.drawable.cu)
        configButton(binding.bSilver, R.string.e_density_silver.toString(), R.string.e_price_silver.toString(), R.drawable.ag)
        configButton(binding.bGold, R.string.e_density_gold.toString(), R.string.e_price_gold.toString(), R.drawable.au)
        configButton(binding.bTungsten, R.string.e_density_tungsten.toString(), R.string.e_price_tungsten.toString(), R.drawable.w)

    }

    private fun configButton(button: Button, density : String, price : String, image : Int) {
         button.setOnClickListener{
             binding.gbtnElements.visibility = View.GONE
             binding.gbtnElements2.visibility = View.GONE
             binding.gbtnElements3.visibility = View.GONE
             binding.linlaySelected.visibility = View.VISIBLE

             binding.tvNDensityElement.text = density
             var radius : Double = binding.sliderRadius.value.toDouble()
             var volume : Double = ((4.0/3.0) * PI * radius.pow(3.0))
             binding.tvNVolumeElement.text = volume.toString()
             var mass : Double = (density.toDouble() * volume)
             binding.tvNMassElement.text = mass.toString()
             binding.tvNPrice.text = price

             binding.ivElement.setImageResource(image)
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