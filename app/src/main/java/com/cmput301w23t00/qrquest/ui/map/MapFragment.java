package com.cmput301w23t00.qrquest.ui.map;

import static android.content.ContentValues.TAG;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.SearchSuggestionsAdapter;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentMapBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is a class which defines the map page.
 */
public class MapFragment extends Fragment {
    CustomMapView map;
    MyLocationNewOverlay myLocationNewOverlay;
    FirebaseFirestore db; // Firebase Firestore database instance
    Boolean markerIsClicked = false;
    private FragmentMapBinding binding; // View binding for the map fragment

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
        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Connect to firebase instance and get collection references for database querying
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersQRCodesCollectionReference = db.collection("usersQRCodes");

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION }, 100);
        }

        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            map = root.findViewById(R.id.mapview);
            Configuration.getInstance().setUserAgentValue(getActivity().getPackageName());

            this.myLocationNewOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity()), map);
            this.myLocationNewOverlay.enableFollowLocation();
            this.myLocationNewOverlay.enableMyLocation();
            myLocationNewOverlay.runOnFirstFix(new Runnable() {
                @Override
                public void run() {
                    final GeoPoint myLocation = myLocationNewOverlay.getMyLocation();
                    if (myLocation != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                map.getController().setZoom(19.0);
                                map.getController().setCenter(myLocation);
                                root.findViewById(R.id.loading_panel).setVisibility(View.INVISIBLE);
                            }
                        });
                    };
                }
            });

            map.getOverlays().add(this.myLocationNewOverlay);
            map.setMultiTouchControls(true);
            map.setBuiltInZoomControls(false);

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
                                                    bundle.putBoolean("isLeaderboard", false);

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

            FloatingSearchView mSearchView = root.findViewById(R.id.floating_search_view);

            mSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
                @Override
                public void onSearchTextChanged(String oldQuery, final String newQuery) {
                    if (!oldQuery.equals("") && newQuery.equals("")) {
                        mSearchView.clearSuggestions();
                    } else {

                        //this shows the top left circular progress
                        //you can call it where ever you want, but
                        //it makes sense to do it when loading something in
                        //the background.
                        mSearchView.showProgress();

                        Geocoder geocoder = new Geocoder(getContext());
                        List<Address> list = new ArrayList<>();

                        // Regex by Iain Fraser
                        // Source : https://stackoverflow.com/a/18690202
                        Pattern mPattern = Pattern.compile("^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$\n");
                        Matcher matcher = mPattern.matcher(newQuery.toString());

                        if (matcher.matches()) {
                            String latString = newQuery.trim().split(",\\s+")[0];
                            String lonString = newQuery.trim().split(",\\s+")[1];
                            try {
                                list = geocoder.getFromLocation(Double.parseDouble(latString), Double.parseDouble(lonString), 1);
                            } catch (IOException e) {
                                Log.e(TAG, "geoLocate: IOException: Location " + e.getMessage() );
                            }
                        } else {
                            try {
                                list = geocoder.getFromLocationName(newQuery, 1);
                            } catch (IOException e){
                                Log.e(TAG, "geoLocate: IOException: " + e.getMessage() );
                            }
                        }



                        if(list.size() > 0){
                            Address address = list.get(0);

                            /*
                            // Original Author: Ryan Lee
                            // https://stackoverflow.com/a/46920982
                            // ported and modified from Swift 3.0 to Java by Kolby ML
                             */
                            double latitude = address.getLatitude();
                            double longitude = address.getLongitude();
                            double distance = 1;

                            // ~1 mile of lat and lon in degrees
                            double lat = 0.0144927536231884;
                            double lon = 0.0181818181818182;

                            double lowerLat = latitude - (lat * distance);
                            double lowerLon = longitude - (lon * distance);

                            double greaterLat = latitude + (lat * distance);
                            double greaterLon = longitude + (lon * distance);

                            com.google.firebase.firestore.GeoPoint lesserGeopoint = new com.google.firebase.firestore.GeoPoint(lowerLat, lowerLon);
                            com.google.firebase.firestore.GeoPoint greaterGeopoint = new com.google.firebase.firestore.GeoPoint(greaterLat, greaterLon);

                            db.collection("usersQRCodes").whereGreaterThan("lcoation", lesserGeopoint).whereLessThan("lcoation", greaterGeopoint).get().addOnCompleteListener(new OnCompleteListener<>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) { // If found iterate through all user QR codes
                                        List<QRCodeSuggestions> queryList = new ArrayList();
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            String qrCodeData = (String) document.getData().get("qrCodeData");
                                            String identifierId = (String) document.getData().get("identifierId");
                                            com.google.firebase.Timestamp timestamp = (com.google.firebase.Timestamp) document.getData().get("dateScanned");
                                            // Convert Firebase Timestamp to Date
                                            Date dateScanned = timestamp.toDate();
                                            queryList.add(new QRCodeSuggestions(new QRCodeProcessor(qrCodeData).getName(), qrCodeData, identifierId, document.getId(), dateScanned));
                                        }
                                        //this will swap the data and
                                        //render the collapse/expand animations as necessary
                                        mSearchView.swapSuggestions(queryList);

                                        //let the users know that the background
                                        //process has completed
                                        mSearchView.hideProgress();
                                    }
                                }
                            });
                        }
                    }
                }
            });

            mSearchView.setOnBindSuggestionCallback(new SearchSuggestionsAdapter.OnBindSuggestionCallback() {
                @Override
                public void onBindSuggestion(View suggestionView, ImageView leftIcon, TextView textView, SearchSuggestion item, int itemPosition) {
                    QRCodeSuggestions qrCodeSuggestions = (QRCodeSuggestions) item;
                    leftIcon.setImageBitmap(new QRCodeProcessor(qrCodeSuggestions.getQrCodeData()).getBitmap(getContext()));
                }

            });

            mSearchView.setOnSearchListener(new FloatingSearchView.OnSearchListener() {
                @Override
                public void onSuggestionClicked(final SearchSuggestion searchSuggestion) {
                    QRCodeSuggestions qrCodeSuggestions = (QRCodeSuggestions) searchSuggestion;

                    // Create a bundle to store data that will be passed to the QR code information fragment
                    Bundle bundle = new Bundle();
                    // Add the selected QR code object and the user ID to the bundle
                    bundle.putParcelable("selectedQRCode", new LibraryQRCode(qrCodeSuggestions.getQrCodeData(), new QRCodeProcessor(qrCodeSuggestions.getQrCodeData()).getScore(), qrCodeSuggestions.getDate()));
                    bundle.putString("userID", qrCodeSuggestions.getIdentifierId());
                    bundle.putString("documentID", qrCodeSuggestions.getDocumentId());
                    bundle.putBoolean("isMap", true);

                    // Use the Navigation component to navigate to the QR code information fragment,
                    // and pass the bundle as an argument to the destination fragment
                    NavHostFragment.findNavController(MapFragment.this).navigate(R.id.action_navigation_map_to_qrCodeInformationFragment, bundle);

                }

                @Override
                public void onSearchAction(String currentQuery) {
                }
            });
        }

        Button leaderboardButton = binding.circleButton;
        leaderboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_map_to_leaderboardFragment);
            }
        });

        return root;
    }

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
