package com.example.pairs.presentation.result

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pairs.R
import com.example.pairs.databinding.ActivityResultBinding
import com.example.pairs.presentation.game.GameActivity

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val reward = intent.getStringExtra(GameActivity.REWARD_EXTRA)
        binding.reward.text = reward

        val sharedPref = getSharedPreferences("data", MODE_PRIVATE)
        val balance = sharedPref.getInt("balance", 0)
        val currentBalance = balance + reward.toString().toInt()

        sharedPref.edit()
            .putInt("balance", currentBalance)
            .apply()



        binding.btnHome.setOnClickListener {
            finish()
        }
    }
}