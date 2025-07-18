package cashi.fee

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.VirtualObject
import dev.restate.sdk.kotlin.ObjectContext
import dev.restate.sdk.kotlin.stateKey

@VirtualObject
class TransactionFee {

    companion object {
        val FEE = stateKey<Fee>("fee")
    }

    @Handler
    suspend fun calculateFee(ctx: ObjectContext, transaction: TransactionRequest): Fee {
        return ctx.getFee() ?: run {
            val fee = Fee(transaction.type.rate * transaction.amount, transaction.type.rate)
            ctx.setFee(fee)
            fee
        }
    }

    private suspend fun ObjectContext.getFee() = this.get(FEE)
    private suspend fun ObjectContext.setFee(fee: Fee) = this.set(FEE, fee)
}