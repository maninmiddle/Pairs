package com.example.pairs.presentation.menu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.pairs.R
import com.example.pairs.databinding.ActivityMenuBinding
import com.example.pairs.presentation.game.GameActivity

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getBalance()

        binding.btnPlay.setOnClickListener {
            val intent = Intent(this@MenuActivity, GameActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onResume() {
        super.onResume()
        getBalance()
    }

    private fun getBalance() {
        val sharedPref = getSharedPreferences("data", MODE_PRIVATE)
        val balance = sharedPref.getInt("balance", 0)
        binding.tvBalance.text = balance.toString()
    }
}