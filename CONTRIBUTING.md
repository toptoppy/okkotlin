# Contributing to OkKotlin

Welcome! We appreciate your interest in contributing to **OkKotlin**, a showcase and reference guide for modern Kotlin 2.x and reactive JVM development.

## Getting Started

1. **Prerequisites:**
   - JDK 21 or higher
   - Gradle (use the included `./gradlew` wrapper)

2. **Clone & Build:**
   ```bash
   git clone git@github.com:toptoppy/okkotlin.git
   cd okkotlin
   ./gradlew test
   ```

## Development Guidelines

- **Code Style:** Follow official Kotlin coding conventions. Keep functions small, focused, and expressive.
- **Modern Idioms:** Prefer sealed interfaces, value classes (`@JvmInline`), and functional error handling (`Result<T>`) over exceptions for control flow.
- **Coroutines & Flow:** Use structured concurrency with proper cancellation support and avoid `GlobalScope`.
- **Testing:** Every new module or utility should include comprehensive tests in `src/test/kotlin/` using `kotlin.test`, `kotlinx-coroutines-test` (`runTest`), and Kotest assertions.

## Submitting Pull Requests

1. Create a feature branch: `git checkout -b feature/my-new-topic`
2. Commit your changes: `git commit -m "feat: add topic demo"`
3. Run test verification: `./gradlew check test`
4. Open a Pull Request with a clear description of the concepts introduced.
