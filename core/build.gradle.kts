plugins {
    alias(libs.plugins.kotlin.multiplatform) apply true
    alias(libs.plugins.nmarsman.detekt) apply true
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()
}
