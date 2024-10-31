package com.dm.cryptotracker.crypto.domain

import com.dm.cryptotracker.core.domain.util.NetworkError
import com.dm.cryptotracker.core.domain.util.Result

interface CoinDataSource {
    suspend fun getCoins(): Result<List<Coin>, NetworkError>
}