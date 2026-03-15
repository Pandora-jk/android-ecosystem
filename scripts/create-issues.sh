#!/bin/bash
# Script to create GitHub issues from PLANNED_ISSUES.md
# Usage: ./create-issues.sh

REPO="Pandora-jk/android-ecosystem"
GH_TOKEN="${GH_TOKEN:-$(bash /home/ubuntu/.openclaw/workspace/tools/bw-get.sh "github" password)}"

if [ -z "$GH_TOKEN" ]; then
    echo "Error: GitHub token not found"
    exit 1
fi

# Issue 1: core:contracts module
echo "Creating Issue #1: core:contracts module..."
curl -s -X POST "https://api.github.com/repos/$REPO/issues" \
  -H "Authorization: token $GH_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "core:contracts: Create shared Gradle module with URI constants",
    "body": "## Task\nCreate the `core:contracts` Gradle module that defines shared URIs and data classes.\n\n## Acceptance Criteria\n- [ ] Create `core:contracts` module with `build.gradle.kts`\n- [ ] Add `EcosystemContracts` object with URI constants for Contacts, Media, Locations\n- [ ] Add data classes: `Contact`, `Photo`, `Location`\n- [ ] Publish to Maven Local\n\n## Estimated Size\n~100 lines of Kotlin\n\n## Labels\nenhancement, core, contracts",
    "labels": ["enhancement", "core", "contracts"]
  }'

echo ""
echo "Issue #1 created (or failed silently)"

# Issue 2: Contact data class
echo "Creating Issue #2: Contact data class..."
curl -s -X POST "https://api.github.com/repos/$REPO/issues" \
  -H "Authorization: token $GH_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "core:contracts: Add Contact data class with id, name, phone fields",
    "body": "## Task\nCreate the `Contact` data class in the contracts module.\n\n## Acceptance Criteria\n- [ ] Add `data class Contact(val id: Long, val name: String, val phone: String)`\n- [ ] Add Parcelable annotation for cross-process passing\n- [ ] Add unit tests for equality\n\n## Estimated Size\n~20 lines\n\n## Labels\nenhancement, core, contracts",
    "labels": ["enhancement", "core", "contracts"]
  }'

echo ""
echo "Issue #2 created (or failed silently)"

# Issue 3: Photo data class
echo "Creating Issue #3: Photo data class..."
curl -s -X POST "https://api.github.com/repos/$REPO/issues" \
  -H "Authorization: token $GH_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "core:contracts: Add Photo data class with uri, timestamp, location fields",
    "body": "## Task\nCreate the `Photo` data class in the contracts module.\n\n## Acceptance Criteria\n- [ ] Add `data class Photo(val uri: String, val timestamp: Long, val location: Location?)`\n- [ ] Add Parcelable annotation\n- [ ] Add unit tests\n\n## Estimated Size\n~20 lines\n\n## Labels\nenhancement, core, contracts",
    "labels": ["enhancement", "core", "contracts"]
  }'

echo ""
echo "Issue #3 created (or failed silently)"

# Issue 4: Location data class
echo "Creating Issue #4: Location data class..."
curl -s -X POST "https://api.github.com/repos/$REPO/issues" \
  -H "Authorization: token $GH_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "core:contracts: Add Location data class with lat, lon, radius fields",
    "body": "## Task\nCreate the `Location` data class in the contracts module.\n\n## Acceptance Criteria\n- [ ] Add `data class Location(val lat: Double, val lon: Double, val radius: Int = 0)`\n- [ ] Add Parcelable annotation\n- [ ] Add helper method `contains(Location)` for radius check\n\n## Estimated Size\n~25 lines\n\n## Labels\nenhancement, core, contracts",
    "labels": ["enhancement", "core", "contracts"]
  }'

echo ""
echo "Issue #4 created (or failed silently)"

# Issue 5: Signature-level permissions
echo "Creating Issue #5: Signature-level permissions..."
curl -s -X POST "https://api.github.com/repos/$REPO/issues" \
  -H "Authorization: token $GH_TOKEN" \
  -H "Accept: application/vnd.github.v3+json" \
  -H "Content-Type: application/json" \
  -d '{
    "title": "core:contracts: Define signature-level permissions for cross-app access",
    "body": "## Task\nAdd signature-level permissions to prevent unauthorized app access.\n\n## Acceptance Criteria\n- [ ] Add `<permission>` for READ_CONTACTS, WRITE_CONTACTS, READ_MEDIA, WRITE_MEDIA\n- [ ] Set protectionLevel to \"signature\"\n- [ ] Document in ECOSYSTEM-ARCHITECTURE.md\n\n## Estimated Size\n~10 lines in AndroidManifest.xml\n\n## Labels\nenhancement, security, manifest",
    "labels": ["enhancement", "security", "manifest"]
  }'

echo ""
echo "Issue #5 created (or failed silently)"

echo ""
echo "All 5 Phase 1 issues created!"
echo "See PLANNED_ISSUES.md for the remaining 15 issues."
