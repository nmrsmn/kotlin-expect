# Contributing to Kotlin Expect

Thank you for your interest in contributing! This document provides guidelines and instructions for contributing to the project.

## Getting started

1. **Fork** the repository and clone your fork locally.
2. Make sure you have **JDK 21+** installed.
3. Build the project to verify your setup:

   ```shell
   ./gradlew build
   ```

   > The Gradle wrapper is included — you do **not** need to install Gradle separately.

## Development workflow

### Branching

- Create a feature branch from `main` for your work.
- Use descriptive branch names (e.g. `feature/add-string-assertions`, `bugfix/null-handling`).
  - Whenever possible, link the branch name to a related issue (e.g. `feature/EXP123-add-string-assertions`).
- Keep your branch focused on a single feature or bug fix to make reviews easier.
- Regularly pull changes from `main` to keep your branch up to date and minimize merge conflicts.

### Making changes

1. Make your changes in the appropriate module (e.g. `core/` for assertion API work).
2. Write tests for any new functionality.
3. Run the full check suite before submitting:

   ```shell
   ./gradlew check
   ```

   This runs:
   - **Tests** across all platforms (JVM, iOS)
   - **detekt** — static analysis
   - **ktlint** — code style / formatting

4. Fix any issues reported by detekt or ktlint. You can run them individually:

   ```shell
   # Lint check
   ./gradlew ktlintCheck

   # Detekt analysis
   ./gradlew detekt
   ```

### Code Style

- Code style is enforced by **ktlint** — the check runs automatically as part of `./gradlew check`.
- Static analysis rules are configured in [`config/detekt/detekt.yml`](config/detekt/detekt.yml).

## Submitting a pull request

1. Ensure `./gradlew check` passes with no errors.
2. Make sure your branch is up to date with `main` and resolve any conflicts.
3. Push your branch to your fork.
4. Open a pull request against `main`.
5. Provide a clear description of the changes and the motivation behind them.
6. Link any related issues.

## Reporting issues

- Use [GitHub Issues](../../issues) to report bugs or request features.
- Include steps to reproduce, expected behavior, and actual behavior when reporting bugs.

## License

By contributing, you agree that your contributions will be licensed under the [MIT License](LICENSE).

