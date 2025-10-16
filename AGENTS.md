# Repository Guidelines

## Project Structure & Module Organization
- `src/main/java`: Spring Boot application code. Key packages: `api` (controllers, DTOs, messages), `domain` (models, services, repositories), `infrastructure` (config, helpers).
- `src/main/resources`: `application.properties`, profile config (e.g., `application-dev.properties`), Flyway migrations under `db/migration`.
- `src/test/java`: JUnit 5 tests (uses `@ActiveProfiles("dev")` when needed).
- `docs/`: Internal documentation. Root contains `pom.xml`, `Dockerfile`, and `docker-compose.yml`.

## Build, Test, and Development Commands
- Build: `./mvnw clean install` (Windows: `mvnw.cmd clean install`).
- Run (dev, H2): `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`.
- Run (PostgreSQL): set `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`, then `./mvnw spring-boot:run`.
- Tests: `./mvnw test`.
- Docker (app + db): `docker-compose up --build`.

## Coding Style & Naming Conventions
- Java 21, Spring Boot 3. Use 4‑space indentation and standard Java formatting.
- Packages: lowercase dot notation; classes: `PascalCase`; methods/fields: `camelCase`; constants: `UPPER_SNAKE_CASE`.
- Common suffixes: DTOs `*Response`, services `*Service`, repositories `*Repository`, enums `*Status`.
- Prefer Lombok for boilerplate (getters, builders) where already used.

## Testing Guidelines
- Framework: JUnit 5 via `spring-boot-starter-test`.
- Place tests mirroring source packages; name files `*Tests.java`.
- Integration tests: `@SpringBootTest`; activate H2 with `@ActiveProfiles("dev")`.
- Run: `./mvnw test`. Add focused unit tests for domain services and helpers.

## Commit & Pull Request Guidelines
- Commits: short, imperative, and scoped (English or Portuguese). Example: `Implement async upload flow` or `corrige validação de células`.
- Reference issues in messages/PRs (`Fixes #123`). Separate refactors from behavior changes when possible.
- PRs: include summary, motivation, testing steps (profiles/env vars), and any relevant logs/screenshots. Update docs and migrations as needed.

## Security & Configuration Tips
- Do not commit credentials. Use env vars: `SPRING_DATASOURCE_*` and `PROCESSAMENTO_LIMITE_REGISTROS`.
- Profiles: default uses PostgreSQL; `dev` uses in‑memory H2. Flyway runs on startup—add new `Vx__Description.sql` files; do not edit applied migrations.
