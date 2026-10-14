package com.example.myapp002objednavka

import android.os.Bundle
import android.widget.Toast
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

        // Ošetření systémových lišt – použije se přímo binding.main
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Změna obrázku v závislosti na vybraném radiobuttonu IHNED po překliknutí
        binding.rgBeers.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.rbBeer1.id -> binding.ivBeer.setImageResource(R.drawable.koz_10)
                binding.rbBeer2.id -> binding.ivBeer.setImageResource(R.drawable.koz_11)
                binding.rbBeer3.id -> binding.ivBeer.setImageResource(R.drawable.bran_10)
            }
        }

        // 4. Binding - přímý a bezpečný přístup k prvkům layoutu přes binding.<id>
        binding.btnOrder.setOnClickListener {
            // NOVÁ FUNKCE: Kontrola plnoletosti
            if (!binding.swAge.isChecked) {
                Toast.makeText(
                    this,
                    getString(R.string.toast_age_error),
                    Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener // Přeruší další vykonávání kódu
            }
            // Zjistí vybraný RadioButton ze skupiny rgBeers
            val beer = when (binding.rgBeers.checkedRadioButtonId) {
                binding.rbBeer1.id -> binding.rbBeer1
                binding.rbBeer2.id -> binding.rbBeer2
                binding.rbBeer3.id -> binding.rbBeer3
                else -> binding.rbBeer1   // Záložní možnost (fallback)
            }

            // Zjištění stavu checkboxů
            val hasChips = binding.cbChips.isChecked
            val hasSausage = binding.cbPickledSausage.isChecked
            val hasCamembert = binding.cbPickledCamembert.isChecked

            // Vytvoření textu objednávky s využitím přeložených textů
            val summaryTitle = getString(R.string.textview_order_summary_text)
            val orderText = "$summaryTitle ${beer.text}" +
                    (if(hasChips) "; ${binding.cbChips.text}" else "") +
                    (if(hasSausage) "; ${binding.cbPickledSausage.text}" else "") +
                    (if(hasCamembert) "; ${binding.cbPickledCamembert.text}" else "")

            binding.tvOrder.text = orderText

            // Zobrazení lokalizovaného Toastu s formátováním (%1$s = vybrané pivo)
            Toast.makeText(
                this,
                getString(R.string.toast_order_success, beer.text),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}