package com.example.pairs.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.pairs.model.CardModel

class GameItemDiffCallback: DiffUtil.ItemCallback<CardModel>() {
    override fun areItemsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem.resId == newItem.resId
    }

    override fun areContentsTheSame(oldItem: CardModel, newItem: CardModel): Boolean {
        return oldItem == newItem
    }
}