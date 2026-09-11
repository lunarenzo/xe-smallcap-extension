plugins {
    kotlin("jvm") version "2.0.21"
}

group = "com.lunarenzo"
version = "1.0.0"

repositories {
    mavenCentral()
    google()
}

sourceSets {
    main {
        java.srcDir("src/stub/java")
        kotlin.srcDir("src/stub/kotlin")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}
