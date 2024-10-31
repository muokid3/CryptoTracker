package com.dm.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinDto(
    val id: String,
    val name: String,
    val rank: Int,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double,


//    val maxSupply: String,
//    val supply: String,
//    val volumeUsd24Hr: String,
//    val vwap24Hr: String
)