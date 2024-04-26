package com.anak.grckikino.models

import com.google.gson.annotations.SerializedName

data class GameData(
    @SerializedName("gameId") val gameId: Int,
    @SerializedName("drawId") val drawId: Int,
    @SerializedName("drawTime") val drawTime: Long,
    @SerializedName("status") val status: String,
    @SerializedName("drawBreak") val drawBreak: Int,
    @SerializedName("visualDraw") val visualDraw: Int,
    @SerializedName("pricePoints") val pricePoints: PricePoints,
    @SerializedName("prizeCategories") val prizeCategories: List<PrizeCategory>,
    @SerializedName("wagerStatistics") val wagerStatistics: WagerStatistics,
    @SerializedName("winningNumbers") val winningNumbers: WinningNumbers
)

data class WinningNumbers(
    @SerializedName("list") val list: List<Int>,
    @SerializedName("bonus") val bonus: List<Int>,
    @SerializedName("sidebets") val sideBets: SideBets
)

data class SideBets(
    @SerializedName("evenNumbersCount") val evenNumbersCount: Int,
    @SerializedName("oddNumbersCount") val oddNumbersCount: Int,
    @SerializedName("winningColumn") val winningColumn: Int,
    @SerializedName("winningParity") val winningParity: String,
    @SerializedName("oddNumbers") val oddNumbers: List<Int>,
    @SerializedName("evenNumbers") val evenNumbers: List<Int>,
    @SerializedName("columnNumbers") val columnNumbers: Map<Int, List<Int>>
)

data class AddOn(
    @SerializedName("amount") val amount: Double,
    @SerializedName("gameType") val gameType: String
)

data class PricePoints(
    @SerializedName("amount") val amount: Double,
    @SerializedName("addOn") val addOn: List<AddOn>
)

data class PrizeCategory(
    @SerializedName("id") val id: Int,
    @SerializedName("divident") val divident: Double,
    @SerializedName("winners") val winners: Int,
    @SerializedName("distributed") val distributed: Double,
    @SerializedName("jackpot") val jackpot: Double,
    @SerializedName("fixed") val fixed: Double,
    @SerializedName("categoryType") val categoryType: Int,
    @SerializedName("gameType") val gameType: String
)

data class WagerStatistics(
    @SerializedName("column") val column: Int,
    @SerializedName("wagers") val wagers: Int,
    @SerializedName("addOn") val addOn: List<String>
)
