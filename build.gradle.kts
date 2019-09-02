/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

/*
 * Copyright (C) 2019 The Smash Ks Open Project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is furnished
 * to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE
 * SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        jcenter()
        maven { url = uri("http://dl.bintray.com/kotlin/kotlin-eap") }
    }
    dependencies {
        classpath("com.android.tools.build:gradle:3.5.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${config.Versions.Kotlin.kotlinLib}")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
//        classpath "org.jacoco:org.jacoco.core:0.8.4"
        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
//        classpath "com.google.gms:google-services:4.2.0"
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt").version(config.Versions.Plugin.detekt)
    id("com.github.ben-manes.versions").version(config.Versions.Plugin.versionUpdater)
}

// dependencies {
//     detektPlugins("io.gitlab.arturbosch.detekt:detekt-formatting:${deteckt_version}")
// }

subprojects {
    //region Apply plugin
    apply {
        when (name) {
            "domain", "ext" -> {
                plugin("java-library")
                plugin("kotlin")
            }
            "widget", "ktx", "player", "data" -> {
                plugin("com.android.library")
                plugin("kotlin-android")
            }
            "presentation" -> {
                plugin("com.android.application")
                plugin("kotlin-android")
            }
        }
        if (name == "data" || name == "presentation") {
            plugin("kotlin-android-extensions")
            plugin("org.jetbrains.kotlin.kapt")
        }
        plugin("io.gitlab.arturbosch.detekt")
//        plugin("org.jlleitschuh.gradle.ktlint")
    }
    //endregion

    //region Detekt
    val detektVersion = config.Versions.Plugin.detekt
    detekt {
        toolVersion = detektVersion
        debug = true
        parallel = true
        input = files("src/main/java")
        config = files("$rootDir/detekt.yml")

        idea {
            path = "$rootDir/.idea"
            codeStyleScheme = "$rootDir/.idea/idea-code-style.xml"
            inspectionsProfile = "$rootDir/.idea/inspect.xml"
            mask = "*.kt"
        }
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt> {
        exclude(".*/resources/.*", ".*/build/.*") // but exclude our legacy internal package
    }
    //endregion

    tasks.whenObjectAdded {
        if (
            name.contains("lint") ||
            name == "clean" ||
            name.contains("jacoco") ||
            name.contains("lintVitalRelease") ||
            name.contains("Aidl") ||
            name.contains("mockableAndroidJar") ||
            name.contains("UnitTest") ||
            name.contains("AndroidTest") ||
            name.contains("Ndk") ||
            name.contains("Jni")
        ) {
            enabled = false
        }
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        // required to find the project's artifacts
        maven { url = uri("https://dl.bintray.com/pokk/maven") }
        maven { url = uri("http://dl.bintray.com/kotlin/kotlin-eap") }
        maven { url = uri("https://dl.bintray.com/kodein-framework/Kodein-DI") }
    }
    tasks {
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                jvmTarget = "1.8"
                suppressWarnings = false
                freeCompilerArgs = listOf("-Xuse-experimental=kotlin.Experimental",
                                          "-Xuse-experimental=kotlin.ExperimentalStdlibApi",
                                          "-Xuse-experimental=kotlin.ExperimentalContracts",
                                          "-Xuse-experimental=org.mylibrary.ExperimentalMarker")
            }
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
