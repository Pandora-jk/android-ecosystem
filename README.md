# Android Ecosystem - Modular Monorepo

A modular Android repository structure for building multiple independent apps with shared core libraries.

## Structure

```
android-ecosystem/
├── apps/                 # Independent apps
│   └── contacts/        # Contacts app (first release)
│
├── core/                # Shared libraries
│   ├── database/       # Room DB, used by ALL apps
│   ├── map/           # Map visualization
│   ├── media/         # Photo/media handling
│   └── sync/          # Sync engine
│
└── docs/               # Architecture documentation
```

## Build Commands

### Build Contacts App (Debug)
```bash
./gradlew :apps:contacts:assembleDebug
```

### Build Contacts App (Release)
```bash
./gradlew :apps:contacts:assembleRelease
```

### Build All Apps
```bash
./gradlew assembleRelease
```

### Install on Device
```bash
./gradlew :apps:contacts:installDebug
```

## Release Workflow

Each app is versioned independently:

1. Update version in `apps/contacts/build.gradle.kts`:
   ```kotlin
   versionCode = 1
   versionName = "1.0.0"
   ```

2. Build release APK:
   ```bash
   ./gradlew :apps:contacts:assembleRelease
   ```

3. APK output: `apps/contacts/build/outputs/apk/release/contacts-1.0.0.apk`

4. Tag release: `git tag contacts-v1.0.0`

## Adding New Apps

1. Create `apps/<appname>/` directory
2. Add `build.gradle.kts` with unique `applicationId`
3. Add to `settings.gradle.kts`: `include(":apps:<appname>")`
4. Build: `./gradlew :apps:<appname>:assembleRelease`

## License

MIT - Jim Knopf
