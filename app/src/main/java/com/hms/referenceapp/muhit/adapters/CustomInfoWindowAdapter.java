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

package com.hms.referenceapp.muhit.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hms.referenceapp.muhit.R;
import com.huawei.hms.maps.HuaweiMap;
import com.huawei.hms.maps.model.Marker;

public class CustomInfoWindowAdapter implements HuaweiMap.InfoWindowAdapter {

    private LayoutInflater inflate;

    public CustomInfoWindowAdapter(LayoutInflater inflater) {
        this.inflate = inflater;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        @SuppressLint("InflateParams")
        View view = this.inflate.inflate(R.layout.map_info_window, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView title = view.findViewById(R.id.txtMarkerTitle);
        TextView snippet = view.findViewById(R.id.txtMarkerSnippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getInfoContents(Marker marker) {
        View view = this.inflate.inflate(R.layout.map_info_window, null);
        view.setLayoutParams(new LinearLayout.LayoutParams(100, LinearLayout.LayoutParams.WRAP_CONTENT));

        TextView title = view.findViewById(R.id.txtMarkerTitle);
        TextView snippet = view.findViewById(R.id.txtMarkerSnippet);

        title.setText(marker.getTitle());
        snippet.setText(marker.getSnippet());

        return view;
    }

}