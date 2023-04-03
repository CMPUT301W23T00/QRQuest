package com.cmput301w23t00.qrquest.ui.search;

import static android.content.ContentValues.TAG;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentSearchBinding;
import com.cmput301w23t00.qrquest.ui.addqrcode.QRCodeProcessor;
import com.cmput301w23t00.qrquest.ui.externaluserpage.ExternalUserProfile;
import com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.ExternalUsersAdapter;
import com.cmput301w23t00.qrquest.ui.map.QRCodeSuggestions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * SearchFragment is an template class not yet with fully implemented functionality
 */
public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    FirebaseFirestore db; // Firebase Firestore database instance
    ArrayList<ExternalUserProfile> filteredUsers;
    ArrayList<ExternalUserProfile> allUsers;
    ListView usersList;
    ExternalUsersAdapter usersAdapter;

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
//        SearchViewModel searchViewModel =
//                new ViewModelProvider(this).get(SearchViewModel.class);

        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        filteredUsers = new ArrayList<>();
        allUsers = new ArrayList<>();
        SearchView profileSearch = root.findViewById(R.id.search_users_profile);
        db = FirebaseFirestore.getInstance();
        final CollectionReference usersCollectionReference = db.collection("users");
        usersAdapter = new ExternalUsersAdapter(getContext(), filteredUsers);
        usersList = (ListView) root.findViewById(R.id.search_users_list);
        usersList.setAdapter(usersAdapter);
        usersCollectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String name = (String) document.getData().get("name");
                        String aboutMe = (String) document.getData().get("aboutMe");
                        String userId = (String) document.getData().get("identifierId");
                        String phoneNumber = (String) document.getData().get("phoneNumber");
                        String email = (String) document.getData().get("email");
                        ExternalUserProfile currentUser = new ExternalUserProfile(name,aboutMe,userId,phoneNumber,email);
                        allUsers.add(currentUser);
                        filteredUsers.add(currentUser);
                        usersAdapter.notifyDataSetChanged();
                        }
                }
            }
        });
        profileSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileSearch.setIconified(false);
            }
        });
        profileSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.println(Log.INFO,"searched", s);
                filteredUsers.clear();
                ArrayList<ExternalUserProfile> tempArray = allUsers.stream().filter((data) ->
                        data.getName().toLowerCase().contains(s.toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
                tempArray.sort(Comparator.comparing(ExternalUserProfile::getName));
                tempArray.sort(Comparator.comparingInt(a -> a.getName().length()));
                for (ExternalUserProfile profile: tempArray) {
                    filteredUsers.add(profile);
                }
                usersAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
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
        binding = null;
    }
}