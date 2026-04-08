plugins {
    alias(libs.plugins.kotlin.multiplatform) apply true
    alias(libs.plugins.nmarsman.detekt) apply true
    alias(libs.plugins.test.balloon) apply true
}

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
