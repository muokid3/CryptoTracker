package com.dm.cryptotracker.crypto.data.networking.dto

import kotlinx.serialization.Serializable


@Serializable
data class CoinDto(
    val changePercent24Hr: Double,
    val id: String,
    val marketCapUsd: Double,
    val maxSupply: String,
    val name: String,
    val priceUsd: Double,
    val rank: Int,
    val supply: String,
    val symbol: String,
    val volumeUsd24Hr: String,
    val vwap24Hr: String
)