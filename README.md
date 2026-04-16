# Kotlin Expect

A Kotlin Multiplatform assertion/expection library designed to provide expressive, type-safe test assertions across all supported platforms.

> **Status**: 🚧 Work in progress — the API is under active development and not yet published.

## Supported platforms

| Platform             | Status |
|----------------------|--------|
| JVM                  | ✅      |
| iOS (arm64)          | ✅      |
| iOS (simulatorArm64) | ✅      |

## Requirements

- **JDK**: 21+
- **Gradle**: 9.4.1 (included via wrapper)
- **Kotlin**: 2.3.20

## Getting Started

### Build the project

```shell
./gradlew build
```

### Run checks

```shell
./gradlew check
```

This will run code quality checks ([detekt](https://detekt.dev/), [ktlint](https://pinterest.github.io/ktlint/)) and code coverage verification ([Kover](https://github.com/Kotlin/kotlinx-kover)).

### Testing

Tests are written using [testBalloon](https://github.com/nicholasgasior/testballoon) as the testing framework. Tests reside in the `commonTest` source set and should run across all supported platforms.

```shell
# Run all tests
./gradlew allTests

# Run JVM tests only
./gradlew jvmTest
```

### Code Coverage

Code coverage is measured by **[Kover](https://github.com/Kotlin/kotlinx-kover)** and aggregated across all modules. A verification rule enforces **100% branch coverage** per class.

```shell
# Run coverage verification
./gradlew koverVerify

# Generate an HTML coverage report
./gradlew koverHtmlReport
```

### Code Quality

The project enforces code quality through two tools, both integrated into the `check` lifecycle:

- **[detekt](https://detekt.dev/)** — static code analysis for Kotlin, applied per module via a convention plugin
- **[ktlint](https://pinterest.github.io/ktlint/)** — Kotlin linter and formatter, run across all source files from the root project

```shell
# Run all checks (tests + detekt + ktlint + kover)
./gradlew check

# Run only ktlint
./gradlew ktlintCheck

# Run only detekt
./gradlew detekt
```

## Examples

Use `expectThat` to start an assertion on any value, then chain one or more assertions:

### Equality

```kotlin
expectThat(42)
    .isEqualTo(42)

expectThat("hello")
    .isEqualTo("hello")

expectThat(listOf(1, 2, 3))
    .isEqualTo(listOf(1, 2, 3))
```

### Type checking

```kotlin
expectThat("hello")
    .isA<String>()

expectThat(1L)
    .isA<Number>()
```

### Null checking

```kotlin
expectThat(null)
    .isNull()
```

### Boolean checking

```kotlin
expectThat(true)
    .isTrue()

expectThat(false)
    .isFalse()
```

### CharSequence assertions

```kotlin
expectThat("")
    .isEmpty()

expectThat("hello")
    .isNotEmpty()
    .isNotBlank()
    .hasLength(5)

expectThat("hello world")
    .startsWith("hello")
    .endsWith("world")
    .contains("lo wo")

expectThat("Hello World")
    .containsIgnoringCase("hello world")

expectThat("abc123")
    .matches(Regex("[a-z]+\\d+"))

expectThat("Hello World")
    .matchesIgnoringCase(Regex("hello world"))
```

### Comparable assertions

```kotlin
expectThat(2026)
    .isGreaterThan(2025)

expectThat(3.14)
    .isLessThan(4.0)

expectThat(12L)
    .isGreaterThanOrEqualTo(12L)
    .isLessThanOrEqualTo(12L)

expectThat(12)
    .isIn(10..20)
```

### Enum assertions

```kotlin
expectThat(Color.RED)
    .isIn(Color.RED, Color.GREEN)
```

### Chaining assertions

Assertions return the builder, so you can chain multiple checks on the same subject:

```kotlin
expectThat("hello")
    .isA<String>()
    .isEqualTo("hello")
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute.

## License

This project is licensed under the [MIT License](LICENSE).
