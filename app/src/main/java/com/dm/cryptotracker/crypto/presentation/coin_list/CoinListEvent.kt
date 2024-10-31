package com.dm.cryptotracker.crypto.presentation.coin_list

import com.dm.cryptotracker.core.domain.util.NetworkError
import java.lang.Error

sealed interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}