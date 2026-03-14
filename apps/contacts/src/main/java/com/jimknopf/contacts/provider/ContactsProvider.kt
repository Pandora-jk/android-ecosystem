package com.jimknopf.contacts.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.MatrixCursor
import android.net.Uri
import android.provider.BaseColumns
import com.jimknopf.contracts.EcosystemContracts

/**
 * ContactsProvider - Exposes contact data to other apps in the ecosystem.
 * 
 * This content provider enables cross-app features like:
 * - Gallery app querying "photos for contact X"
 * - Map app querying "contacts at location Y"
 * - Unified search across contacts
 * 
 * Security: Protected by signature-level permissions, only apps signed with the same 
 * key can access this provider.
 */
class ContactsProvider : ContentProvider() {

    companion object {
        private const val AUTHORITY = "com.jimknopf.contacts.provider"
        
        // URI patterns
        private const val CONTACTS = 1
        private const val CONTACTS_ID = 2
        private const val CONTACTS_ID_PHOTOS = 3
        private const val CONTACTS_ID_LOCATIONS = 4
        private const val CONTACTS_AT_LOCATION = 5
        
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            // content://com.jimknopf.contacts.provider/contacts
            addURI(AUTHORITY, "contacts", CONTACTS)
            
            // content://com.jimknopf.contacts.provider/contacts/{id}
            addURI(AUTHORITY, "contacts/#", CONTACTS_ID)
            
            // content://com.jimknopf.contacts.provider/contacts/{id}/photos
            addURI(AUTHORITY, "contacts/#/photos", CONTACTS_ID_PHOTOS)
            
            // content://com.jimknopf.contacts.provider/contacts/{id}/locations
            addURI(AUTHORITY, "contacts/#/locations", CONTACTS_ID_LOCATIONS)
            
            // content://com.jimknopf.contacts.provider/contacts/at-location?lat=X&lon=Y&radius=Z
            addURI(AUTHORITY, "contacts/at-location", CONTACTS_AT_LOCATION)
        }
    }

    override fun onCreate(): Boolean {
        // Initialize provider
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            CONTACTS -> queryAllContacts(projection, selection, selectionArgs, sortOrder)
            CONTACTS_ID -> queryContactById(uri, projection, selection, selectionArgs, sortOrder)
            CONTACTS_ID_PHOTOS -> queryPhotosForContact(uri)
            CONTACTS_ID_LOCATIONS -> queryLocationsForContact(uri)
            CONTACTS_AT_LOCATION -> queryContactsAtLocation(uri)
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CONTACTS -> "vnd.android.cursor.dir/vnd.com.jimknopf.contacts"
            CONTACTS_ID -> "vnd.android.cursor.item/vnd.com.jimknopf.contact"
            CONTACTS_ID_PHOTOS -> "vnd.android.cursor.dir/vnd.com.jimknopf.contact.photos"
            CONTACTS_ID_LOCATIONS -> "vnd.android.cursor.dir/vnd.com.jimknopf.contact.locations"
            CONTACTS_AT_LOCATION -> "vnd.android.cursor.dir/vnd.com.jimknopf.contacts"
            else -> null
        }
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        // For now, read-only. Implement insert logic if needed.
        throw UnsupportedOperationException("Insert not supported")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        // For now, read-only. Implement delete logic if needed.
        throw UnsupportedOperationException("Delete not supported")
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        // For now, read-only. Implement update logic if needed.
        throw UnsupportedOperationException("Update not supported")
    }

    // Query implementations
    private fun queryAllContacts(
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        // In production, query from your Room database
        // For now, return sample data
        val matrixCursor = MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                EcosystemContracts.Contacts.COLUMN_NAME,
                EcosystemContracts.Contacts.COLUMN_PHONE,
                EcosystemContracts.Contacts.COLUMN_EMAIL,
                EcosystemContracts.Contacts.COLUMN_PHOTO_URI
            )
        )
        
        // Sample data
        matrixCursor.addRow(arrayOf(1L, "Alice Johnson", "+61 400 100 200", "alice@example.com", null))
        matrixCursor.addRow(arrayOf(2L, "Bob Smith", "+61 400 200 300", "bob@example.com", null))
        matrixCursor.addRow(arrayOf(3L, "Charlie Brown", "+61 400 300 400", "charlie@example.com", null))
        
        return matrixCursor
    }

    private fun queryContactById(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor {
        val contactId = uri.lastPathSegment?.toLongOrNull() ?: return MatrixCursor(projection)
        
        val matrixCursor = MatrixCursor(
            projection ?: arrayOf(
                BaseColumns._ID,
                EcosystemContracts.Contacts.COLUMN_NAME,
                EcosystemContracts.Contacts.COLUMN_PHONE,
                EcosystemContracts.Contacts.COLUMN_EMAIL
            )
        )
        
        // In production, query from database where ID = contactId
        when (contactId) {
            1L -> matrixCursor.addRow(arrayOf(1L, "Alice Johnson", "+61 400 100 200", "alice@example.com"))
            2L -> matrixCursor.addRow(arrayOf(2L, "Bob Smith", "+61 400 200 300", "bob@example.com"))
            3L -> matrixCursor.addRow(arrayOf(3L, "Charlie Brown", "+61 400 300 400", "charlie@example.com"))
        }
        
        return matrixCursor
    }

    private fun queryPhotosForContact(uri: Uri): Cursor {
        // In production, this would:
        // 1. Get contact ID from URI
        // 2. Query Media ContentProvider for photos tagged with this contact
        // 3. Return cursor with photo URIs
        
        val contactId = uri.pathSegments.getOrNull(1)?.toLongOrNull() ?: return MatrixCursor(null)
        
        val cursor = MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                EcosystemContracts.Media.COLUMN_URI,
                EcosystemContracts.Media.COLUMN_TIMESTAMP
            )
        )
        
        // Sample: return 3 photos for each contact
        cursor.addRow(arrayOf(100L + contactId, "content://media/external/images/101", System.currentTimeMillis() - 86400000))
        cursor.addRow(arrayOf(200L + contactId, "content://media/external/images/102", System.currentTimeMillis() - 172800000))
        cursor.addRow(arrayOf(300L + contactId, "content://media/external/images/103", System.currentTimeMillis() - 259200000))
        
        return cursor
    }

    private fun queryLocationsForContact(uri: Uri): Cursor {
        // In production: query location history for this contact
        val matrixCursor = MatrixCursor(
            arrayOf(
                BaseColumns._ID,
                EcosystemContracts.Locations.COLUMN_NAME,
                EcosystemContracts.Locations.COLUMN_LATITUDE,
                EcosystemContracts.Locations.COLUMN_LONGITUDE,
                EcosystemContracts.Locations.COLUMN_LAST_VISIT
            )
        )
        
        matrixCursor.addRow(arrayOf(1L, "Sydney CBD", -33.8688, 151.2093, System.currentTimeMillis()))
        matrixCursor.addRow(arrayOf(2L, "Bondi Beach", -33.8915, 151.2767, System.currentTimeMillis() - 86400000))
        
        return matrixCursor
    }

    private fun queryContactsAtLocation(uri: Uri): Cursor {
        // In production: query for contacts who have been at the specified location
        val lat = uri.getQueryParameter("lat")?.toDoubleOrNull() ?: 0.0
        val lon = uri.getQueryParameter("lon")?.toDoubleOrNull() ?: 0.0
        val radius = uri.getQueryParameter("radius")?.toIntOrNull() ?: 100
        
        // In production: spatial query against location history
        return queryAllContacts(null, null, null, null)
    }
}
