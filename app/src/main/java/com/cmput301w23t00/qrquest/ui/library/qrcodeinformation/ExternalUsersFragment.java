package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.profile.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * fragment used to display external user list
 */
public class ExternalUsersFragment extends Fragment {

    ArrayList<ExternalUserProfile> users = new ArrayList<>();
    ListView usersList;
    ExternalUsersAdapter usersAdapter;
    //Used for returning to previous page
    Bundle bundle;
    String docID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    LibraryQRCode qrCode;
    Boolean back = false;

    /**
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_externalusers, container, false);
        usersAdapter = new ExternalUsersAdapter(getContext(), users);
        usersList = (ListView) root.findViewById(R.id.other_users_list);
        usersList.setAdapter(usersAdapter);


        if (getArguments() == null) this.bundle = ViewCycleStack.pop();
        else this.bundle = getArguments();
        qrCode = bundle.getParcelable("selectedQRCode");
        docID = bundle.getString("documentID");

        setHasOptionsMenu(true);

        //allows for exit by system back button
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                restoreActionBar();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getActivity(), callback);

        final int[] total_users = {0};
        final int[] updated_users = {0};

        CollectionReference databaseCodes = db.collection("usersQRCodes");
        databaseCodes.whereEqualTo("qrCodeData", qrCode.getData())
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) ;
                        {
                            int count = task.getResult().getDocuments().size();
                            for (int i = 0; i < count; i++) {
                                DocumentSnapshot document = task.getResult().getDocuments().get(i);
                                if (document != null) {
                                    String identifierId = document.getString("identifierId");
                                    if (!identifierId.equals("") && !identifierId.equals(UserProfile.getUserId())) {
                                        //gets user with specific id and increments total
                                        total_users[0]++;
                                        FirebaseFirestore.getInstance().collection("users").whereEqualTo("identifierId", identifierId).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                updated_users[0]++;
                                                if (task.isSuccessful() && task.getResult().getDocuments().size() != 0) {
                                                    ExternalUserProfile user = new ExternalUserProfile(identifierId);
                                                    user.setName(task.getResult().getDocuments().get(0).getString("name"));
                                                    user.setAboutMe(task.getResult().getDocuments().get(0).getString("aboutMe"));
                                                    user.setPhoneNumber(task.getResult().getDocuments().get(0).getString("phoneNumber"));
                                                    user.setEmail(task.getResult().getDocuments().get(0).getString("email"));
                                                    String userAvatarNumber;
                                                    try {
                                                        userAvatarNumber = task.getResult().getDocuments().get(0).getString("avatarId");
                                                    } catch (Exception e) { // catch NullPointerException explicitly
                                                        userAvatarNumber = "0"; // use string "0" instead of String.valueOf(0)
                                                    }

                                                    if (userAvatarNumber == "") {
                                                        userAvatarNumber = "0";
                                                    }
                                                    user.setAvatarId(userAvatarNumber);
                                                    Log.d(TAG, "onComplete: Finished Loading");
                                                    users.add(user);
                                                }
                                                //done to display everything in list all at once after being loaded
                                                if (total_users[0] == updated_users[0]) {
                                                    users.sort(Comparator.comparing(ExternalUserProfile::getName));
                                                    Collections.reverse(users);
                                                    usersList.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, users.size() * 239 + 200));
                                                    usersAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: failed to query");
                                                updated_users[0]++;
                                            }
                                        });
                                    }
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

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();

                bundle.putParcelable("selectedUser", users.get(i));
                bundle.putBoolean("isSearch", false);
                bundle.putBoolean("isLeaderboard", false);
                bundle.putBoolean("isExternalUsers", true);
                Navigation.findNavController(view).navigate(R.id.action_navigation_externalusersfragment_to_externaluserprofile, bundle);
            }
        });

        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            restoreActionBar();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Restores action menu to previous state
     */
    private void restoreActionBar() {
        this.back = true;
        NavHostFragment.findNavController(this).navigate(R.id.action_navigation_externalusersfragment_to_qrCodeInformationFragment);
    }

    /**
     * When fragment pauses add save state to stack
     */
    @Override
    public void onPause() {
        super.onPause();
        if (!back) ViewCycleStack.push(bundle);
    }

}
