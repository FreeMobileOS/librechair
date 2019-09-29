/*
 * Copyright (c) 2019 oldosfan.
 * Copyright (c) 2019 the Lawnchair developers
 *
 *     This file is part of Librechair.
 *
 *     Librechair is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Librechair is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Librechair.  If not, see <https://www.gnu.org/licenses/>.
 */

package ch.deletescape.lawnchair.feed.maps;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

import ch.deletescape.lawnchair.feed.ProviderScreen;
import ch.deletescape.lawnchair.feed.impl.LauncherFeed;

public class MapScreen extends ProviderScreen {
    private final LauncherFeed feed;
    private final double lat;
    private final double lon;
    private MapView mapView;
    private ViewGroup parent, layout;

    public MapScreen(Context base, LauncherFeed feed, double lat, double lon, double zoom) {
        super(base);
        this.feed = feed;
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    protected View getView(ViewGroup parent) {
        this.parent = parent;
        LinearLayout layout = new LinearLayout(parent.getContext()) {
            @Override
            public boolean onInterceptTouchEvent(MotionEvent ev) {
                if (feed != null) {
                    feed.getFeedController().setDisallowInterceptCurrentTouchEvent(true);
                }
                return super.onInterceptTouchEvent(ev);
            }
        };
        layout.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        layout.addView(mapView = getMapView());
        this.layout = layout;
        return layout;
    }

    private MapView getMapView() {
        MapView mapView = new MapView(parent.getContext());
        mapView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        return mapView;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void bindView(View view) {
        mapView.getTileProvider().setTileSource(TileSourceFactory.MAPNIK);
        mapView.getController().setCenter(new GeoPoint(lat, lon));
        mapView.setClipToPadding(false);
        mapView.getController().zoomTo(9.0);
    }

    @Override
    public void onPause() {
        layout.removeAllViews();
    }

    @Override
    public void onResume() {
        layout.addView(mapView = getMapView());
        bindView(null);
    }
}