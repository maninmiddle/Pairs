package com.example.pairs.presentation.game

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.pairs.R
import com.example.pairs.databinding.ActivityGameBinding
import com.example.pairs.model.CardModel
import com.example.pairs.presentation.adapter.GameAdapter
import com.example.pairs.presentation.result.ResultActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameActivity : AppCompatActivity() {
    private lateinit var viewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private var reward = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = getSharedPreferences("data", MODE_PRIVATE)
        val balance = sharedPref.getInt("balance", 0)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[GameViewModel::class.java]

        binding.tvBalance.text = balance.toString()

        rvSetup()
        startTimer()
        checkTimerEnd()

    }

    fun getResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(REWARD_EXTRA, reward.toString())
        startActivity(intent)
        finish()
    }

    private fun checkTimerEnd() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.canTake.collect { canTake ->
                    if (canTake) {
                        while (reward > 10) {
                            delay(1000)
                            reward -= 5
                        }
                    }
                }
            }
        }
    }

    private fun startTimer() {
        viewModel.startTimer(20000)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.timerValue.collect { mills ->
                    val seconds = mills / 1000
                    val minutes = seconds / 60
                    binding.tvTimer.text = getString(R.string.stringTimeFormat, minutes, seconds)
                }
            }
        }
    }

    private fun rvSetup() {
        viewModel.generateCards()

        val gameAdapter = GameAdapter(this@GameActivity)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cards.collect { cards ->
                    gameAdapter.submitList(cards)
                }
            }
        }

        binding.rvLayout.layoutManager = GridLayoutManager(this, 4)
        binding.rvLayout.adapter = gameAdapter
    }

    companion object {
        const val REWARD_EXTRA = "REWARD_EXTRA"
    }
}