package com.example.retosohphos.activities

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.retosohphos.Api.RetrofitApi
import com.example.retosohphos.R
import com.example.retosohphos.models.OficinasResponse
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Oficinas : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {

    private lateinit var map: GoogleMap
    //private val response:list<OficinasResponse>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oficinas)
        crearMapa()
        marcadores()
    }

    private fun crearMapa() {
        val mapFragment=supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googlemap: GoogleMap) {

        map=googlemap
        crearMarcador()
        map.setOnMyLocationButtonClickListener(this)
        map.uiSettings.isZoomControlsEnabled=true
        map.uiSettings.isCompassEnabled=true
        enableLocalizacion()

    }

    fun marcadores(){


        CoroutineScope(Dispatchers.IO).launch {
            val call= RetrofitApi.api.getOficinas()
            val lat=call.body()
            Log.d("oficinas","${lat}.")
            runOnUiThread{
                if (call.isSuccessful){
                    lat?.let {
                        for (lista in it ){
                            Log.d("oficinas2","${lista.Latitud} y ${lista.Longitud}.")

                        }
                    }




                }
            }


        }

    }

    private fun crearMarcador() {
        val coordenadas=LatLng(1.2345678,-1.3243)
        val marker=MarkerOptions().position(coordenadas).title("Oficinas de Shopos solution")
        map.addMarker(marker)
        map.animateCamera(
            CameraUpdateFactory.newLatLngZoom(coordenadas,15f),4000,null
        )
    }
    private fun validarPermisos()=ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED

    private fun enableLocalizacion(){
        if (!::map.isInitialized)return
        if (validarPermisos()){
            map.isMyLocationEnabled=true
        }else{
            pedirPermisos()
        }
    }

    private fun pedirPermisos() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_COARSE_LOCATION)){
            Toast.makeText(this, "Ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),0)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode==0 && grantResults.isNotEmpty()){
            for (i in grantResults.indices){
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Permisos","${permissions[i]} aceptados.")
                    map.isMyLocationEnabled=true
                }
                else{
                    Toast.makeText(this, "Para Activar la localizacion ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        if (!::map.isInitialized)return
        if (!validarPermisos()){
            map.isMyLocationEnabled=false
            Toast.makeText(this, "Para Activar la localizacion ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMyLocationButtonClick(): Boolean {
        return false
    }
}