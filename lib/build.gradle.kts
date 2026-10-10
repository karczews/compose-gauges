plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.maven.publish)
}

kotlin {
    explicitApi()

    android {
        namespace = "io.github.karczews.gauges"
        compileSdk = libs.versions.android.compileSdk.get().toInt()
        minSdk = libs.versions.android.minSdk.get().toInt()
        withHostTest {}
    }
    jvm()
    iosArm64()
    iosSimulatorArm64()
    @OptIn(org.jetbrains.kotlin.gradle.ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        // Bundles the Skiko runtime into the browser tests (CMP-4906); the published klib is unaffected.
        binaries.executable()
    }

    sourceSets {
        commonMain.dependencies {
            api(libs.compose.runtime)
            api(libs.compose.ui)
            implementation(libs.compose.foundation)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    if (providers.gradleProperty("signingInMemoryKey").isPresent) {
        signAllPublications()
    }

    pom {
        name.set("Compose Gauges")
        description.set("A collection of gauge components for Compose Multiplatform.")
        inceptionYear.set("2026")
        url.set("https://github.com/karczews/compose-gauges")
        licenses {
            license {
                name.set("MIT License")
                url.set("https://opensource.org/licenses/MIT")
                distribution.set("repo")
            }
        }
        developers {
            developer {
                id.set("karczews")
                name.set("Krzysztof Karczewski")
                url.set("https://github.com/karczews")
            }
        }
        scm {
            url.set("https://github.com/karczews/compose-gauges")
            connection.set("scm:git:git://github.com/karczews/compose-gauges.git")
            developerConnection.set("scm:git:ssh://git@github.com/karczews/compose-gauges.git")
        }
    }
}
