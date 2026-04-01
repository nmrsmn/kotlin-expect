plugins {
    alias(libs.plugins.kotlin.multiplatform) apply true
}

kotlin {
    jvm()

    iosArm64()
    iosSimulatorArm64()
}
