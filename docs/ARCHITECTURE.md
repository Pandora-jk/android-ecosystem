# Android Ecosystem Architecture

## Overview

This is a **modular monorepo** containing multiple independent Android apps that share core libraries. Each app can be built, versioned, and released separately while benefiting from shared functionality.

## Structure

```
android-ecosystem/
├── apps/                    # Independent applications
│   └── contacts/           # Contacts app (v1.0.0)
│       ├── build.gradle.kts
│       └── src/
│
├── core/                    # Shared libraries
│   ├── database/           # Room database, data layer
│   ├── map/               # Map visualization components
│   ├── media/             # Photo/media handling
│   └── sync/              # Sync engine
│
├── docs/                   # Documentation
├── build.gradle.kts        # Root build config
└── settings.gradle.kts     # Project settings
```

## Key Principles

### 1. **App Independence**
- Each app has its own `applicationId`
- Apps can be built/released independently
- Version numbers are per-app
- No cross-app dependencies

### 2. **Shared Core**
- `core/*` modules are libraries (not apps)
- All apps can depend on core modules
- Core modules cannot depend on apps
- Shared code lives in core to avoid duplication

### 3. **Build System**
- Gradle Kotlin DSL (`.kts` files)
- JDK 17, Kotlin 2.0
- Android Gradle Plugin 8.7.0
- Min SDK: 26 (Android 8.0)
- Target SDK: 34 (Android 14)

## Build Commands

### Build Specific App
```bash
# Debug
./gradlew :apps:contacts:assembleDebug

# Release
./gradlew :apps:contacts:assembleRelease
```

### Build All Apps
```bash
./gradlew assembleRelease
```

### Install to Device
```bash
./gradlew :apps:contacts:installDebug
```

## Adding a New App

1. **Create app directory:**
   ```bash
   mkdir -p apps/myapp/src/main/java/com/jimknopf/myapp
   ```

2. **Add `build.gradle.kts`:**
   ```kotlin
   plugins {
       id("com.android.application")
       id("org.jetbrains.kotlin.android")
   }

   android {
       namespace = "com.jimknopf.myapp"
       compileSdk = 34
       
       defaultConfig {
           applicationId = "com.jimknopf.myapp"
           minSdk = 26
           targetSdk = 34
           versionCode = 1
           versionName = "1.0.0"
       }
   }
   ```

3. **Add to `settings.gradle.kts`:**
   ```kotlin
   include(":apps:myapp")
   ```

4. **Build:**
   ```bash
   ./gradlew :apps:myapp:assembleRelease
   ```

## Versioning

Each app maintains its own version:
- `versionCode`: Integer (increment for each release)
- `versionName`: SemVer string (e.g., "1.0.0")

Example release workflow:
```bash
# 1. Update version in build.gradle.kts
# 2. Build release
./gradlew :apps:contacts:assembleRelease

# 3. Tag release
git tag contacts-v1.0.0
git push origin contacts-v1.0.0

# 4. APK is auto-published to GitHub Releases
```

## CI/CD

GitHub Actions workflow (`.github/workflows/android-ci.yml`):
- Builds on every push/PR
- Uploads debug APKs as artifacts
- Creates GitHub Release on version tags
- Runs on Ubuntu with JDK 17

## Security

- No secrets in code (use `local.properties` or env vars)
- ProGuard enabled for release builds
- Min SDK 26+ (modern security features)
- Regular dependency updates required

## Testing

```bash
# Run unit tests
./gradlew test

# Run instrumented tests
./gradlew connectedAndroidTest
```

## License

MIT - Jim Knopf
