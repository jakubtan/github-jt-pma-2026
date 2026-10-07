package com.example.myapp001a_dicethrowxml

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Zajištění, aby obsah nepřekrývaly systémové lišty
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")
        val tvDice = findViewById<TextView>(R.id.tvDice)
        val btnRoll = findViewById<Button>(R.id.btnRoll)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnRoll.setOnClickListener {
            lifecycleScope.launch {
                // Zakázání tlačítka během animace
                btnRoll.isEnabled = false

                // Informace pro uživatele
                tvResult.text = "Rolling..."

                // 10 náhodných změn s fixní prodlevou 250 ms
                repeat(times = 10) {
                    tvDice.text = diceSymbols.random()
                    delay(timeMillis = 250)
                }

                // Výsledný hod a zobrazení textu
                val finalIndex = diceSymbols.indices.random()
                tvDice.text = diceSymbols[finalIndex]

                val finalNumber = finalIndex + 1
                tvResult.text = "You rolled a $finalNumber!"

                // Povolení tlačítka po dokončení animace
                btnRoll.isEnabled = true

            }
        }
    }
}