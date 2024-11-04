package com.dm.cryptotracker.crypto.data.networking

import com.dm.cryptotracker.core.data.networking.constructUrl
import com.dm.cryptotracker.core.data.networking.safeCall
import com.dm.cryptotracker.core.domain.util.NetworkError
import com.dm.cryptotracker.core.domain.util.Result
import com.dm.cryptotracker.core.domain.util.map
import com.dm.cryptotracker.crypto.data.mappers.toCoin
import com.dm.cryptotracker.crypto.data.networking.dto.CoinHistoryDto
import com.dm.cryptotracker.crypto.data.networking.dto.CoinsResponseDto
import com.dm.cryptotracker.crypto.data.networking.dto.toCoinPrice
import com.dm.cryptotracker.crypto.domain.Coin
import com.dm.cryptotracker.crypto.domain.CoinDataSource
import com.dm.cryptotracker.crypto.domain.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoins(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinsResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets")
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {

        val startInMillis = start.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        val endInMillis = end.withZoneSameInstant(ZoneId.of("UTC")).toInstant().toEpochMilli()
        return safeCall<CoinHistoryDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ){
                parameter("interval", "h6")
                parameter("start", startInMillis)
                parameter("end", endInMillis)
            }
        }.map { response->
            response.data.map { it.toCoinPrice() }
        }
    }

}