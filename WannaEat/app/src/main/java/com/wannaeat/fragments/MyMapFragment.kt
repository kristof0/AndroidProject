package com.wannaeat.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions




class MapFragment : SupportMapFragment() {
    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */


        val restaurant = LatLng(lat, lng)

        googleMap.addMarker(MarkerOptions().position(restaurant).title("The restaurant`s place"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(restaurant))
    }
    private var lat:Double = 0.0
    private var lng:Double = 0.0
    override fun onCreateView(arg0: LayoutInflater, arg1: ViewGroup?, arg2: Bundle?): View? {
        val v: View? = super.onCreateView(arg0, arg1, arg2)
        initMap()
        return v
    }

    private fun initMap() {
        getMapAsync(callback)
    }

    companion object {
        fun newInstance(lat:Double,lng:Double): MapFragment {
            val frag = MapFragment()
            frag.lat=lat
            frag.lng=lng
            return frag
        }
    }
}