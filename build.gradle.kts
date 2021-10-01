import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Dependency's version
val JDA = "4.3.0_309" // JDA
val JDA_KTX = "ea0a1b2" // JDA-KTX
val LAVA_PLAYER = "1.3.78" // Lava Player
val LOG_BACK = "1.2.3" //LOGBack

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.khora"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io/")
}

dependencies {
    implementation("net.dv8tion:JDA:${JDA}")
    implementation("com.github.minndevelopment:jda-ktx:${JDA_KTX}")
    implementation("com.sedmelluq:lavaplayer:${LAVA_PLAYER}")
    runtimeOnly("ch.qos.logback:logback-classic:${LOG_BACK}")
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("me.khora.eris.MainKt")
}
