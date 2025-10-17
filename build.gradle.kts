plugins {
    kotlin("jvm") version "2.2.0"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.sksamuel.scrimage:scrimage-core:4.1.3")
    implementation("com.sksamuel.scrimage:scrimage-webp:4.1.3")
    implementation(kotlin("stdlib"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}