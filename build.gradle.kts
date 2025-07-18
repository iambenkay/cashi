plugins {
  application
  kotlin("jvm") version "2.1.20"
  kotlin("plugin.serialization") version "2.1.20"
  id("com.google.devtools.ksp") version "2.1.20-1.0.32"
}

repositories {
  mavenCentral()
}

val restateVersion = "2.2.0"
val log4jVersion = "2.24.3"
val cucumberVersion = "7.4.1"
val kotlinCoroutinesTestVersion = "1.7.3"

dependencies {
  // Annotation processor
  ksp("dev.restate:sdk-api-kotlin-gen:$restateVersion")

  // Restate SDK
  implementation("dev.restate:sdk-kotlin-http:$restateVersion")

  // Logging
  implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")

  testImplementation("dev.restate:sdk-testing:$restateVersion")
  testImplementation("io.cucumber:cucumber-java:$cucumberVersion")
  testImplementation("io.cucumber:cucumber-junit:$cucumberVersion")
  testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:$kotlinCoroutinesTestVersion")
  testImplementation(kotlin("test"))
}

kotlin {
  jvmToolchain(21)
}

// Configure main class
application {
  mainClass.set("cashi.fee.TransactionServiceKt")
}
