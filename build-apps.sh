#!/bin/bash
# Build all apps in the ecosystem

set -e

echo "🔨 Building Android Ecosystem Apps..."

# Build Contacts app
echo "Building Contacts app..."
./gradlew :apps:contacts:assembleRelease

echo ""
echo "✅ Build complete!"
echo "📦 APK location: apps/contacts/build/outputs/apk/release/"
ls -lh apps/contacts/build/outputs/apk/release/*.apk 2>/dev/null || echo "⚠️  No APK found (build may have failed)"
