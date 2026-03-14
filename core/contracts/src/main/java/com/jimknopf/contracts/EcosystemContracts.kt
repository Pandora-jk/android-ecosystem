package com.jimknopf.contracts

import android.net.Uri

/**
 * Ecosystem Contracts - Shared URIs and constants for cross-app communication.
 * 
 * All apps in the ecosystem use these contracts to query each other's data
 * via ContentProviders. This enables features like:
 * - Showing photos of a specific contact in the Contacts app
 * - Showing contacts at a specific location in the Map app
 * - Unified search across all data domains
 */
object EcosystemContracts {
    
    // Base authority for all ecosystem providers
    const val AUTHORITY = "com.jimknopf.ecosystem.provider"
    
    // Signature permission for cross-app access
    const val READ_PERMISSION = "com.jimknopf.ecosystem.permission.READ"
    const val WRITE_PERMISSION = "com.jimknopf.ecosystem.permission.WRITE"
    
    /**
     * Contacts Domain
     * Exposes: name, phone, email, photo URIs, location history
     */
    object Contacts {
        const val AUTHORITY = "com.jimknopf.contacts.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/contacts")
        
        // Query all contacts
        val ALL_URI: Uri = Uri.parse("content://$AUTHORITY/contacts")
        
        // Query contacts by ID
        fun byId(id: Long): Uri = Uri.parse("content://$AUTHORITY/contacts/$id")
        
        // Query photos of a specific contact (joins with Media provider)
        fun photosOf(id: Long): Uri = Uri.parse("content://$AUTHORITY/contacts/$id/photos")
        
        // Query locations where this contact has been (joins with Location provider)
        fun locationsOf(id: Long): Uri = Uri.parse("content://$AUTHORITY/contacts/$id/locations")
        
        // Query contacts at a specific location
        fun atLocation(lat: Double, lon: Double, radiusMeters: Int = 100): Uri {
            return Uri.parse("content://$AUTHORITY/contacts/at-location")
                .buildUpon()
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("radius", radiusMeters.toString())
                .build()
        }
        
        // Column names
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_PHOTO_URI = "photo_uri"
        const val COLUMN_LAST_SEEN = "last_seen"
        const val COLUMN_LAST_LOCATION = "last_location"
    }
    
    /**
     * Media Domain (Photos, Videos, Audio)
     * Exposes: media URI, thumbnail, timestamp, location, person tags
     */
    object Media {
        const val AUTHORITY = "com.jimknopf.gallery.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/media")
        
        // Query all media
        val ALL_URI: Uri = Uri.parse("content://$AUTHORITY/media")
        
        // Query media by ID
        fun byId(id: Long): Uri = Uri.parse("content://$AUTHORITY/media/$id")
        
        // Query media by person (tagged with contact ID)
        fun byPerson(personId: Long): Uri = Uri.parse("content://$AUTHORITY/media/by-person/$personId")
        
        // Query media by location
        fun byLocation(lat: Double, lon: Double, radiusMeters: Int = 100): Uri {
            return Uri.parse("content://$AUTHORITY/media/by-location")
                .buildUpon()
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("radius", radiusMeters.toString())
                .build()
        }
        
        // Query media by date range
        fun byDateRange(startMs: Long, endMs: Long): Uri {
            return Uri.parse("content://$AUTHORITY/media/by-date")
                .buildUpon()
                .appendQueryParameter("start", startMs.toString())
                .appendQueryParameter("end", endMs.toString())
                .build()
        }
        
        // Column names
        const val COLUMN_ID = "_id"
        const val COLUMN_URI = "media_uri"
        const val COLUMN_THUMBNAIL_URI = "thumbnail_uri"
        const val COLUMN_TYPE = "type" // PHOTO, VIDEO, AUDIO
        const val COLUMN_TIMESTAMP = "timestamp"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
        const val COLUMN_PERSON_IDS = "person_ids" // Comma-separated list of tagged person IDs
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
    }
    
    /**
     * Location Domain
     * Exposes: places, check-ins, routes, linked contacts and media
     */
    object Locations {
        const val AUTHORITY = "com.jimknopf.map.provider"
        val CONTENT_URI: Uri = Uri.parse("content://$AUTHORITY/locations")
        
        // Query all locations
        val ALL_URI: Uri = Uri.parse("content://$AUTHORITY/locations")
        
        // Query location by ID
        fun byId(id: Long): Uri = Uri.parse("content://$AUTHORITY/locations/$id")
        
        // Query contacts at location
        fun contactsAt(lat: Double, lon: Double, radiusMeters: Int = 100): Uri {
            return Uri.parse("content://$AUTHORITY/locations/contacts-at")
                .buildUpon()
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("radius", radiusMeters.toString())
                .build()
        }
        
        // Query media at location
        fun mediaAt(lat: Double, lon: Double, radiusMeters: Int = 100): Uri {
            return Uri.parse("content://$AUTHORITY/locations/media-at")
                .buildUpon()
                .appendQueryParameter("lat", lat.toString())
                .appendQueryParameter("lon", lon.toString())
                .appendQueryParameter("radius", radiusMeters.toString())
                .build()
        }
        
        // Column names
        const val COLUMN_ID = "_id"
        const val COLUMN_NAME = "name"
        const val COLUMN_LATITUDE = "latitude"
        const val COLUMN_LONGITUDE = "longitude"
        const val COLUMN_RADIUS = "radius_meters"
        const val COLUMN_VISIT_COUNT = "visit_count"
        const val COLUMN_LAST_VISIT = "last_visit"
        const val COLUMN_ASSOCATED_CONTACTS = "associated_contacts"
        const val COLUMN_ASSOCATED_MEDIA = "associated_media"
    }
    
    /**
     * Deep Link Actions
     * Use these to open specific views in other apps
     */
    object Actions {
        // Open contact details in Contacts app
        const val ACTION_VIEW_CONTACT = "com.jimknopf.ecosystem.action.VIEW_CONTACT"
        const val EXTRA_CONTACT_ID = "com.jimknopf.ecosystem.extra.CONTACT_ID"
        
        // Open photo in Gallery app
        const val ACTION_VIEW_PHOTO = "com.jimknopf.ecosystem.action.VIEW_PHOTO"
        const val EXTRA_PHOTO_URI = "com.jimknopf.ecosystem.extra.PHOTO_URI"
        
        // Show location on Map
        const val ACTION_SHOW_LOCATION = "com.jimknopf.ecosystem.action.SHOW_LOCATION"
        const val EXTRA_LATITUDE = "com.jimknopf.ecosystem.extra.LATITUDE"
        const val EXTRA_LONGITUDE = "com.jimknopf.ecosystem.extra.LONGITUDE"
        
        // View all photos of a person
        const val ACTION_VIEW_PHOTOS_OF_PERSON = "com.jimknopf.ecosystem.action.VIEW_PHOTOS_OF_PERSON"
        
        // View all contacts at location
        const val ACTION_VIEW_CONTACTS_AT_LOCATION = "com.jimknopf.ecosystem.action.VIEW_CONTACTS_AT_LOCATION"
    }
}
