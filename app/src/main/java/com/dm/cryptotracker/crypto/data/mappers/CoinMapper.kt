package com.dm.cryptotracker.crypto.data.mappers

import com.dm.cryptotracker.crypto.data.networking.dto.CoinDto
import com.dm.cryptotracker.crypto.domain.Coin

fun CoinDto.toCoin(): Coin{
    return Coin(
        id = id,
        name = name,
        rank = rank,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr,
    )
}