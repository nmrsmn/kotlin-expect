plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.nmarsman.detekt) apply false
    alias(libs.plugins.test.balloon) apply false
    base
}

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint(libs.ktlint) {
        attributes {
            attribute(Bundling.BUNDLING_ATTRIBUTE, objects.named(Bundling.EXTERNAL))
        }
    }
}

val ktlintCheck by tasks.registering(JavaExec::class) {
    group = LifecycleBasePlugin.VERIFICATION_GROUP
    description = "Check Kotlin code style"
    classpath = ktlint
    mainClass.set("com.pinterest.ktlint.Main")
    args(
        "**.kt",
        "**.kts",
        "!**/build/**",
    )
}

tasks.named("check") {
    dependsOn(ktlintCheck)
}
