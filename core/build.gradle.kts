plugins {
    alias(libs.plugins.kotlin.multiplatform) apply true
    alias(libs.plugins.nmarsman.detekt) apply true
    alias(libs.plugins.test.balloon) apply true
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.test.balloon.core)
        }
    }
}
