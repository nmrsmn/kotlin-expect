plugins {
    alias(libs.plugins.kotlin.multiplatform) apply true
    alias(libs.plugins.dokka) apply true
    alias(libs.plugins.maven.publish) apply true
    alias(libs.plugins.nmarsman.detekt) apply true
    alias(libs.plugins.test.balloon) apply true
}

group = "dev.nmarsman.expect"
version = libs.versions.kotlin.expect.get()

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }

    sourceSets {
        commonMain.dependencies {
            implementation(libs.opentest4k)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.test.balloon.core)
        }
    }
}

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()

    coordinates(
        groupId = "dev.nmarsman.expect",
        artifactId = "kotlin-expect-core",
        version = libs.versions.kotlin.expect.get(),
    )

    pom {
        name.set("Kotlin Expect")
        description.set("A Kotlin library for writing fluent and descriptive assertions, with a focus test readability.")
        inceptionYear.set("2026")

        developers {
            developer {
                id.set("nmrsmn")
                name.set("Niels Marsman")
                url.set("https://github.com/nmrsmn")
                timezone.set("Europe/Amsterdam")
            }
        }

        scm {
            url.set("https://github.com/nmrsmn/kotlin-expect")
            connection.set("git@github.com:nmrsmn/kotlin-expect.git")
        }

        licenses {
            license {
                name = "The MIT License (MIT)"
                url = "https://mit-license.org/"
                distribution = "https://mit-license.org/"
            }
        }
    }
}
