package com.dm.cryptotracker.di

import com.dm.cryptotracker.core.data.networking.HttpClientFactory
import com.dm.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.dm.cryptotracker.crypto.domain.CoinDataSource
import com.dm.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(engine = CIO.create()) }
//    single { RemoteCoinDataSource(get()) }.bind<CoinDataSource>()
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    viewModelOf(::CoinListViewModel)

}