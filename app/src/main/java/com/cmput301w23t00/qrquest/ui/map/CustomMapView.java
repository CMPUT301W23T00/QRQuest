package com.cmput301w23t00.qrquest.ui.map;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;

import org.osmdroid.tileprovider.MapTileProviderBase;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * This subclass allows the expected center of the map to be set at a particular geo-point.
 */
public class CustomMapView extends MapView {
    public CustomMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }

    public CustomMapView(Context context, MapTileProviderBase tileProvider, Handler tileRequestCompleteHandler, AttributeSet attrs, boolean hardwareAccelerated) {
        super(context, tileProvider, tileRequestCompleteHandler, attrs, hardwareAccelerated);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }

    public CustomMapView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }

    public CustomMapView(Context context) {
        super(context);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }

    public CustomMapView(Context context, MapTileProviderBase aTileProvider) {
        super(context, aTileProvider);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }

    public CustomMapView(Context context, MapTileProviderBase aTileProvider, Handler tileRequestCompleteHandler) {
        super(context, aTileProvider, tileRequestCompleteHandler);
        setExpectedCenter(new GeoPoint(52, -113.0));
    }
}
