/*
Copyright 2020. Explore in HMS. All rights reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.hms.referenceapp.muhit.kits;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.os.Looper;
import android.util.Log;

import com.hms.referenceapp.muhit.R;
import com.hms.referenceapp.muhit.common.Utils;
import com.huawei.hmf.tasks.Task;
import com.huawei.hms.common.ApiException;
import com.huawei.hms.common.ResolvableApiException;
import com.huawei.hms.location.FusedLocationProviderClient;
import com.huawei.hms.location.LocationAvailability;
import com.huawei.hms.location.LocationCallback;
import com.huawei.hms.location.LocationRequest;
import com.huawei.hms.location.LocationResult;
import com.huawei.hms.location.LocationServices;
import com.huawei.hms.location.LocationSettingsRequest;
import com.huawei.hms.location.LocationSettingsStatusCodes;
import com.huawei.hms.location.SettingsClient;
import com.huawei.hms.maps.model.LatLng;

import static com.hms.referenceapp.muhit.common.Constants.TAG;


public class LocationKit {

    FusedLocationProviderClient fusedLocationProviderClient;
    LocationRequest locationRequest;
    SettingsClient settingsClient;
    LocationCallback locationCallback;
    public LatLng currentLatLng;
    public MapKit map;
    Context context;

    public void init(Context mcontext, MapKit mmap) {

        map = mmap;
        context = mcontext;

        // LOCATION
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        settingsClient = LocationServices.getSettingsClient(context);
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

    }

    public void getLastLocationWithCallback(final Activity activity) {

        Utils.toggleView(activity.findViewById(R.id.frmLoading), true);

        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    currentLatLng = new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude());
                    Log.i(TAG, "LocationKit -> currentLatLng: " + currentLatLng.toString());
                    if (map == null) {
                        Log.d(TAG, "map is null");
                    } else {
                        map.huaweiMap.setMyLocationEnabled(true);
                        map.setCamera(currentLatLng);
                        Utils.toggleView(activity.findViewById(R.id.frmLoading), false);
                    }
                }
            }

            @Override
            public void onLocationAvailability(LocationAvailability locationAvailability) {
                if (locationAvailability != null) {
                    boolean flag = locationAvailability.isLocationAvailable();
                    Log.i(TAG, "GeoFence onLocationAvailability isLocationAvailable:" + flag);
                }
            }
        };
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }


    public void getLastLocation() {
        try {
            Task<android.location.Location> lastLocation = fusedLocationProviderClient.getLastLocation();

            lastLocation.addOnSuccessListener(location -> {

                if (location == null) {
                    Log.i(TAG, "getLastLocation onFail location is null");
                    return;
                }

                Log.i(TAG, "getLastLocation: " + location.getLatitude() + "," + location.getLongitude());
                currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                if (map == null) {
                    Log.d(TAG, "map is null");
                } else {
                    map.huaweiMap.setMyLocationEnabled(true);
                    map.setCamera(currentLatLng);
                }

            }).addOnFailureListener(e -> Log.e(TAG, "getLastLocation onFailure:" + e.getMessage()));

        } catch (Exception e) {
            Log.e(TAG, "getLastLocation exception:" + e.getMessage());
        }
    }

    public void checkLocationSettings(final Activity activity) {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        locationRequest = new LocationRequest();
        builder.addLocationRequest(locationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check the device location settings.
        settingsClient.checkLocationSettings(locationSettingsRequest)
                .addOnSuccessListener(locationSettingsResponse -> {
                    // Initiate location requests when the location settings meet the requirements.
                    fusedLocationProviderClient
                            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                            .addOnSuccessListener(aVoid -> {
                                // Processing when the API call is successful.
                            });
                })
                .addOnFailureListener(e -> {
                    // Device location settings do not meet the requirements.
                    int statusCode = ((ApiException) e).getStatusCode();
                    if (statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        try {
                            ResolvableApiException rae = (ResolvableApiException) e;
                            // Call startResolutionForResult to display a pop-up asking the user to enable related permission.
                            rae.startResolutionForResult(activity, 0);
                        } catch (IntentSender.SendIntentException sie) {
                            Log.d(TAG, "LocationKit -> " + sie.getMessage());
                        }
                    }
                });
    }
}
