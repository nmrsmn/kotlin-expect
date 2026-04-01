plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(libs.detekt)
}

val packageName = "dev.nmarsman.conventions"
group = packageName

kotlin {
    jvmToolchain(libs.versions.jvm.toolchain.get().toInt())
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("applyDetekt") {
            id = "${packageName}.detekt"
            implementationClass = "${packageName}.ApplyDetektPlugin"
        }
    }
}
