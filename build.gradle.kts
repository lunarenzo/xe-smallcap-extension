import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "2.0.21"
    id("com.gradleup.shadow") version "8.3.5"
}

group = "com.lunarenzo"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
    maven("https://jitpack.io")
}

dependencies {
    // Standard Kotlin JDK library
    implementation(kotlin("stdlib"))

    // Android/Xed Editor SDK compile-only dependencies if available
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("SmallCapsExtension.zip")
    isZip64 = true
    destinationDirectory.set(file("./output"))
}

tasks.register<Zip>("buildExtensionPackage") {
    dependsOn("shadowJar")
    archiveFileName.set("com.lunarenzo.smallcaps.zip")
    destinationDirectory.set(file("./output"))

    from(file("manifest.json"))
    from(file("output/SmallCapsExtension.zip"))
}
