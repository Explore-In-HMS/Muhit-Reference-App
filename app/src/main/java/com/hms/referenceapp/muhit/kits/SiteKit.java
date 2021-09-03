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
import android.util.Log;
import android.widget.Toast;

import com.hms.referenceapp.muhit.R;
import com.hms.referenceapp.muhit.common.Utils;
import com.huawei.hms.site.api.SearchResultListener;
import com.huawei.hms.site.api.SearchService;
import com.huawei.hms.site.api.SearchServiceFactory;
import com.huawei.hms.site.api.model.Coordinate;
import com.huawei.hms.site.api.model.LocationType;
import com.huawei.hms.site.api.model.NearbySearchRequest;
import com.huawei.hms.site.api.model.NearbySearchResponse;
import com.huawei.hms.site.api.model.SearchStatus;
import com.huawei.hms.site.api.model.Site;

import java.util.List;
import java.util.Locale;

import static com.hms.referenceapp.muhit.common.Constants.TAG;

public class SiteKit {

    SearchService searchService;
    NearbySearchRequest nearbySearchRequest;
    Coordinate coordinate;
    SearchResultListener<NearbySearchResponse> nearbySearchResponseSearchResultListener;

    MapKit map;
    Context context;

    public void init(Context mcontext, MapKit mmap) {
        context = mcontext;
        map = mmap;
    }


    public void getPoiList(final Activity activity, final LocationType locationType, String query, final int icon, Coordinate coordinate, final Integer color) {

        if (map.huaweiMap != null) {

            Utils.toggleView(activity.findViewById(R.id.frmLoading), true);
            Utils.toggleView(activity.findViewById(R.id.txtFrmLoading), false);

            searchService = SearchServiceFactory.create(context, Utils.getApiKey(context));
            nearbySearchRequest = new NearbySearchRequest();
            nearbySearchRequest.setLocation(coordinate);
            nearbySearchRequest.setRadius(20000);
            nearbySearchRequest.setPoiType(locationType);
            nearbySearchRequest.setLanguage(Locale.getDefault().getLanguage());
            nearbySearchRequest.setQuery(query);
            //nearbySearchRequest.setPageIndex(1);
            //request.setPageSize(5);

            nearbySearchResponseSearchResultListener = new SearchResultListener<NearbySearchResponse>() {
                @Override
                public void onSearchResult(NearbySearchResponse results) {
                    List<Site> sites = results.getSites();
                    if (results == null || results.getTotalCount() <= 0 || sites == null || sites.size() <= 0) {
                        Toast.makeText(context, context.getString(R.string.poi_not_found), Toast.LENGTH_SHORT).show();
                        return;
                    }
                    for (Site site : sites) {
                        map.addMarker(site, icon, locationType, color);
                    }
                    Utils.toggleView(activity.findViewById(R.id.frmLoading), false);
                }


                @Override
                public void onSearchError(SearchStatus status) {
                    Log.i(TAG, "Error : " + status.getErrorCode() + " " + status.getErrorMessage());
                }
            };
            searchService.nearbySearch(nearbySearchRequest, nearbySearchResponseSearchResultListener);
        }

    }

}
