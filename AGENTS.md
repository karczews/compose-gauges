# AGENTS.md

Kotlin / Compose Multiplatform gauge library. `./gradlew projects` is the source of truth for module structure (IDE state can go stale — re-sync rather than trusting orphan modules like a phantom `:app`).

## Layout

- `lib/` — the published library (`io.github.karczews:compose-gauges`). Android, JVM, iOS (`iosArm64`, `iosSimulatorArm64`), wasmJs.
- `demo-shared/` — shared gallery UI. New gauge demos go here.
- `demo-android/`, `demo-desktop/`, `demo-web/` — thin gallery runners. `iosApp/` — Xcode wrapper (open in Xcode, run).
- `screenshot-tests/` — Android app module hosting the screenshot suite. The KMP Android library plugin has no `screenshotTests` DSL, so the suite lives here and renders `lib` composables.

## Verify (run before opening a PR)

```sh
./gradlew :lib:allTests
./gradlew :demo-android:assembleDebug
./gradlew :demo-desktop:build
./gradlew :demo-web:compileDevelopmentExecutableKotlinWasmJs
./gradlew testDebugScreenshotTestDefaultTestSuite
```

Never run `:demo-desktop:run` or `wasmJsBrowserDevelopmentRun` here — they open a window / serve locally. Use `:demo-desktop:build`.

## Screenshots

- Tests live in `screenshot-tests/src/screenshotTest/kotlin/`, annotated `@PreviewTest` + a regular `@Preview`.
- `./gradlew updateDebugScreenshotTestDefaultTestSuite` re-renders goldens under `screenshot-tests/src/screenshotTestDefaultDebug/reference/` — commit them in the same commit as the change that caused them. Images are Git LFS (`git lfs install` once).
- Failure report: `screenshot-tests/build/reports/tests/testDebugScreenshotTestDefaultTestSuite/index.html`.

## New gauge recipe

Package under `lib/src/commonMain/kotlin/io/github/karczews/gauges/` + a `GaugeDemo` in `demo-shared` + `@PreviewTest` screenshot tests in `screenshot-tests/src/screenshotTest/`.

## Gotchas

- AGP is `9.5.0-alpha09`+ (required for screenshot test suites). Its Compose Preview task hard-fails sync when a variant lacks unit tests or Android resources — the `unitTests.isIncludeAndroidResources` + `HasUnitTestBuilder.enableUnitTest` blocks in `demo-android` / `screenshot-tests` `build.gradle.kts` are load-bearing, do not remove them.
- Requirements: JDK 17+, Android SDK (`local.properties`, gitignored), Xcode for iOS targets.

## Commits

Conventional Commits 1.0.0 — required, enforced per CONTRIBUTING.md. Read the "Commit messages" section of `CONTRIBUTING.md` before committing (type/scope table, imperative lowercase description).
