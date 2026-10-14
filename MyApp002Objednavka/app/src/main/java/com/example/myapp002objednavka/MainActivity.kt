package com.example.myapp002objednavka

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myapp002objednavka.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    // 1. Binding - deklarace binding objektu s odloženou inicializací
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Binding - nafouknutí (inflate) layoutu do binding instance
        binding = ActivityMainBinding.inflate(layoutInflater)

        // 3. Binding - Nastavení kořenového pohledu (root) do okna aktivity
        setContentView(binding.root)

        //setContentView(R.layout.activity_main)
        // Ošetření systémových lišt – použije se přímo binding.main nebo binding.root
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 4. Binding - přímý a bezpečný přístup k prvkům layoutu přes binding.<id>
        binding.btnOrder.setOnClickListener {
            // Zjistí vybraný RadioButton ze skupiny rgBikes; pokud není vybraný žádný, jako výchozí se použije rbBike1
            val beer = when (binding.rgBeers.checkedRadioButtonId) {
                binding.rbBeer1.id -> binding.rbBeer1 // Pokud sedí ID prvního tlačítka, použij rbBike1
                binding.rbBeer2.id -> binding.rbBeer2
                binding.rbBeer3.id -> binding.rbBeer3

                else -> binding.rbBeer1   // Záložní možnost (fallback), kdyby nebylo vybráno nic
            }

            val chips = binding.cbChips.isChecked
            val pickled_sausage = binding.cbPickledSausage.isChecked
            val pickled_camembert = binding.cbPickledCamembert.isChecked

            val orderText = "Order summary: " + "${beer.text}" +
                    (if(chips) "; Chips" else "") +
                    (if(pickled_sausage) "; Pickled sausage" else "") +
                    (if(pickled_camembert) "; Pickled Camembert" else "")

            binding.tvOrder.text = orderText

            Toast.makeText(
                this,
                "The order of ${beer.text} was successfully sent!", Toast.LENGTH_SHORT
            ).show()

            // Změna obrázku v závislosti na vybraném radiobuttonu

            binding.rbBeer1.setOnClickListener {
                binding.ivBeer.setImageResource(R.drawable.koz_10)
            }

            binding.rbBeer2.setOnClickListener {
                binding.ivBeer.setImageResource(R.drawable.koz_11)
            }

            binding.rbBeer3.setOnClickListener {
                binding.ivBeer.setImageResource(R.drawable.bran_10)
            }
        }
    }
}