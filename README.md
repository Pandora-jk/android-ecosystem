# Android Contacts App

Signed Android Contacts APK releases with Material You support and Obtainium-compatible GitHub Releases.

## Install

### Obtainium

Use this repository URL in Obtainium:

```text
https://github.com/Pandora-jk/android-ecosystem
```

Recommended settings for the stable alpha channel from `master`:

- Source: `GitHub`
- Include prereleases: `On`
- Asset filter: `^contacts-alpha-[0-9.]+\.apk$`

Optional test channel for `feat/contacts-alpha`:

- Source: `GitHub`
- Include prereleases: `On`
- Asset filter: `^contacts-alpha-[0-9.]+-test-contacts-alpha\.[0-9]{2}\.apk$`

## Releases

- Releases page: `https://github.com/Pandora-jk/android-ecosystem/releases`
- Package name: `com.jimknopf.contacts`
- Delivery: signed APKs attached to GitHub prereleases

## App

Current focus is the Contacts app in `apps/contacts`.

Features:

- Material You styling
- Contact CRUD
- Favorites and groups
- Search
- GitHub Release delivery for direct install and updates

## Build

Requirements:

- Java 17
- Android SDK

Build debug:

```bash
./gradlew :apps:contacts:assembleDebug
```

Build release:

```bash
./gradlew :apps:contacts:assembleRelease
```

## Repo Layout

```text
android-ecosystem/
├── apps/
│   └── contacts/
├── core/
│   ├── contracts/
│   ├── database/
│   ├── map/
│   ├── media/
│   └── sync/
└── docs/
```

## License

MIT
