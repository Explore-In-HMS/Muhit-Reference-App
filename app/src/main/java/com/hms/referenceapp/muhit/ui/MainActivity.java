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

package com.hms.referenceapp.muhit.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.hms.referenceapp.muhit.R;
import com.hms.referenceapp.muhit.adapters.PoiTypeAdapter;
import com.hms.referenceapp.muhit.common.Utils;
import com.hms.referenceapp.muhit.kits.LocationKit;
import com.hms.referenceapp.muhit.kits.MapKit;
import com.hms.referenceapp.muhit.kits.SiteKit;
import com.hms.referenceapp.muhit.models.PoiTypeModel;
import com.huawei.hms.site.api.model.Coordinate;

import static com.hms.referenceapp.muhit.common.Constants.TAG;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // Init
    Button btnClearAllMarkers;
    Spinner spnPoiTypes;
    View divider;
    AlertDialog alertOpenLocationSettings;
    AlertDialog alertGrantLocationService;
    Boolean isPermissionPopupShowing = false;

    Utils utils;
    MapKit map;
    LocationKit location;
    SiteKit site;

    // PoiTypes
    PoiTypeAdapter poiTypeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Hide status bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Set full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Hide navigation buttons
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        setContentView(R.layout.activity_main);

        // Init
        init();

        // Utils
        utils = new Utils();

        // MAP
        map = new MapKit();
        map.init(this, savedInstanceState);

        // LOCATION
        location = new LocationKit();
        location.init(this, map);
        location.checkLocationSettings(MainActivity.this);

        // SITE
        site = new SiteKit();
        site.init(this, map);


        // POI TYPE LIST
        poiTypeAdapter = new PoiTypeAdapter(MainActivity.this, utils.getPoiTypeList(getApplicationContext()));
        spnPoiTypes.setAdapter(poiTypeAdapter);
        int initialposition = spnPoiTypes.getSelectedItemPosition();
        spnPoiTypes.setSelection(initialposition, false);
        spnPoiTypes.setOnItemSelectedListener(this);
    }

    private void init() {
        btnClearAllMarkers = findViewById(R.id.btnClearMarkers);
        btnClearAllMarkers.setVisibility(View.GONE);
        divider = findViewById(R.id.divider);
        divider.setVisibility(View.GONE);
        spnPoiTypes = findViewById(R.id.spnPoiTypes);


        alertOpenLocationSettings = new AlertDialog.Builder(this)
                .setMessage(getString(R.string.need_to_location_service))
                .setPositiveButton(getString(R.string.go_to_setting), (paramDialogInterface, paramInt) -> startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 3))
                .setNegativeButton(getString(R.string.close_to_app), (dialog, which) -> finish())
                .setCancelable(false).create();


        alertGrantLocationService = new AlertDialog.Builder(MainActivity.this)
                .setMessage(getString(R.string.need_to_location_granted))
                .setPositiveButton(getString(R.string.go_to_setting), (dialog, which) -> utils.openAppSettings(getApplicationContext(), MainActivity.this))
                .setNegativeButton(getString(R.string.close_to_app), (dialog, which) -> finish())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setCancelable(false)
                .create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "MainActivity -> onResume");

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (utils.checkLocationServiceAvailability(MainActivity.this)) {

                    if (isPermissionPopupShowing) {
                        alertOpenLocationSettings.cancel();
                        alertOpenLocationSettings.dismiss();
                    }

                    utils.checkPermissions(MainActivity.this);
                    if (utils.isLocationPermissionGranted) {
                        location.getLastLocationWithCallback(MainActivity.this);
                    }
                } else {
                    alertOpenLocationSettings.show();
                    isPermissionPopupShowing = true;
                }

            }
        }, 500);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if (location.currentLatLng != null) {
            PoiTypeModel poi = (PoiTypeModel) parent.getSelectedItem();
            if (poi.getType() != null) {
                site.getPoiList(
                        MainActivity.this,
                        poi.getType(),
                        poi.getName(),
                        poi.getIcon(),
                        new Coordinate(location.currentLatLng.latitude, location.currentLatLng.longitude),
                        utils.getColor(position)
                );
            }
        } else {
            if (utils.checkLocationServiceAvailability(MainActivity.this)) {
                if (isPermissionPopupShowing) {
                    alertOpenLocationSettings.cancel();
                    alertOpenLocationSettings.dismiss();
                }

                utils.checkPermissions(MainActivity.this);
                if (utils.isLocationPermissionGranted) {
                    location.getLastLocationWithCallback(MainActivity.this);
                }
            } else {
                alertOpenLocationSettings.show();
                isPermissionPopupShowing = true;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        isPermissionPopupShowing = true;
        Log.d(TAG, "MainActivity -> isPermissionPopupShowing: True");

        if (requestCode == 1) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                utils.isLocationPermissionGranted = true;
                Log.d(TAG, "MainActivity -> isLocationPermissionGranted: True");

                isPermissionPopupShowing = false;
                Log.d(TAG, "MainActivity -> isPermissionPopupShowing: False");

            } else {

                utils.isLocationPermissionGranted = false;
                Log.d(TAG, "MainActivity -> isLocationPermissionGranted: False __ Permission Denied by User!!");

                isPermissionPopupShowing = false;
                Log.d(TAG, "MainActivity -> isPermissionPopupShowing: False");

                alertGrantLocationService.show();

            }
        }

        if (requestCode == 2) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                utils.isLocationPermissionGranted = true;
                Log.d(TAG, "MainActivity -> isLocationPermissionGranted: True");

                isPermissionPopupShowing = false;
                Log.d(TAG, "MainActivity -> isPermissionPopupShowing: False");

                if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "onRequestPermissionsResult: apply ACCESS_BACKGROUND_LOCATION successful");
                }

            } else {

                utils.isLocationPermissionGranted = false;
                Log.d(TAG, "MainActivity -> isLocationPermissionGranted: False __ Permission Denied by User!!");

                isPermissionPopupShowing = false;
                Log.d(TAG, "MainActivity -> isPermissionPopupShowing: False");

                alertGrantLocationService.show();

            }
        }

        if (requestCode == 3) {
            //isPermissionPopupShowing = false;
            Log.d(TAG, permissions[0] + " : " + grantResults[0]);
            Log.d(TAG, permissions[1] + " : " + grantResults[1]);
            Log.d(TAG, permissions[2] + " : " + grantResults[2]);
        }
    }


    public void clearAllMarkers(View view) {
        map.huaweiMap.clear();
        btnClearAllMarkers.setVisibility(View.GONE);
        divider.setVisibility(View.GONE);
        spnPoiTypes.setSelection(0);
    }


    /* Hide screen buttons */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        }
    }

}