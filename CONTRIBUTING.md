# Contributing

Thanks for helping improve compose-gauges! This guide covers the development workflow and the
commit message convention used in this repository.

## Development setup

1. Install JDK 17 or newer, the Android SDK, and Xcode (for the iOS targets).
2. Install [Git LFS](https://git-lfs.com) and run `git lfs install` once. All images
   (`png`, `webp`, `jpg`, `gif`), including screenshot reference images, are stored in LFS.
3. Clone the repository and open it in Android Studio or IntelliJ IDEA.

## Before opening a pull request

```sh
./gradlew :lib:allTests                         # library unit tests on every target
./gradlew :demo-android:assembleDebug           # gallery app builds
./gradlew testDebugScreenshotTestDefaultTestSuite # screenshots match the references
```

If you changed how a gauge looks on purpose, regenerate the reference images and commit them in
the same commit as the change that caused them:

```sh
./gradlew updateDebugScreenshotTestDefaultTestSuite
```

New gauges go in their own package under `lib/src/commonMain/kotlin/io/github/karczews/gauges/`,
and need a `GaugeDemo` in `demo-shared` plus `@PreviewTest` screenshot tests in
`screenshot-tests/src/screenshotTest/`.

## Commit messages

This project follows [Conventional Commits 1.0.0](https://www.conventionalcommits.org/en/v1.0.0/).
Every commit message is structured as:

```
<type>[optional scope]: <description>

[optional body]

[optional footer(s)]
```

- **description**: imperative mood, lower case, no trailing period, about 72 characters or less
  (`add needle color option`, not `Added needle color option.`).
- **body**: explain *what* changed and *why*, wrapped at 72 characters.
- **footer**: references such as `Closes #12`, or a `BREAKING CHANGE:` note.

### Types

| Type       | Use for                                                        |
|------------|----------------------------------------------------------------|
| `feat`     | A new feature, e.g. a new gauge or a new public API parameter   |
| `fix`      | A bug fix                                                       |
| `docs`     | Documentation only (README, KDoc, this guide)                   |
| `style`    | Formatting changes that do not affect behaviour                 |
| `refactor` | Code changes that neither fix a bug nor add a feature           |
| `perf`     | Performance improvements                                        |
| `test`     | Adding or updating tests, including screenshot references       |
| `build`    | Build system or dependency changes (Gradle, version catalog)    |
| `ci`       | CI configuration                                                |
| `chore`    | Maintenance that fits nowhere else (gitignore, tooling)         |
| `revert`   | Reverting a previous commit                                     |

### Scopes

A scope is optional. When used, name the area that changed:

- a gauge, e.g. `speed-gauge`
- `lib`, for library-wide changes
- `demo`, for the gallery demo
- `screenshot`, for the screenshot test setup
- `screenshots`, for the screenshot test setup

### Breaking changes

Changes to the published API that are not backwards compatible must be marked, either with `!`
after the type/scope or with a `BREAKING CHANGE:` footer (or both):

```
feat(speed-gauge)!: replace outsideTempC string with a temperature value

BREAKING CHANGE: SpeedGauge now takes `outsideTemp: Float?` instead of
`outsideTempC: String`; the gauge formats the value itself.
```

### Examples

```
feat(speed-gauge): allow customising the maximum speed
fix(speed-gauge): keep needle within the dial for negative speeds
test(speed-gauge): add screenshot for custom colors
build: bump Compose Multiplatform to 1.12.1
docs: describe publishing in the README
```
