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

### Run tests

```shell
./gradlew check
```

This will also run code quality checks ([detekt](https://detekt.dev/) and [ktlint](https://pinterest.github.io/ktlint/)).

### Code Quality

The project enforces code quality through two tools, both integrated into the `check` lifecycle:

- **[detekt](https://detekt.dev/)** — static code analysis for Kotlin, applied per module via a convention plugin
- **[ktlint](https://pinterest.github.io/ktlint/)** — Kotlin linter and formatter, run across all source files from the root project

```shell
# Run all checks (tests + detekt + ktlint)
./gradlew check

# Run only ktlint
./gradlew ktlintCheck

# Run only detekt
./gradlew detekt
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines on how to contribute.

## License

This project is licensed under the [MIT License](LICENSE).
