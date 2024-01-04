package com.example.pairs.model

data class CardModel(
    val resId: Int,
    var isFlipped: Boolean = false,
    var isMatched: Boolean = false,
    var position: Int = 0
)