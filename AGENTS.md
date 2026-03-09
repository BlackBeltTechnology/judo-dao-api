# JUDO DAO API - Project Documentation

## Project Overview


**Repository:** BlackBeltTechnology/judo-dao-api
**License:** Eclipse Public License 2.0 (EPL-2.0)
**Java Version:** 21
**Build System:** Maven 3.9.4+ with Maven Wrapper (`./mvnw`)

1. Defines the core `DAO` interface specifying all CRUD, query, navigation, and reference management operations on EMF-typed transfer objects
2. Provides the `Payload` interface (extends `Map<String, Object>`) and its `PayloadImpl` as the universal data transfer object for the JUDO runtime
3. Defines the `PayloadValidator` interface and `ValidationResult` model for payload validation
4. Provides the `IdentifierProvider` interface for pluggable identifier generation
5. Packaged as an OSGi bundle for modular deployment in the JUDO runtime

## Code Instructions

1. First think through the problem, read the codebase for relevant files.
2. Before you make any major changes, check in with me and I will verify the plan.
3. Please every step of the way just give me a high level explanation of what changes you made.
4. Make every task and code change you do as simple as possible. We want to avoid making any massive or complex changes. Every change should impact as little code as possible. Everything is about simplicity.
5. Maintain a documentation file that describes how the architecture of the app works inside and out.
6. Never speculate about code you have not opened. If the user references a specific file, you MUST read the file before answering. Make sure to investigate and read relevant files BEFORE answering questions about the codebase. Never make any claims about code before investigating unless you are certain of the correct answer - give grounded and hallucination-free answers.
7. For implementation use TDD (Test-Driven Development): write or update tests first to define the expected behaviour, verify they fail, then write the minimal implementation to make them pass.
8. Use DRY (Don't Repeat Yourself): extract reusable logic into separate classes, utilities, or components. If the same pattern appears in multiple places, refactor it into a shared helper.

## Directory Structure

```
judo-dao-api/
├── src/main/java/hu/blackbelt/judo/dao/api/   # All production source (single package)
├── src/test/java/hu/blackbelt/judo/dao/api/   # Unit tests
├── openspec/                                   # OpenSpec change management
│   ├── config.yaml
│   ├── changes/
│   └── specs/
├── .github/workflows/                          # CI/CD pipelines
├── .claude/                                    # Claude Code skills
├── .vscode/                                    # VS Code settings
├── .zed/                                       # Zed editor settings
├── pom.xml                                     # Maven build config
└── logback-test.xml                            # Test logging config
```

## Core Modules

This is a single-module project (no Maven submodules). All source lives in one package: `hu.blackbelt.judo.dao.api`.

| Class/Interface | Type | Purpose |
|-----------------|------|---------|
| `DAO` | Interface | Main data access contract — CRUD, search, count, navigation, reference management. All methods accept EMF types (`EClass`, `EReference`, `EAttribute`). Contains inner classes `OrderBy`, `Seek`, and `QueryCustomizer`. |
| `Payload` | Interface | Data transfer object extending `Map<String, Object>`. Provides factory methods (`map(...)`, `empty()`, `asPayload(...)`) and type-safe accessors (`getAsPayload`, `getAsCollectionPayload`, `getAs`). |
| `PayloadImpl` | Class | `TreeMap`-based `Payload` implementation. Auto-wraps nested maps/collections as `Payload` on construction. Keys prefixed with `__$` (`TRANSIENT_PREFIX`) are transient — excluded from `equals()`/`hashCode()`. JSON serialization via Jackson with `JavaTimeModule`. |
| `PayloadValidator` | Interface | Validates payloads, references, and attributes against EMF-typed transfer object definitions. |
| `ValidationResult` | Class | Lombok `@Data`/`@Builder` value class: `code`, `level` (ERROR/WARNING), `location`, `details` map. |
| `IdentifierProvider` | Interface | Provides identifiers (`get()`), their type (`getType()`), and name (`getName()`). |
| `JacksonNullKeySerializer` | Class | Package-private Jackson serializer that writes null map keys as empty strings. Used by `PayloadImpl.toString()`. |

## Technology Stack

### Core Technologies
- **Java 21** — source and target
- **Eclipse EMF (ECore)** — `org.eclipse.emf.ecore` 2.12.0 — metamodel types (`EClass`, `EAttribute`, `EReference`) used throughout the DAO interface
- **Lombok** 1.18.34 — `@Builder`, `@Getter`, `@Data`, `@NonNull`, `@Singular`
- **Jackson** 2.17.2 — `jackson-databind` + `jackson-datatype-jsr310` (JavaTimeModule) for JSON serialization in `PayloadImpl`
- **Google Guava** 30.0-jre — utility library
- **OSGi** 6.0.0 — `org.osgi.core`, `osgi.cmpn`, `org.osgi.annotation` for bundle packaging
- **SLF4J** 2.0.16 / **Logback** 1.5.12 — logging

### Build & Quality
- **Maven Wrapper** (`./mvnw`) included in repository
- **maven-bundle-plugin** 5.1.8 — OSGi bundle packaging, exports `hu.blackbelt.judo.dao.api*`
- **flatten-maven-plugin** 1.3.0 — CI-friendly `${revision}` version resolution
- **JaCoCo** 0.8.12 — code coverage
- **SonarQube** — static analysis integration
- **JUnit 5** (Jupiter) 5.9.1 — unit testing
- **Hamcrest** 2.2 — matcher assertions
- **Mockito** 4.8.0 — mocking
- **Surefire** 3.5.1 — test execution with `--add-opens` for Java module system reflection

## Build Commands

```bash
# Full build (compile, test, package, install locally)
./mvnw clean install

# Run all tests
./mvnw clean test

# Run a specific test class
./mvnw test -Dtest=PayloadImplTest

# Run a specific test method
./mvnw test -Dtest=PayloadImplTest#testMethodName

# Generate coverage report (output: target/site/jacoco/)
./mvnw clean test jacoco:report
```

### Maven Profiles

| Profile | Purpose |
|---------|---------|
| `sign-artifacts` | GPG-signs artifacts using `sign-maven-plugin` |
| `release-judong` | Deploys snapshots/releases to JUDO Nexus (`nexus.judo.technology`) |
| `release-central` | Deploys to Maven Central via Sonatype OSSRH |
| `release-dummy` | Deploys to local filesystem (`/tmp/`) for testing |
| `generate-github-asciidoc-diagrams` | Generates diagrams from AsciiDoc using AsciidoctorJ + PlantUML |
| `update-source-code-license` | Updates EPL 2.0 license headers on source files |

## Key Configuration Files

| File | Purpose |
|------|---------|
| `pom.xml` | Maven build configuration, dependencies, profiles, OSGi bundle instructions |
| `.mvn/jvm.config` | JVM args for Maven (1024m–2048m heap, UTF-8, `--add-opens java.base/java.lang`) |
| `logback-test.xml` | Logback configuration for test execution |
| `.github/workflows/build.yml` | Primary CI pipeline (build, test, deploy, tag, release) |
| `openspec/config.yaml` | OpenSpec spec-driven development workflow configuration |

## Development Environment

**Required:**
- Java 21 JDK
- Maven 3.9.4+ (or use the included `./mvnw` wrapper)

**Surefire JVM args** (configured in pom.xml, applied automatically):
```
--add-opens java.base/java.lang=ALL-UNNAMED
--add-opens java.base/java.util=ALL-UNNAMED
--add-opens java.base/java.time=ALL-UNNAMED
--add-opens java.base/java.net=ALL-UNNAMED
```

## Git Workflow

- **Main Branch:** `develop`
- **Versioning:** CI-friendly `${revision}` property, currently `1.0.4-SNAPSHOT`
- **Branching model:** GitFlow — see [CIFLOW.md](.github/CIFLOW.md) for details
- **Commit rule:** Every commit must reference a JIRA ticket (`JNG-xxx`)
- **PRs:** Use GitHub's forking model; PRs target `develop`

## Important Notes

1. This is a **pure API module** — it contains interfaces and one lightweight implementation (`PayloadImpl`). No persistence, no database access. Downstream runtime modules provide the actual DAO implementations.
2. All DAO operations accept **EMF metaclass types** (`EClass`, `EReference`, `EAttribute`), not Java classes — this is fundamental to JUDO's model-driven architecture.
3. `Payload` extends `Map<String, Object>` — nested maps are automatically wrapped as `Payload` instances on construction via `PayloadImpl`.
4. Fields prefixed with `__$` are **transient** — they are excluded from `equals()`/`hashCode()` comparisons but are still present in the map.
5. The `QueryCustomizer` builder supports filtering, ordering (`OrderBy`), pagination (`Seek` with offset/limit/cursor), field masking, and instance ID filtering.
6. New source files require the EPL 2.0 license header (use `./mvnw -Pupdate-source-code-license process-sources` to auto-generate).
7. The OSGi bundle exports `hu.blackbelt.judo.dao.api*` — all public types in the package are part of the bundle's public API.

## Related Documentation

- [README.md](README.md) — Project introduction and architecture diagram
- [CONTRIBUTING.md](CONTRIBUTING.md) — How to build, test, and submit PRs
- [CIFLOW.md](.github/CIFLOW.md) — CI/CD workflow and branching strategy
- [judo-community](https://github.com/BlackBeltTechnology/judo-community) — Parent ecosystem documentation
