package com.cmput301w23t00.qrquest.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.osmdroid.config.Configuration;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.Date;

/**
 * This is a class which defines the map page.
 */
public class MapFragment extends Fragment {
    MapView map;
    MyLocationNewOverlay myLocationNewOverlay;
    FirebaseFirestore db; // Firebase Firestore database instance
    Boolean markerIsClicked = false;

    /**
     * onCreateView inflates the view, showing a user's collection of QR codes with a button to see
     * summary statistics
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, null);

        // Connect to firebase instance and get collection references for database querying
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");

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
            map.setMultiTouchControls(true);

            usersQRCodesCollectionReference.get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    String qrCodeData = (String) document.getData().get("qrCodeData");
                                    String identifierId = (String) document.getData().get("identifierId");
                                    com.google.firebase.firestore.GeoPoint location = document.getGeoPoint("lcoation");
                                    com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) document.getData().get("dateScanned");
                                    // Convert Firebase Timestamp to Date
                                    Date dateScanned = timestamp.toDate();
                                    if (location != null) {
                                        GeoPoint locationOSM = new GeoPoint(location.getLatitude(), location.getLongitude());
                                        Marker currentLocationMarker = new Marker(map);
                                        Resources res = getResources();
                                        Drawable drawable = ResourcesCompat.getDrawable(res, R.drawable.baseline_location_pin_24, null);
                                        currentLocationMarker.setIcon(drawable);
                                        currentLocationMarker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
                                            @Override
                                            public boolean onMarkerClick(Marker marker, MapView mapView) {
                                                if (!markerIsClicked) {
                                                    markerIsClicked = true;
                                                    // Create a bundle to store data that will be passed to the QR code information fragment
                                                    Bundle bundle = new Bundle();
                                                    // Add the selected QR code object and the user ID to the bundle
                                                    bundle.putParcelable("selectedQRCode", new LibraryQRCode(qrCodeData, new QRCodeProcessor(qrCodeData).getScore(), dateScanned));
                                                    bundle.putString("userID", identifierId);
                                                    bundle.putString("documentID", document.getId());
                                                    bundle.putBoolean("isMap", true);

                                                    // Use the Navigation component to navigate to the QR code information fragment,
                                                    // and pass the bundle as an argument to the destination fragment
                                                    NavHostFragment.findNavController(MapFragment.this).navigate(R.id.action_navigation_map_to_qrCodeInformationFragment, bundle);
                                                }

                                                return false;
                                            }
                                        });
                                        currentLocationMarker.setPosition(locationOSM);
                                        currentLocationMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
                                        map.getOverlays().add(currentLocationMarker);
                                    }
                                }
                            }
                        }
                    });

            renderLocation();
        }

        return v;
    }

    /**
     * This function when called centers the map on the users geolocation
     */
    private void renderLocation() {
        GeoPoint myLocation = this.myLocationNewOverlay.getMyLocation();
        map.getController().setCenter(myLocation);
        map.getController().setZoom(19.0);
        map.getController().animateTo(myLocation);
    }

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
