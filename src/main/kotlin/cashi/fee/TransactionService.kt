package cashi.fee

import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Name
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.http.vertx.RestateHttpServer
import dev.restate.sdk.kotlin.*
import dev.restate.sdk.kotlin.endpoint.endpoint

@Name("transaction")
@Service
class TransactionService {

    @Handler
    suspend fun fee(ctx: Context, req: TransactionRequest): TransactionResponse {
        val client = TransactionFeeClient.fromContext(ctx, req.transactionId)

        val fee = client.calculateFee(req).await()
        return req.toResponse(fee)
    }
}

fun main() {
    RestateHttpServer.listen(endpoint {
        bind(TransactionService())
        bind(TransactionFee())
    }, 9090)
}