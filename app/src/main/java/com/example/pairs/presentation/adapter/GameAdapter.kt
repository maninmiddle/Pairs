package com.example.pairs.presentation.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pairs.databinding.CardSampleBinding
import com.example.pairs.model.CardModel
import com.example.pairs.presentation.game.GameActivity

class GameAdapter(
    private val context: Context
) : ListAdapter<CardModel, GameViewHolder>(
    GameItemDiffCallback()
) {
    private var flippedCards = mutableListOf<CardModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = CardSampleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val card = getItem(position)
        card.position = position

        if (!card.isFlipped) {
            holder.binding.ivPair.visibility = View.GONE
        }



        holder.binding.ivPair.setImageResource(card.resId)

        holder.binding.root.setOnClickListener {
            if (!card.isMatched && !card.isFlipped) {
                card.isFlipped = true
                holder.binding.ivPair.visibility = View.VISIBLE
                checkMatch()
            }


        }


    }

    private fun checkMatch() {
        val flippedCards = currentList.filter { it.isFlipped && !it.isMatched }
        val matchedCards = currentList.filter { it.isMatched }
        if (flippedCards.size == 2 && matchedCards.size == currentList.size - 2) {
            val context = (context as GameActivity)
            context.getResult()
        }

        Log.d("GameAdapter", "$flippedCards")

        if (flippedCards.size == 2) {
            if (flippedCards[0].resId == flippedCards[1].resId) {
                flippedCards.forEach { it.isMatched = true }
                flippedCards.forEach { it.isFlipped = true }
                Log.d("GameAdapter", "agree")
            } else {
                flippedCards.forEach { it.isFlipped = false }
                notifyItemChanged(flippedCards[0].position)
                notifyItemChanged(flippedCards[1].position)
                Log.d("GameAdapter", "passed")
            }
        }
    }
}