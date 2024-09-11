package com.example.cardingo.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Words(
    val id : Int,
    val word: String,
    val image: String,
    val turkishWord: String,
    var sentence : String,
    var turkishSentence: String,
    val level: String,
    val language : String,
    val country : String
) : Parcelable
