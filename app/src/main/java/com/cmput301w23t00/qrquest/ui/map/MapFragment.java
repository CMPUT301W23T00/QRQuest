package com.cmput301w23t00.qrquest.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.cmput301w23t00.qrquest.R;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

public class MapFragment extends Fragment {
    MapView map;
    MyLocationNewOverlay myLocationNewOverlay;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, null);


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map = v.findViewById(R.id.mapview);
            Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());

            this.myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity()), map);
            this.myLocationNewOverlay.enableFollowLocation();
            this.myLocationNewOverlay.enableMyLocation();

            map.getOverlays().add(this.myLocationNewOverlay);
            map.setBuiltInZoomControls(true);
            map.setMultiTouchControls(true);
            renderLocation();
        }

        return v;
    }

    private void renderLocation() {
        GeoPoint myLocation = this.myLocationNewOverlay.getMyLocation();
        map.getController().setCenter(myLocation);
        map.getController().setZoom(19.0);
        map.getController().animateTo(myLocation);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
