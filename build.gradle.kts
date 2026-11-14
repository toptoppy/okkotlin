plugins {
    kotlin("jvm") version "2.1.0"
    application
}

group = "toptoppy.kotlin.training"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin Coroutines & Flow (Latest Stable)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.10.1")

    // Testing Suite (TDD / BDD)
    testImplementation(kotlin("test"))
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.10.1")
    testImplementation("io.kotest:kotest-assertions-core:5.9.1")
    testImplementation("io.mockk:mockk:1.13.13")
}

application {
    mainClass.set("toptoppy.kotlin.training.MainKt")
}

tasks.register<JavaExec>("runCapstone") {
    group = "application"
    description = "Runs the EventPulse reactive pipeline capstone demo"
    classpath = sourceSets["main"].runtimeClasspath
    mainClass.set("toptoppy.kotlin.training.capstone.eventpulse.EventPulseDemoKt")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = false
    }
}

kotlin {
    jvmToolchain(21)
}
