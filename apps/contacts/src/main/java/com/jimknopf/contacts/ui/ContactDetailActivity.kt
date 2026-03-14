package com.jimknopf.contacts.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.jimknopf.contacts.R
import com.jimknopf.contracts.EcosystemContracts

/**
 * Contact Detail Activity - Shows contact info with tabs for Photos and Locations.
 * 
 * This demonstrates the "Data-Centric" architecture:
 * - Photos tab queries Gallery app's ContentProvider
 * - Locations tab queries Map app's ContentProvider
 * - Tapping items deep-links to the respective apps
 */
class ContactDetailActivity : AppCompatActivity() {

    private lateinit var toolbar: MaterialToolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var contactNameText: TextView

    private var contactId: Long = 0
    private var contactName: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_detail)

        toolbar = findViewById(R.id.toolbar)
        tabLayout = findViewById(R.id.tabLayout)
        recyclerView = findViewById(R.id.recyclerView)
        contactNameText = findViewById(R.id.contactNameText)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        // Get contact ID from intent
        contactId = intent.getLongExtra(EcosystemContracts.Actions.EXTRA_CONTACT_ID, -1)
        contactName = intent.getStringExtra("contact_name") ?: "Contact"

        contactNameText.text = contactName
        supportActionBar?.title = contactName

        setupTabs()
        loadContactData()
    }

    private fun setupTabs() {
        tabLayout.addTab(tabLayout.newTab().setText("Info"))
        tabLayout.addTab(tabLayout.newTab().setText("Photos"))
        tabLayout.addTab(tabLayout.newTab().setText("Map"))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> loadContactData()
                    1 -> loadPhotosTab()
                    2 -> loadMapTab()
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadContactData() {
        // Show basic contact info
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = InfoAdapter(contactName)
    }

    private fun loadPhotosTab() {
        // Query Gallery provider for photos of this contact
        val uri = EcosystemContracts.Media.byPerson(contactId)
        
        try {
            val cursor = contentResolver.query(uri, null, null, null, null)
            
            if (cursor?.count == 0) {
                // No photos - show placeholder
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PlaceholderAdapter("No photos yet")
                return
            }

            cursor?.use {
                recyclerView.layoutManager = LinearLayoutManager(this)
                recyclerView.adapter = PhotosAdapter(cursor, contactId)
            }
        } catch (e: Exception) {
            // Gallery app not installed or error
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = PlaceholderAdapter("Install Gallery app to view photos")
        }
    }

    private fun loadMapTab() {
        // Show map with contact's locations
        // For now, show placeholder - Map app will be implemented next
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = PlaceholderAdapter("Map view - coming soon")
    }

    // Simple adapters for demo
    inner class InfoAdapter(private val name: String) : RecyclerView.Adapter<InfoAdapter.ViewHolder>() {
        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(parent.context).apply {
                setPadding(32, 32, 32, 32)
                textSize = 16f
            }
            return ViewHolder(textView)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = "Contact: $name\n\nThis is the info tab.\nTap Photos tab to see cross-app data."
        }
        override fun getItemCount() = 1
    }

    inner class PhotosAdapter(
        private val cursor: android.database.Cursor,
        private val contactId: Long
    ) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
        
        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
        
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(parent.context).apply {
                setPadding(32, 24, 32, 24)
                textSize = 14f
            }
            return ViewHolder(textView)
        }
        
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (cursor.moveToPosition(position)) {
                val photoUri = cursor.getString(0)
                holder.textView.text = "Photo $position: $photoUri\n(Tap to open in Gallery)"
                holder.itemView.setOnClickListener {
                    // Deep link to Gallery app
                    val intent = Intent(EcosystemContracts.Actions.ACTION_VIEW_PHOTO)
                    intent.putExtra(EcosystemContracts.Actions.EXTRA_PHOTO_URI, photoUri)
                    startActivity(intent)
                }
            }
        }
        
        override fun getItemCount() = cursor.count
    }

    inner class PlaceholderAdapter(private val message: String) : RecyclerView.Adapter<PlaceholderAdapter.ViewHolder>() {
        class ViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
        override fun onCreateViewHolder(parent: android.view.ViewGroup, viewType: Int): ViewHolder {
            val textView = TextView(parent.context).apply {
                setPadding(32, 64, 32, 64)
                textSize = 14f
                textAlignment = View.TEXT_ALIGNMENT_CENTER
            }
            return ViewHolder(textView)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.textView.text = message
        }
        override fun getItemCount() = 1
    }
}
