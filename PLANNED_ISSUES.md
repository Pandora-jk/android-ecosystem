# Android Ecosystem - Planned Issues (Smallest Possible Items)

## Phase 1: Shared Data Contracts (Week 1)

### Issue #1: [core:contracts] Create shared module with URI constants
**Title:** `core:contracts: Create shared Gradle module with URI constants`
**Body:**
```
## Task
Create the `core:contracts` Gradle module that defines shared URIs and data classes.

## Acceptance Criteria
- [ ] Create `core:contracts` module with `build.gradle.kts`
- [ ] Add `EcosystemContracts` object with URI constants for Contacts, Media, Locations
- [ ] Add data classes: `Contact`, `Photo`, `Location`
- [ ] Publish to Maven Local

## Estimated Size
~100 lines of Kotlin

## Labels
enhancement, core, contracts
```

---

### Issue #2: [core:contracts] Add Contact data class
**Title:** `core:contracts: Add Contact data class with id, name, phone fields`
**Body:**
```
## Task
Create the `Contact` data class in the contracts module.

## Acceptance Criteria
- [ ] Add `data class Contact(val id: Long, val name: String, val phone: String)`
- [ ] Add Parcelable annotation for cross-process passing
- [ ] Add unit tests for equality

## Estimated Size
~20 lines

## Labels
enhancement, core, contracts
```

---

### Issue #3: [core:contracts] Add Photo data class
**Title:** `core:contracts: Add Photo data class with uri, timestamp, location fields`
**Body:**
```
## Task
Create the `Photo` data class in the contracts module.

## Acceptance Criteria
- [ ] Add `data class Photo(val uri: String, val timestamp: Long, val location: Location?)`
- [ ] Add Parcelable annotation
- [ ] Add unit tests

## Estimated Size
~20 lines

## Labels
enhancement, core, contracts
```

---

### Issue #4: [core:contracts] Add Location data class
**Title:** `core:contracts: Add Location data class with lat, lon, radius fields`
**Body:**
```
## Task
Create the `Location` data class in the contracts module.

## Acceptance Criteria
- [ ] Add `data class Location(val lat: Double, val lon: Double, val radius: Int = 0)`
- [ ] Add Parcelable annotation
- [ ] Add helper method `contains(Location)` for radius check

## Estimated Size
~25 lines

## Labels
enhancement, core, contracts
```

---

### Issue #5: [core:contracts] Add signature-level permissions to AndroidManifest
**Title:** `core:contracts: Define signature-level permissions for cross-app access`
**Body:**
```
## Task
Add signature-level permissions to prevent unauthorized app access.

## Acceptance Criteria
- [ ] Add `<permission>` for READ_CONTACTS, WRITE_CONTACTS, READ_MEDIA, WRITE_MEDIA
- [ ] Set protectionLevel to "signature"
- [ ] Document in ECOSYSTEM-ARCHITECTURE.md

## Estimated Size
~10 lines in AndroidManifest.xml

## Labels
enhancement, security, manifest
```

---

## Phase 2: Cross-App Queries (Week 2)

### Issue #6: [app:contacts] Expose ContentProvider for contacts data
**Title:** `app:contacts: Implement ContentProvider for contacts data`
**Body:**
```
## Task
Make contacts queryable by other ecosystem apps.

## Acceptance Criteria
- [ ] Create `ContactsProvider` class extending `ContentProvider`
- [ ] Implement `query()`, `getType()`, `getUriMatcher()`
- [ ] Support URI: `content://com.jimknopf.contacts.provider/contacts`
- [ ] Return Cursor with columns: _id, name, phone

## Estimated Size
~80 lines

## Labels
enhancement, contacts, contentprovider
```

---

### Issue #7: [app:contacts] Add "Photos" tab to ContactDetailActivity
**Title:** `app:contacts: Add Photos tab showing contact's photos from Gallery`
**Body:**
```
## Task
Query Media ContentProvider for photos of the current contact.

## Acceptance Criteria
- [ ] Add "Photos" tab in ContactDetailActivity
- [ ] Query Media.CONTENT_URI with contact_id parameter
- [ ] Display photos in RecyclerView
- [ ] Tap photo → open in Gallery app

## Estimated Size
~60 lines

## Labels
enhancement, contacts, ui
```

---

### Issue #8: [app:contacts] Add "Map" tab showing contact's locations
**Title:** `app:contacts: Add Map tab showing locations where contact was`
**Body:**
```
## Task
Query Location ContentProvider for places the contact visited.

## Acceptance Criteria
- [ ] Add "Map" tab in ContactDetailActivity
- [ ] Query Locations.CONTENT_URI with contact_id
- [ ] Show markers on embedded map
- [ ] Tap marker → open in Map app

## Estimated Size
~60 lines

## Labels
enhancement, contacts, ui, map
```

---

### Issue #9: [app:gallery] Expose ContentProvider for media data
**Title:** `app:gallery: Implement ContentProvider for media library`
**Body:**
```
## Task
Make media queryable by other ecosystem apps.

## Acceptance Criteria
- [ ] Create `MediaProvider` class extending `ContentProvider`
- [ ] Support URIs:
  - `content://com.jimknopf.gallery.provider/media`
  - `content://com.jimknopf.gallery.provider/media/by-person/{person_id}`
  - `content://com.jimknopf.gallery.provider/media/by-location`
- [ ] Implement query() with proper URI matching

## Estimated Size
~100 lines

## Labels
enhancement, gallery, contentprovider
```

---

### Issue #10: [app:gallery] Add "People" view grouping photos by person
**Title:** `app:gallery: Add People view grouping photos by contact`
**Body:**
```
## Task
Query Contacts ContentProvider to show photos grouped by person.

## Acceptance Criteria
- [ ] Add "People" tab in Gallery
- [ ] Query Contacts provider for people in photos
- [ ] Group photos by person
- [ ] Tap person → filter to their photos

## Estimated Size
~80 lines

## Labels
enhancement, gallery, ui
```

---

### Issue #11: [app:gallery] Add "Places" view grouping photos by location
**Title:** `app:gallery: Add Places view grouping photos by location`
**Body:**
```
## Task
Group photos by location coordinates.

## Acceptance Criteria
- [ ] Add "Places" tab in Gallery
- [ ] Query Location provider for photo locations
- [ ] Cluster photos by location
- [ ] Tap cluster → show photos at that location

## Estimated Size
~80 lines

## Labels
enhancement, gallery, ui
```

---

### Issue #12: [app:map] Create Map app skeleton
**Title:** `app:map: Create Map app with basic map view`
**Body:**
```
## Task
Create the Map app that displays locations on a map.

## Acceptance Criteria
- [ ] Create `apps:map` module
- [ ] Add basic map view (OSMDroid or MapsForge for FLOSS)
- [ ] Display user's current location
- [ ] Request location permissions

## Estimated Size
~150 lines

## Labels
enhancement, map, skeleton
```

---

### Issue #13: [app:map] Add "People" layer showing contacts on map
**Title:** `app:map: Add People layer showing contact locations`
**Body:**
```
## Task
Query Contacts provider to show where contacts were.

## Acceptance Criteria
- [ ] Add "People" layer toggle
- [ ] Query Contacts provider for contact locations
- [ ] Show markers for each contact
- [ ] Tap marker → open Contact app

## Estimated Size
~60 lines

## Labels
enhancement, map, ui
```

---

### Issue #14: [app:map] Add "Photos" layer showing photo locations
**Title:** `app:map: Add Photos layer showing where photos were taken`
**Body:**
```
## Task
Query Media provider to show photo locations on map.

## Acceptance Criteria
- [ ] Add "Photos" layer toggle
- [ ] Query Media provider for photo locations
- [ ] Show photo count markers
- [ ] Tap marker → open Gallery app with location filter

## Estimated Size
~60 lines

## Labels
enhancement, map, ui
```

---

## Phase 3: Cross-App Navigation (Week 3)

### Issue #15: [core:intents] Create Intent helper object
**Title:** `core:intents: Create helper for cross-app intents`
**Body:**
```
## Task
Standardize cross-app navigation intents.

## Acceptance Criteria
- [ ] Create `EcosystemIntents` helper object
- [ ] Add methods:
  - `viewContact(contactId: Long)`
  - `viewPhoto(photoUri: String)`
  - `viewLocation(lat: Double, lon: Double)`
  - `viewPhotosOfPerson(personId: Long)`
- [ ] Handle missing apps gracefully

## Estimated Size
~50 lines

## Labels
enhancement, core, intents
```

---

### Issue #16: [app:contacts] Add deep link handling for contact URIs
**Title:** `app:contacts: Handle deep links to contact:// URIs`
**Body:**
```
## Task
Allow other apps to open specific contacts.

## Acceptance Criteria
- [ ] Add intent-filter for `contact://` URIs
- [ ] Parse URI and open ContactDetailActivity
- [ ] Handle missing contact gracefully

## Estimated Size
~30 lines

## Labels
enhancement, contacts, deeplink
```

---

### Issue #17: [app:gallery] Add deep link handling for photo URIs
**Title:** `app:gallery: Handle deep links to photo:// URIs`
**Body:**
```
## Task
Allow other apps to open specific photos.

## Acceptance Criteria
- [ ] Add intent-filter for `photo://` URIs
- [ ] Parse URI and open PhotoDetailActivity
- [ ] Handle missing photo gracefully

## Estimated Size
~30 lines

## Labels
enhancement, gallery, deeplink
```

---

### Issue #18: [app:map] Add deep link handling for location URIs
**Title:** `app:map: Handle deep links to location:// URIs`
**Body:**
```
## Task
Allow other apps to open specific locations.

## Acceptance Criteria
- [ ] Add intent-filter for `location://lat,lon` URIs
- [ ] Parse URI and center map
- [ ] Add marker if coordinates provided

## Estimated Size
~30 lines

## Labels
enhancement, map, deeplink
```

---

## Phase 4: Testing & Polish (Week 4)

### Issue #19: [testing] Add integration tests for cross-app queries
**Title:** `testing: Add integration tests for cross-ContentProvider queries`
**Body:**
```
## Task
Test that apps can query each other's data.

## Acceptance Criteria
- [ ] Test Contacts → Media query
- [ ] Test Gallery → Contacts query
- [ ] Test Map → all providers query
- [ ] Test permission denial scenarios

## Estimated Size
~100 lines of test code

## Labels
testing, integration
```

---

### Issue #20: [docs] Document cross-app architecture
**Title:** `docs: Document cross-app query patterns and URIs`
**Body:**
```
## Task
Document the ecosystem for future maintainers.

## Acceptance Criteria
- [ ] Document all ContentProvider URIs
- [ ] Document all intent actions
- [ ] Add sequence diagrams for common flows
- [ ] Add troubleshooting guide

## Estimated Size
~200 lines of markdown

## Labels
documentation
```

---

## Summary
- **Total Issues:** 20
- **Smallest Possible:** Each issue is <100 lines of code
- **Sequential:** Can be done in order
- **Testable:** Each has clear acceptance criteria
- **Independent:** Minimal dependencies between issues
