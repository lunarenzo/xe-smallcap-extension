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
}

sourceSets {
    main {
        kotlin.srcDir("src/stub/kotlin")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.named<ShadowJar>("shadowJar") {
    archiveFileName.set("SmallCapsExtension.zip")
    isZip64 = true
    destinationDirectory.set(file("./output"))

    // Exclude host SDK stubs from the compiled extension output package
    exclude("com/rk/**")
    exclude("androidx/**")
    exclude("android/**")
}

tasks.register<Zip>("buildExtensionPackage") {
    dependsOn("shadowJar")
    archiveFileName.set("com.lunarenzo.smallcaps.zip")
    destinationDirectory.set(file("./output"))

    from(file("manifest.json"))
    from(file("output/SmallCapsExtension.zip"))
}
