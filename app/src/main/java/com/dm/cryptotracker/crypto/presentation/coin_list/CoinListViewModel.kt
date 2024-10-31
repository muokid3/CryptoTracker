package com.dm.cryptotracker.crypto.presentation.coin_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dm.cryptotracker.core.domain.util.onError
import com.dm.cryptotracker.core.domain.util.onSuccess
import com.dm.cryptotracker.crypto.domain.CoinDataSource
import com.dm.cryptotracker.crypto.presentation.models.toCOinUi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.onStart {
        loadCoins()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        CoinListState()
    )

    fun onAction(coinListAction: CoinListAction) {
        when (coinListAction) {
            is CoinListAction.OnCoinClick -> {
            }
        }
    }

    private fun nothing(): Nothing {
        TODO()
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }

            coinDataSource.getCoins().onSuccess { coins ->
                _state.update { it.copy(isLoading = false, coins = coins.map { it.toCOinUi() }) }

            }.onError { networkError ->
                _state.update { it.copy(isLoading = false) }

            }
        }
    }
}