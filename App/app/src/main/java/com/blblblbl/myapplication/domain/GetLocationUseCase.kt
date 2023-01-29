package com.blblblbl.myapplication.domain

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.blblblbl.myapplication.viewmodels.utils.PermissionHandler
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class GetLocationUseCase @Inject constructor() {
    suspend fun getLocation(context:Context, ph: PermissionHandler, location: MutableStateFlow<Location?>){
        getLastKnownLocation(context, ph,location)
        Log.d("MyLog","getLocation")
    }
    val state = mutableStateOf("")

    @OptIn(ExperimentalPermissionsApi::class)
    suspend fun checkPermission(ph: PermissionHandler) {
        val r = ph.check(*(REQUEST_PERMISSIONS_MAP))
        state.value = (r?.permissions?.map {
            "${it.permission}=${it.status}"
        }?.joinToString("\n"))?:"null"
    }

    private suspend fun requestPermission(ph: PermissionHandler): Map<String,Boolean>?{
        val r = ph.request(*(REQUEST_PERMISSIONS_MAP))
        state.value = r?.entries?.map {
            "${it.key}=${it.value}"
        }?.joinToString("\n")?:"null"
        return r
    }

    private suspend fun getLastKnownLocation(context:Context, ph: PermissionHandler, loc: MutableStateFlow<Location?>) {
        var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            val request = requestPermission(ph)
            val isAllGranted = request?.all { it.value }
            if (isAllGranted == true){
                fusedLocationClient.lastLocation
                    .addOnSuccessListener { location->
                        if (location != null) {
                            loc.value = location
                            Log.d("MyLog", "location:$location")
                        }
                    }
            }
        }
        else{
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        loc.value = location
                        Log.d("MyLog", "location:$location")
                    }
                }
        }
    }

    companion object{
        val REQUEST_PERMISSIONS_MAP:Array<String> = buildList {
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
            add(Manifest.permission.ACCESS_FINE_LOCATION)
        }.toTypedArray()
    }
}