package com.example.projectaplikasi

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.Place.Field
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private var mGoogleMap: GoogleMap? = null
    private lateinit var autocompleteFragment: AutocompleteSupportFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        val navBottom: BottomNavigationView = findViewById(R.id.navBottom)
        navBottom.selectedItemId = R.id.nav_map

        navBottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }

                R.id.nav_map -> true
                R.id.nav_kalkulator -> {
                    startActivity(Intent(this, CalculatorActivity::class.java))
                    true
                }

                else -> false
            }
        }

        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val mapOptionsButton: ImageButton = findViewById(R.id.mapOptionsMenu)
        val popupMenu = PopupMenu(this, mapOptionsButton)
        popupMenu.menuInflater.inflate(R.menu.map_option, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener { menuItem ->
            changeMap(menuItem.itemId)
            true
        }

        mapOptionsButton.setOnClickListener {
            popupMenu.show()
        }

    }


    private fun changeMap(itemId: Int) {
        if (mGoogleMap == null) {
            Toast.makeText(this, "Map belum siap!", Toast.LENGTH_SHORT).show()
            return
        }
        when (itemId) {
            R.id.normal_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.hybrid_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.satellite_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain_map -> mGoogleMap?.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mGoogleMap = googleMap
        // Add a marker in Yogyakarta, Indonesia,
        // and move the map's camera to the same location.
        val locations = listOf(
            LatLng(-7.825532608462961, 110.37591526858363) to "Tritunggal Fitnes Gym Jogja",
            LatLng(-7.8246787802836435, 110.37490677615236) to "TT gym jogja",
            LatLng(-7.824649732302928, 110.37473987211794) to "Tritunggal Fitness Center",
            LatLng(-7.821008650064526, 110.37326765654625) to "Glanzfit",
            LatLng(-7.821987729657657, 110.3682827386413) to "Wzone Gym Studio",
            LatLng(-7.822278451763605, 110.36736410447067) to "OCIGEN Physical Fitness Center",
            LatLng(-7.824978147637174, 110.36078229924631) to "COPRAZZ GYM",
            LatLng(-7.816574053162959, 110.35944197881214) to "DM GYM",
            LatLng(-7.8105066842831725, 110.3547467374956) to "Joglo Camp",
            LatLng(-7.797562669531342, 110.37607946449617) to "DR GYM"
        )

        // Builder untuk LatLngBounds
        val boundsBuilder = LatLngBounds.Builder()

        // Tambahkan marker untuk setiap lokasi
        for ((latLng, title) in locations) {
            googleMap.addMarker(MarkerOptions().position(latLng).title(title))
            boundsBuilder.include(latLng) // Tambahkan lokasi ke bounds
        }

        // Pindahkan kamera untuk mencakup semua lokasi
        val bounds = boundsBuilder.build()
        val padding = 200 // Padding dalam piksel
        googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding))
    }
}
