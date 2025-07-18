package cashi.fee.steps

import cashi.fee.*
import io.cucumber.java.After
import io.cucumber.java.Before
import io.cucumber.java.ParameterType
import io.cucumber.java.en.Given
import io.cucumber.java.en.When
import io.cucumber.java.en.Then
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import java.time.LocalDateTime
import kotlin.test.assertEquals

class FeeDefinitions {

    private val restate: RestateTestContainer = RestateTestContainer(TransactionService(), TransactionFee())

    private var amount: Double = 0.0
    private lateinit var transactionId: String
    private lateinit var transactionType: TransactionType

    @Before
    fun setup() {
        restate.createRunner()
    }

    @After
    fun teardown() {
        restate.destroyRunner()
    }

    @ParameterType(".*")
    fun transactionType(type: String): TransactionType {
        return Json.decodeFromString<TransactionType>(type)
    }

    @Given("I submit a transaction with id {string} and amount {double}")
    fun setTransactionAmount(transactionId: String, amount: Double) {
        this.transactionId = transactionId
        this.amount = amount
    }

    @When("the transaction type is {transactionType}")
    fun setTransactionType(transactionType: TransactionType) {
        this.transactionType = transactionType
    }

    @Then("the rate should be {double} and the fee should be {double}")
    fun checkRateAndFee(rate: Double, fee: Double) = runTest {
        val client = TransactionServiceClient.fromClient(restate.ingress)

        val request = TransactionRequest(
            transactionId = "txn_001",
            amount = amount,
            asset = "USD",
            assetType = "FIAT",
            type = transactionType,
            state = "SETTLED - PENDING FEE",
            createdAt = LocalDateTime.now()
        )

        val response = client.fee(request)

        assertEquals(rate, response.rate)
        assertEquals(fee, response.fee)
    }
}

