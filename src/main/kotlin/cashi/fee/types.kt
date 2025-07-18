package cashi.fee

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TransactionRequest(
    @SerialName("transaction_id") val transactionId: String,
    val amount: Double,
    val asset: String,
    @SerialName("asset_type") val assetType: String,
    val type: TransactionType,
    val state: String,
    @SerialName("created_at")
    @Serializable(with = DateTimeSerializer::class)
    val createdAt: LocalDateTime,
) {
    fun toResponse(fee: Fee) =
        TransactionResponse(
            transactionId = transactionId,
            amount = amount,
            asset = asset,
            type = type,
            fee = fee.amount,
            rate = fee.rate,
            description = "Standard fee rate of ${"%.2f".format(fee.rate * 100.0)}%"
        )
}

@Serializable
data class Fee(val amount: Double, val rate: Double)

@Serializable
data class TransactionResponse(
    @SerialName("transaction_id") val transactionId: String,
    val amount: Double,
    val asset: String,
    val type: TransactionType,
    val fee: Double,
    val rate: Double,
    val description: String,
)

@Serializable
enum class TransactionType(val rate: Double) {
    @SerialName("Mobile Top Up")
    MobileTopUp(.0015),
    @SerialName("Flight Booking")
    FlightBooking(.04),
    @SerialName("Domestic Transfer")
    DomesticTransfer(.025);
}