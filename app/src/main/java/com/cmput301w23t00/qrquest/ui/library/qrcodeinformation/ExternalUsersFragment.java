package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.MainActivity;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ExternalUsersFragment extends Fragment {

    ArrayList<ExternalUserProfile> users = new ArrayList<>();
    ListView usersList;
    ExternalUsersAdapter usersAdapter;
    //Used for returning to previous page
    Bundle bundle;
    String docID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LibraryQRCode qrCode;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_externalusers, container, false);
        usersAdapter = new ExternalUsersAdapter(getContext(), users);
        usersList = (ListView) root.findViewById(R.id.other_users_list);
        usersList.setAdapter(usersAdapter);

        Bundle bundle = getArguments();
        if (bundle != null) {
            this.bundle = bundle;
            qrCode = bundle.getParcelable("selectedQRCode");
            docID = bundle.getString("documentID");
        }

        setHasOptionsMenu(true);

        //allows for exit by system back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                restoreActionBar();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        /*for (int i = 0; i < 15; i++) */users.add(new ExternalUserProfile("c54c41c7-1ce0-498d-8b2d-9105c52381dc"));
        getUsers();
        usersList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, users.size()*239 + 200));
        usersAdapter.notifyDataSetChanged();

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();

                bundle.putParcelable("selectedUser", users.get(i));
                Navigation.findNavController(view).navigate(R.id.action_navigation_externalusersfragment_to_externaluserprofile, bundle);
            }
        });

        return root;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((MainActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Other's With This QR Code");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            restoreActionBar();
        }
        return super.onOptionsItemSelected(item);
    }


    private void getUsers() {
        CollectionReference databaseCodes = db.collection("usersQRcodes");
        databaseCodes.whereEqualTo("qrCodeData", qrCode.getData())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()); {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if (document != null) {
                                    String identifierId = document.getString("identifierId");
                                    if (!identifierId.equals("")) users.add(new ExternalUserProfile(identifierId));
                                }
                            }
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Unable to connect to network", Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void restoreActionBar() {
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_externalusersfragment_to_qrCodeInformationFragment, this.bundle);
    }
}
