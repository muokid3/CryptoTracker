package com.dm.cryptotracker.crypto.presentation.coin_details

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dm.cryptotracker.R
import com.dm.cryptotracker.crypto.presentation.coin_details.component.InfoCard
import com.dm.cryptotracker.crypto.presentation.coin_list.CoinListState
import com.dm.cryptotracker.crypto.presentation.coin_list.components.previewCoin
import com.dm.cryptotracker.crypto.presentation.models.toDisplayableNumber
import com.dm.cryptotracker.ui.theme.CryptoTrackerTheme
import com.dm.cryptotracker.ui.theme.greenBackground

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CoinDetailsScreen(
    state: CoinListState,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) {
        Color.White
    } else {
        Color.Black
    }


    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else if (state.selectedCoin != null) {
        val coin = state.selectedCoin

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Icon(
                imageVector = ImageVector.vectorResource(id = coin.iconRes),
                contentDescription = coin.name,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(100.dp)
            )

            Text(
                text = coin.name,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black,
                color = contentColor
            )
            Text(
                text = coin.symbol,
                fontSize = 20.sp,
                fontWeight = FontWeight.Light,
                color = contentColor
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                InfoCard(
                    title = stringResource(R.string.market_cap),
                    formatedText = "$ ${coin.marketCapUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.stock),
                )

                InfoCard(
                    title = stringResource(R.string.price),
                    formatedText = "$ ${coin.priceUsd.formatted}",
                    icon = ImageVector.vectorResource(id = R.drawable.dollar),
                )

                val absoluteChangeFormatted =
                    (coin.priceUsd.value * (coin.changePercent24Hr.value / 100)).toDisplayableNumber()

                val isPositive = coin.changePercent24Hr.value > 0.0

                val contentColor = if (isPositive) {
                    if (isSystemInDarkTheme()) Color.Green else greenBackground
                } else {
                    MaterialTheme.colorScheme.error
                }


                InfoCard(
                    title = stringResource(R.string.change_last_24h),
                    formatedText = "$ ${absoluteChangeFormatted.formatted}",
                    icon = if (isPositive) {
                        ImageVector.vectorResource(id = R.drawable.trending)
                    } else {
                        ImageVector.vectorResource(id = R.drawable.trending_down)
                    },
                    contentColor = contentColor
                )
            }


        }
    }

}

@PreviewLightDark
@Composable
private fun CoinDetailsPrev() {
    CryptoTrackerTheme {
        CoinDetailsScreen(
            state = CoinListState(selectedCoin = previewCoin),
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        )
    }
}