package com.cmput301w23t00.qrquest.ui.library.qrcodeinformation.comments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentCommentsBinding;
import com.cmput301w23t00.qrquest.ui.library.LibraryQRCode;
import com.cmput301w23t00.qrquest.ui.updateavatar.AvatarUtility;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * The class  QR code information fragment extends fragment
 *
 * The QRCodeInformationFragment class extends the Fragment class and provides a fragment that displays information about a QR code.
 */
public class CommentFragment extends Fragment {

    private FragmentCommentsBinding binding; // view binding object for the fragment
    String userID; // a string to hold the current user's ID
    String docID; // the qr code document id
    Boolean isMap; // determines which page to return to
    FirebaseFirestore db; // Firestore database instance
    LibraryQRCode libraryQRCode;
    Drawable Profile;
    Bundle qrCodeInformationBundle;


    String User;

    /**
     * onCreateView is called when the view is first created.
     * It inflates the view and sets up the QRCodeInformationViewModel to display the QR code information.
     *
     * @param inflater the LayoutInflater object that can be used to inflate any views in the fragment
     * @param container the parent view that the fragment's UI should be attached to
     * @param savedInstanceState the previously saved instance state
     * @return the view for the fragment's UI
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        qrCodeInformationBundle = getArguments();
        LibraryQRCode qrCodeData = qrCodeInformationBundle.getParcelable("selectedQRCode");
        System.out.println("Ran Comment Fragment");

        // Inflate the fragment_qrcodeinformation.xml layout for this fragment.

        binding = FragmentCommentsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ArrayList<CommentData> list = new ArrayList<CommentData>();
        CommentAdapter NewAdapter = new CommentAdapter(getContext(),list);
        binding.commentList.setAdapter(NewAdapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usersQRCodes").whereEqualTo("qrCodeData", qrCodeData.getData()).whereNotEqualTo("comment","").get().addOnCompleteListener(new OnCompleteListener<>() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("Task1");
                    for (QueryDocumentSnapshot doc1 : task.getResult()) {
                        System.out.println("Ran Task 1");
                        String ID = doc1.getString("identifierId");

                        db.collection("users").whereEqualTo("identifierId", ID).get().addOnCompleteListener(task2 -> {
                            User = "Anonymous User";
                            String Comment = doc1.getString("comment");
                            Integer Inp = -1;

                            if (task2.isSuccessful()) {
                                for (QueryDocumentSnapshot doc2 : task2.getResult()) {
                                    User = doc2.getString("name");
                                    System.out.println(User);
                                    System.out.println("Ran Task 2: " + User);
                                    // PFP processing

                                    String Avatar = (String) doc2.get("avatarId");
                                    System.out.println("Avatar: " + Avatar);
                                    if (Avatar != "" && Avatar != null) {
                                        AvatarUtility AvatarIDGetter = new AvatarUtility();
                                        Integer InputId = Integer.parseInt(Avatar);
                                        System.out.println(InputId);
                                        Integer PFPId = AvatarIDGetter.getAvatarImageResource(InputId);
                                        Inp = PFPId;
                                    }

                                    break;
                                }
                            }

                            System.out.println("Final User: " + User);
                            System.out.println("Final Comment: " + Comment);
                            System.out.println("Final Test: " + Inp);

                            if (Inp != -1) {
                                Profile = getResources().getDrawable(Inp);
                                System.out.println("Ran something else!");
                            } else {
                                Profile = getResources().getDrawable(R.drawable.default_avatar);
                            }

                            CommentData NewComment = new CommentData(User,Comment,Profile);
                            NewAdapter.add(NewComment);
                            NewAdapter.notifyDataSetChanged();
                        });
                    }
                };
            }
        });


        return root;
    }

    /**
     * onCreate is called to do initial creation of the fragment.
     *
     * @param savedInstanceState the previously saved instance state
     */
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Creates this fragment's menu.
        setHasOptionsMenu(true);

        
    }


    /**
     * onOptionsItemSelected is called when a menu item is selected.
     *
     * @param item The menu item that was selected
     * @return True if the menu item was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Back arrow
        if (item.getItemId() == android.R.id.home) {
            // Navigate back to the previous fragment
            NavHostFragment.findNavController(CommentFragment.this).navigate(R.id.commentFragment_to_action_qrCodeInformationFragment, qrCodeInformationBundle);
            return true;
        }

        return super.onOptionsItemSelected(item);
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
