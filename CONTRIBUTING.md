# Contributing to JUDO

## Development Environment

Make sure your development environment meets the requirements described in the parent project's [CONTRIBUTING guide](https://github.com/BlackBeltTechnology/judo-community/blob/develop/CONTRIBUTING.adoc).

**Minimum requirements:**
- Java 21 JDK
- Maven 3.9.4+

## Code Structure

This project follows a standard Maven Java project layout:

| Directory | Contents |
|-----------|----------|
| `src/main/java` | Production source code |
| `src/test/java` | Unit tests |

## Build Commands

```bash
# Run tests
./mvnw clean test

# Full build (compile, test, package, install)
./mvnw clean install

# Run a specific test
./mvnw test -Dtest=PayloadImplTest
```

## Submitting an Issue

Before submitting, search the [issue tracker](https://github.com/BlackBeltTechnology/judo-dao-api/issues) — your problem may already be reported or resolved.

To help us reproduce and fix bugs quickly, please include:
- Output of `java -version` and `mvn -version`
- `pom.xml` or `.flattened-pom.xml` (when applicable)
- A minimal reproduction case that demonstrates the failure

File new issues using the [issue form](https://github.com/BlackBeltTechnology/judo-dao-api/issues/new/choose).

## Submitting a PR

This project follows [GitHub's standard forking model](https://guides.github.com/activities/forking/). Fork the project and submit pull requests from your fork.
