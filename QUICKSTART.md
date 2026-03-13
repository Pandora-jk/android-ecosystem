# Quick Start - Test Your First Install

## Option 1: Build on Your Machine (Recommended for testing)

```bash
cd ~/android-ecosystem

# Build debug APK (faster, no signing needed)
./gradlew :apps:contacts:assembleDebug

# Install to connected device/emulator
./gradlew :apps:contacts:installDebug

# Or manually install APK from:
# apps/contacts/build/outputs/apk/debug/contacts-debug.apk
```

## Option 2: Build Release APK (for distribution)

```bash
cd ~/android-ecosystem

# Build release APK
./gradlew :apps:contacts:assembleRelease

# APK location:
# apps/contacts/build/outputs/apk/release/contacts-1.0.0.apk
```

## Option 3: Push to GitHub & Use GitHub Actions

1. **Create repo on GitHub:**
   ```bash
   git remote add origin https://github.com/jimknopf8jk/android-ecosystem.git
   git push -u origin master
   ```

2. **GitHub Actions will auto-build** (CI config coming next)

3. **Download APK from Releases** or use Obtanium to install

## Next Steps

- [ ] Add GitHub remote and push
- [ ] Create GitHub Actions CI workflow
- [ ] Add actual contact list UI (currently placeholder)
- [ ] Set up Obtanium auto-update metadata

## Key Commands

| Task | Command |
|------|---------|
| Build debug | `./gradlew :apps:contacts:assembleDebug` |
| Build release | `./gradlew :apps:contacts:assembleRelease` |
| Install on device | `./gradlew :apps:contacts:installDebug` |
| Clean build | `./gradlew clean` |
| Test app only | `./gradlew :apps:contacts:test` |
