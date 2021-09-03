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

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hms.referenceapp.muhit.R;
import com.hms.referenceapp.muhit.models.PoiTypeModel;

import java.util.ArrayList;

public class PoiTypeAdapter extends ArrayAdapter<PoiTypeModel> {

    public PoiTypeAdapter(@NonNull Context context, ArrayList<PoiTypeModel> poiList) {
        super(context, 0, poiList);
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_type_item, parent, false);
        }

        PoiTypeModel item = getItem(position);
        ImageView imgPoiIcon = convertView.findViewById(R.id.imgPoiIcon);
        TextView txtPoiName = convertView.findViewById(R.id.txtPoiName);

        if (item != null) {
            imgPoiIcon.setImageResource(item.getIcon());
            txtPoiName.setText(item.getName());
        }

        return convertView;
    }


    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.poi_type_item, parent, false);
        }

        PoiTypeModel item = getItem(position);
        ImageView imgPoiIcon = convertView.findViewById(R.id.imgPoiIcon);
        TextView txtPoiName = convertView.findViewById(R.id.txtPoiName);

        if (item != null) {
            imgPoiIcon.setImageResource(item.getIcon());
            txtPoiName.setText(item.getName());

            if (item.getType() == null) {
                imgPoiIcon.setVisibility(View.GONE);
                txtPoiName.setVisibility(View.GONE);
                convertView.setVisibility(View.GONE);
            } else {
                imgPoiIcon.setVisibility(View.VISIBLE);
                txtPoiName.setVisibility(View.VISIBLE);
                convertView.setVisibility(View.VISIBLE);
            }
        }

        return convertView;
    }
}
