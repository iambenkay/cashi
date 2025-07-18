package cashi.fee

import dev.restate.client.Client
import dev.restate.sdk.endpoint.Endpoint
import dev.restate.sdk.testing.RestateRunner
import dev.restate.serde.kotlinx.KotlinSerializationSerdeFactory

class RestateTestContainer(vararg bindTargets: Any) {
    private val runner = getRestateClient(*bindTargets)
    val ingress: Client by lazy {
        Client.connect(runner.restateUrl.toString(), KotlinSerializationSerdeFactory())
    }

    fun createRunner() {
        runner.start()
    }

    fun destroyRunner() {
        runner.stop()
    }

    private fun getRestateClient(vararg bindTargets: Any): RestateRunner {
        val endpoint = Endpoint.Builder()
        bindTargets.forEach { endpoint.bind(it) }

        val runner = RestateRunner.from(endpoint.build())
        runner.withRestateContainerImage("docker.io/restatedev/restate:latest")
        return runner.build()
    }
}