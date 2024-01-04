package com.example.pairs.presentation.game

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.pairs.R
import com.example.pairs.model.CardModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class GameViewModel : ViewModel() {

    private val _timerValue = MutableStateFlow<Long>(0)
    val timerValue: StateFlow<Long>
        get() = _timerValue

    private val _canTake = MutableStateFlow(false)
    val canTake: StateFlow<Boolean>
        get() = _canTake

    private var countDownTimer: CountDownTimer? = null

    private val _cards = MutableStateFlow<List<CardModel>>(emptyList())
    val cards: StateFlow<List<CardModel>>
        get() = _cards


    fun generateCards() {
        val cards = listOf(
            R.drawable.card_icon_1,
            R.drawable.card_icon_2,
            R.drawable.card_icon_3,
            R.drawable.card_icon_4,
            R.drawable.card_icon_5,
            R.drawable.card_icon_6,
            R.drawable.card_icon_7,
            R.drawable.card_icon_8,
            R.drawable.card_icon_9,
            R.drawable.card_icon_10,
        )
        val cardsList = mutableListOf<CardModel>()
        for (a in 0..9) {
            for (b in 0..1) {
                cardsList.add(CardModel(cards[a]))
            }
        }
        _cards.value = cardsList.toList().shuffled()
    }

    fun startTimer(mills: Long) {
        countDownTimer = object : CountDownTimer(mills, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerValue.value = millisUntilFinished
            }

            override fun onFinish() {
                _canTake.value = true
            }

        }.start()
    }
}