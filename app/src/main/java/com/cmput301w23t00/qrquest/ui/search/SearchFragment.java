package com.cmput301w23t00.qrquest.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentSearchBinding;
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.ExternalUsersAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * This class models the fragment used to search for other user profiles
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    FirebaseFirestore db; // Firebase Firestore database instance
    ArrayList<ExternalUserProfile> filteredUsers; // list of filtered users
    ArrayList<ExternalUserProfile> allUsers; // list of all users
    ListView usersList; // listview of users
    ExternalUsersAdapter usersAdapter; // adapter for user list

    /**
     * onCreateView inflates the view, showing a search bar and all user profiles which match
     * the search query
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // instantiate arrays
        filteredUsers = new ArrayList<>();
        allUsers = new ArrayList<>();
        // connect to firebase
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersCollectionReference = db.collection("users");
        // set adapter with listview
        usersAdapter = new ExternalUsersAdapter(getContext(), filteredUsers);
        usersList = (ListView) root.findViewById(R.id.search_users_list);
        usersList.setAdapter(usersAdapter);
        SearchView profileSearch = root.findViewById(R.id.search_users_profile);
        // get all users and display them
        usersCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        // get user info from document
                        String name = (String) document.getData().get("name");
                        String aboutMe = (String) document.getData().get("aboutMe");
                        String userId = (String) document.getData().get("identifierId");
                        String phoneNumber = (String) document.getData().get("phoneNumber");
                        String email = (String) document.getData().get("email");
                        // set profile picture id
                        String userAvatarNumber;
                        try {
                            userAvatarNumber = (String) document.getData().get("avatarId");
                        } catch (Exception e) { // catch NullPointerException explicitly
                            userAvatarNumber = "0"; // use string "0" instead of String.valueOf(0)
                        }

                        if (userAvatarNumber == "") {
                            userAvatarNumber = "0";
                        }
                        // add user to list
                        ExternalUserProfile currentUser = new ExternalUserProfile(name, aboutMe, userId, phoneNumber, email, userAvatarNumber);
                        allUsers.add(currentUser);
                        filteredUsers.add(currentUser);
                        usersAdapter.notifyDataSetChanged();
                        }
                }
            }
        });
        // allow user to open search view after clicking anywhere on it
        profileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSearch.setIconified(false);
            }
        });
        // filter user list based on search query
        profileSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.println(Log.INFO,"searched", s);
                filteredUsers.clear();
                // get all users that match search query then sort
                ArrayList<ExternalUserProfile> tempArray = allUsers.stream().filter((data) ->
                        data.getName().toLowerCase(Locale.CANADA).contains(s.toLowerCase(Locale.CANADA)))
                        .sorted(Comparator.comparing(ExternalUserProfile::getName))
                        .sorted(Comparator.comparingInt(a -> a.getName().length()))
                        .collect(Collectors.toCollection(ArrayList::new));
                filteredUsers.addAll(tempArray);
                usersAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        // navigate to external user profile fragment when user list item is clicked
        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // create bundle
                Bundle bundle = new Bundle();
                bundle.putBoolean("isSearch", true);
                bundle.putBoolean("isLeaderboard", false);
                bundle.putBoolean("isExternalProfile", false);
                bundle.putParcelable("selectedUser", filteredUsers.get(i));
                Navigation.findNavController(view).navigate(R.id.action_navigation_search_to_externaluser_profile, bundle);
            }
        });
        return root;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    /**
     * onDestroyView is called when the view is destroyed.
     * It cleans up any references to the binding to prevent memory leaks.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}