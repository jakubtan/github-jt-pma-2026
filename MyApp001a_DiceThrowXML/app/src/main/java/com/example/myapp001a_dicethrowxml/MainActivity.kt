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

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.llMain)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val diceSymbols = listOf("⚀", "⚁", "⚂", "⚃", "⚄", "⚅")

        // Načtení obou kostek z rozvržení
        val tvDice1 = findViewById<TextView>(R.id.tvDice1)
        val tvDice2 = findViewById<TextView>(R.id.tvDice2)
        val btnRoll = findViewById<Button>(R.id.btnRoll)
        val tvResult = findViewById<TextView>(R.id.tvResult)

        btnRoll.setOnClickListener {
            lifecycleScope.launch {
                btnRoll.isEnabled = false
                tvResult.text = "Rolling..."

                // Animace obou kostek současně
                repeat(times = 10) {
                    tvDice1.text = diceSymbols.random()
                    tvDice2.text = diceSymbols.random()
                    delay(timeMillis = 250)
                }

                // Finální hod pro první kostku
                val finalIndex1 = diceSymbols.indices.random()
                tvDice1.text = diceSymbols[finalIndex1]
                val value1 = finalIndex1 + 1

                // Finální hod pro druhou kostku
                val finalIndex2 = diceSymbols.indices.random()
                tvDice2.text = diceSymbols[finalIndex2]
                val value2 = finalIndex2 + 1

                // Výpočet celkového součtu
                val totalSum = value1 + value2
                tvResult.text = "Total score: $totalSum"

                btnRoll.isEnabled = true
            }
        }
    }
}