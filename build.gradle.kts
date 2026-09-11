import java.io.File

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
        kotlin.srcDir("src/stub/kotlin")
    }
}

dependencies {
    implementation(kotlin("stdlib"))
}

tasks.register("buildExtensionApk") {
    dependsOn("compileKotlin")
    doLast {
        val buildDir = layout.buildDirectory.get().asFile
        val classesDir = File(buildDir, "classes/kotlin/main")
        val dexDir = File(buildDir, "dex").apply { mkdirs() }
        val apkFile = File(buildDir, "extension.apk")
        val outputDir = File(projectDir, "output").apply { mkdirs() }
        val finalZip = File(outputDir, "com.lunarenzo.smallcaps.zip")

        // Find android sdk d8 tool
        val androidHome = System.getenv("ANDROID_HOME") ?: System.getenv("ANDROID_SDK_ROOT")
        val d8Executable = if (androidHome != null) {
            File(androidHome, "build-tools").listFiles()
                ?.sortedByDescending { it.name }
                ?.map { File(it, if (System.getProperty("os.name").lowercase().contains("win")) "d8.bat" else "d8") }
                ?.firstOrNull { it.exists() }
        } else null

        // Collect compiled class files (excluding stubs com/rk, androidx, android)
        val classFiles = fileTree(classesDir).files.filter { file ->
            val relPath = file.relativeTo(classesDir).path.replace('\\', '/')
            !relPath.startsWith("com/rk/") && !relPath.startsWith("androidx/") && !relPath.startsWith("android/")
        }

        if (d8Executable != null && d8Executable.exists()) {
            println("Converting compiled class files to Android DEX bytecode using ${d8Executable.absolutePath}...")
            val d8Args = mutableListOf(d8Executable.absolutePath, "--output", dexDir.absolutePath)
            classFiles.forEach { d8Args.add(it.absolutePath) }

            val process = ProcessBuilder(d8Args).inheritIO().start()
            val exitCode = process.waitFor()
            if (exitCode != 0) {
                throw GradleException("d8 dexing failed with exit code $exitCode")
            }

            // Create extension.apk containing classes.dex
            println("Packaging extension.apk...")
            ant.invokeMethod("zip", mapOf(
                "destfile" to apkFile.absolutePath,
                "basedir" to dexDir.absolutePath
            ))

            // Create final extension zip package containing manifest.json and extension.apk
            println("Creating final extension package: ${finalZip.absolutePath}...")
            ant.invokeMethod("zip", mapOf(
                "destfile" to finalZip.absolutePath,
                "includes" to "manifest.json",
                "basedir" to projectDir.absolutePath
            ))
            ant.invokeMethod("zip", mapOf(
                "destfile" to finalZip.absolutePath,
                "update" to "true",
                "includes" to "extension.apk",
                "basedir" to buildDir.absolutePath
            ))
            println("Extension package created successfully!")
        } else {
            println("ANDROID_HOME d8 tool not found locally. Preparing fallback package...")
            ant.invokeMethod("zip", mapOf(
                "destfile" to apkFile.absolutePath,
                "basedir" to classesDir.absolutePath,
                "excludes" to "com/rk/** androidx/** android/**"
            ))
            ant.invokeMethod("zip", mapOf(
                "destfile" to finalZip.absolutePath,
                "includes" to "manifest.json",
                "basedir" to projectDir.absolutePath
            ))
            ant.invokeMethod("zip", mapOf(
                "destfile" to finalZip.absolutePath,
                "update" to "true",
                "includes" to "extension.apk",
                "basedir" to buildDir.absolutePath
            ))
        }
    }
}
