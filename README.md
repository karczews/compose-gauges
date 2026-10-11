# compose-gauges

A collection of gauge components for Compose Multiplatform.

| Module | Description |
|--------|-------------|
| `lib`  | The published library (`io.github.karczews:compose-gauges`). Targets Android, JVM (desktop), iOS (`iosArm64`, `iosSimulatorArm64`) and Wasm (browser). |
| `demo-shared`  | Unpublished gallery UI shared by all demo runners. Add new gauge demos here. |
| `demo-android`  | Android gallery app. |
| `demo-desktop`  | Desktop (JVM) gallery app. |
| `demo-web`  | Web (wasmJs) gallery app. |
| `iosApp/`  | Xcode wrapper that shows the gallery on iOS. Open in Xcode and run. |
| `screenshot-tests`  | Android module hosting the screenshot tests (not published). |

## Gauges

### SpeedGauge

An analog speedometer (0–260 km/h) with an animated needle and a trip computer strip.

```kotlin
import io.github.karczews.gauges.speed.SpeedGauge

SpeedGauge(speedKmh = 90f)
```

Colors can be customised with `SpeedGaugeDefaults.colors(...)`.

## Requirements

- JDK 17 or newer (the Gradle daemon is provisioned with JDK 25, see `gradle/gradle-daemon-jvm.properties`)
- Android Gradle Plugin `9.5.0-alpha09` or newer (needed for screenshot test suites)
- Xcode, to build the iOS targets

## Building

```sh
./gradlew :lib:allTests                    # library unit tests on every target
./gradlew :demo-android:assembleDebug      # Android gallery
./gradlew :demo-desktop:run                # desktop gallery (opens a window)
./gradlew :demo-web:wasmJsBrowserDevelopmentRun  # web gallery (serves locally)
# iOS gallery: open iosApp/ in Xcode and run
```

## Screenshot tests

Screenshot tests use Compose Preview Screenshot Testing with
[AGP test suites](https://developer.android.com/studio/preview/compose-screenshot-testing-with-testsuites)
(experimental). Two flags in `gradle.properties` turn it on:

```properties
android.experimental.enableScreenshotTest=true
android.experimental.testSuiteSupport=true
```

The KMP Android library plugin (`com.android.kotlin.multiplatform.library`) does not offer the
`screenshotTests` DSL yet, so the suite is declared in `app/build.gradle.kts` and renders the
library's composables. Tests live in `app/src/screenshotTest/kotlin/`. Annotate a `@Composable`
with `@PreviewTest` plus a regular `@Preview`:

```kotlin
@PreviewTest
@Preview(showBackground = true, backgroundColor = 0xFF000000)
@Composable
fun SpeedGaugeCruisingPreview() {
    SpeedGauge(speedKmh = 90f)
}
```

```sh
# Re-render previews and update the reference (golden) images:
./gradlew updateDebugScreenshotTestDefaultTestSuite

# Render fresh screenshots and compare them against the references:
./gradlew testDebugScreenshotTestDefaultTestSuite
```

Reference images are stored under `app/src/screenshotTestDefaultDebug/reference/` and tracked with
Git LFS (run `git lfs install` once). Commit them together with the test that produces them. If a
comparison fails, the HTML report at
`app/build/reports/tests/testDebugScreenshotTestDefaultTestSuite/index.html` shows the expected
image, the actual one, and the diff.

## Publishing

Publishing uses the [vanniktech maven-publish plugin](https://vanniktech.github.io/gradle-maven-publish-plugin/)
to Maven Central. Coordinates come from `GROUP` / `VERSION_NAME` in `gradle.properties` and
`POM_ARTIFACT_ID` in `lib/gradle.properties`.

```sh
./gradlew :lib:publishToMavenLocal

# Maven Central (credentials and signing key passed as Gradle properties, e.g. ORG_GRADLE_PROJECT_* env vars):
#   mavenCentralUsername, mavenCentralPassword, signingInMemoryKey, signingInMemoryKeyPassword
./gradlew :lib:publishToMavenCentral
```

Publications are signed only when `signingInMemoryKey` is set.

## License

[MIT](LICENSE)
