# Android Ecosystem: Highly Connected Architecture

## Core Philosophy: "Data-Centric, Not App-Centric"

Instead of thinking in terms of separate apps (Contacts, Gallery, Map), think in terms of **Data Domains** that any app can access:

- **People Domain** (Contacts, communication history, social links)
- **Media Domain** (Photos, videos, audio - tagged with people, places, events)
- **Location Domain** (Places, routes, check-ins - linked to people and media)
- **Time Domain** (When things happened - links everything together)

Each "app" is just a **view** or **interface** into these interconnected data domains.

---

## Architecture: Shared Data Layer + Specialized UIs

```
┌─────────────────────────────────────────────────────────────┐
│                    USER INTERFACE LAYER                      │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────────┐  │
│  │  Contacts    │  │   Gallery    │  │      Map         │  │
│  │     UI       │  │      UI      │  │       UI         │  │
│  └──────┬───────┘  └──────┬───────┘  └────────┬─────────┘  │
│         │                 │                    │            │
│         └─────────────────┼────────────────────┘            │
│                           │                                  │
│                  ┌────────▼────────┐                         │
│                  │  Intent Bus     │                         │
│                  │  (Navigation)   │                         │
│                  └────────┬────────┘                         │
└───────────────────────────┼─────────────────────────────────┘
                            │
┌───────────────────────────▼─────────────────────────────────┐
│                  SHARED DATA LAYER                           │
│  ┌────────────────┐  ┌────────────────┐  ┌───────────────┐ │
│  │ ContentProvider│  │ ContentProvider│  │ContentProvider│ │
│  │   :contacts    │  │    :media      │  │   :location   │ │
│  └────────┬───────┘  └────────┬───────┘  └───────┬───────┘ │
│           │                   │                   │         │
│  ┌────────▼───────────────────▼───────────────────▼───────┐ │
│  │              Unified Query Engine                       │ │
│  │   (Joins contacts + photos + locations + timestamps)   │ │
│  └─────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────┘
```

---

## Key Design Patterns

### 1. ContentProvider Network (Not Silos)

Each domain exposes data through **ContentProviders** that support complex queries:

```kotlin
// Query: "Show me all photos of John Doe"
// Contacts app queries Media ContentProvider with contact ID
val uri = ContentUris.withAppendedId(
    MediaProvider.PHOTOS_BY_PERSON_URI, 
    contactId
)
val cursor = contentResolver.query(uri, ...)

// Query: "Show me all contacts who were at this location"
// Map app queries Contacts ContentProvider with location coords
val uri = ContactsProvider.CONTACTS_AT_LOCATION_URI
    .buildUpon()
    .appendQueryParameter("lat", lat.toString())
    .appendQueryParameter("lon", lon.toString())
    .build()
```

### 2. Cross-App Intents (Deep Linking)

Apps don't just expose data — they expose **actions**:

```kotlin
// From Contacts app: "View all photos of this contact"
val intent = Intent("com.jimknopf.ecosystem.action.VIEW_PHOTOS_OF_PERSON")
intent.putExtra("person_id", contactId)
intent.putExtra("caller_package", "com.jimknopf.contacts")
startActivity(intent)

// From Gallery app: "Show this photo's location on map"
val intent = Intent("com.jimknopf.ecosystem.action.SHOW_LOCATION_ON_MAP")
intent.putExtra("photo_uri", photoUri)
intent.putExtra("lat", lat)
intent.putExtra("lon", lon)
startActivity(intent)

// From Map app: "Open contact details for this person"
val intent = Intent("com.jimknopf.ecosystem.action.VIEW_CONTACT")
intent.putExtra("contact_id", contactId)
startActivity(intent)
```

### 3. Shared Tagging System

All data items support a unified tagging system:
- **Person Tags**: `person:{contact_id}`
- **Location Tags**: `location:{lat},{lon},{radius}`
- **Time Tags**: `time:{timestamp}` or `time:{start}-{end}`
- **Event Tags**: `event:{event_id}`

This allows any app to query across domains:
- "Show me all photos tagged with `person:123`" → Gallery app
- "Show me all contacts who were at `location:-33.8,151.2,500m`" → Contacts app
- "Show me all media from `time:2024-03-01`" → Gallery app

### 4. Permission Model

Since all apps are from the same developer (signed with same key):
- **Signature-Level Permissions**: Only apps signed with your key can query certain providers
- **User Consent**: First time an app accesses another app's data, show a dialog
- **Granular Control**: User can revoke cross-app access per app pair

---

## Implementation Strategy

### Phase 1: Shared Data Contracts (Week 1)
- [ ] Define `ContentProvider` URIs for all domains
- [ ] Create shared Kotlin module `core:contracts` with:
  - URI constants
  - Data classes (Contact, Photo, Location)
  - Query helper functions
- [ ] Implement signature-level permissions

### Phase 2: Cross-App Queries (Week 2)
- [ ] **Contacts App**: 
  - Add "Photos" tab (queries Media provider for person's photos)
  - Add "Map" tab (queries Location provider for person's places)
- [ ] **Gallery App**:
  - Add "People" view (queries Contacts provider for faces/people in photos)
  - Add "Places" view (groups photos by location)
- [ ] **Map App**:
  - Query all providers for layered view
  - Click marker → deep link to source app

### Phase 3: Unified Search & Timeline (Week 3)
- [ ] Global search across all domains
- [ ] Timeline view (chronological feed of contacts, photos, locations)
- [ ] Smart albums ("Trip to Sydney" = photos + contacts + locations from date range)

### Phase 4: Offline-First Sync (Week 4)
- [ ] Local-first architecture (all data stored locally)
- [ ] Optional sync to Nextcloud/WebDAV
- [ ] Conflict resolution for multi-device edits

---

## Example User Flows

### Flow 1: "Show me photos of Alice"
1. User opens **Contacts** app
2. Taps on "Alice"
3. Sees "Photos" tab (auto-populated from Gallery's ContentProvider)
4. Taps photo → opens in **Gallery** app with full context

### Flow 2: "Where have I been with Bob?"
1. User opens **Map** app
2. Taps "People" layer → selects "Bob"
3. Map shows all locations where Bob's contact data overlaps with photo timestamps
4. Taps location pin → sees photos taken there with Bob

### Flow 3: "Create album from this event"
1. User opens **Gallery** app
2. Selects multiple photos from a date range
3. Taps "Create Event" → auto-tags with location and date
4. **Contacts** app now shows these photos under attendees' profiles

---

## Technical Implementation Details

### Shared Module: `core:contracts`
```kotlin
// Shared across all apps - defines the "API" between apps
object EcosystemContracts {
    // Contacts Provider
    object Contacts {
        val CONTENT_URI = Uri.parse("content://com.jimknopf.contacts.provider/contacts")
        val PHOTO_URI = Uri.parse("content://com.jimknopf.contacts.provider/contacts/{id}/photos")
        val LOCATION_URI = Uri.parse("content://com.jimknopf.contacts.provider/contacts/{id}/locations")
    }
    
    // Media Provider
    object Media {
        val CONTENT_URI = Uri.parse("content://com.jimknopf.gallery.provider/media")
        val BY_PERSON_URI = Uri.parse("content://com.jimknopf.gallery.provider/media/by-person/{person_id}")
        val BY_LOCATION_URI = Uri.parse("content://com.jimknopf.gallery.provider/media/by-location")
    }
    
    // Location Provider
    object Locations {
        val CONTENT_URI = Uri.parse("content://com.jimknopf.map.provider/locations")
        val CONTACTS_AT_URI = Uri.parse("content://com.jimknopf.map.provider/locations/{lat}/{lon}/contacts")
    }
}
```

### App Dependencies
Each app depends on:
- `core:contracts` (shared URIs and data classes)
- `core:database` (local storage)
- Other apps via `implementation(project(":apps:contacts"))` (for ContentProvider access only)

### Security
```xml
<!-- In AndroidManifest.xml of each app -->
<provider
    android:name=".provider.ContactsProvider"
    android:authorities="com.jimknopf.contacts.provider"
    android:exported="true"
    android:readPermission="com.jimknopf.ecosystem.permission.READ_CONTACTS"
    android:writePermission="com.jimknopf.ecosystem.permission.WRITE_CONTACTS" />

<!-- Signature-level permission (only apps signed with same key can access) -->
<permission
    android:name="com.jimknopf.ecosystem.permission.READ_CONTACTS"
    android:protectionLevel="signature" />
```

---

## Benefits of This Approach

✅ **Truly Modular**: Install only what you need, but everything works together
✅ **No Data Duplication**: Single source of truth per domain
✅ **Rich Cross-App Features**: "Photos of contacts" without merging apps
✅ **Offline-First**: All data stored locally, sync is optional
✅ **FLOSS-Compatible**: No Google dependencies, works with Obtanium
✅ **Extensible**: Add new apps (Notes, Tasks, Finance) that plug into existing data

---

## Next Steps

1. **Create `core:contracts` module** with all URI constants and data classes
2. **Refactor Contacts app** to expose ContentProvider with photo queries
3. **Refactor Gallery app** to expose ContentProvider with person/location queries
4. **Implement deep linking** for cross-app navigation
5. **Build Map app** as a consumer of all providers

**Ready to implement?** I'll start with the `core:contracts` module and the ContentProvider refactor for Contacts.
