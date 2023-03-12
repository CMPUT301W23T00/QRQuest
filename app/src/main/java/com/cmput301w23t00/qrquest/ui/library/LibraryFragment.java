package com.cmput301w23t00.qrquest.ui.library;

import static androidx.navigation.Navigation.findNavController;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.cmput301w23t00.qrquest.R;
import com.cmput301w23t00.qrquest.databinding.FragmentLibraryBinding;

import java.util.ArrayList;
import java.util.Date;

public class LibraryFragment extends Fragment {

    private FragmentLibraryBinding binding;
    private ListView QRList;
    private ArrayAdapter<LibraryQRCode> QRAdapter;
    private ArrayList<LibraryQRCode> dataList;
    private int mSelectedItemIndex;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        LibraryViewModel libraryViewModel =
                new ViewModelProvider(this).get(LibraryViewModel.class);

        binding = FragmentLibraryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // QR Stats button
        Button viewQrStats = binding.viewPersonalQrStatsButton;
        viewQrStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = findNavController(view);
                //navController.navigate(R.id.action_navigation_qrcode_library_to_qrCodeSummaryStatisticsFragment2);
            }
        });

        // QR Code List
        QRList = binding.libraryQrCodesList;
        dataList = new ArrayList<>();
        String[] QRDatas = {"t1", "t2", "t3"};
        Integer[] QRScores = {1,2,3};
        Date[] QRDates = {new Date(), new Date(), new Date()};
        for (int i = 0; i < QRDatas.length; i++) {
            dataList.add(new LibraryQRCode(QRDatas[i], QRScores[i], QRDates[i]));
        }
        QRAdapter = new LibraryQRCodeAdapter((Context) getActivity(), dataList);
        QRList.setAdapter(QRAdapter);

        QRList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                mSelectedItemIndex = index;
                LibraryQRCode qrCode = dataList.get(index);

                Bundle bundle = new Bundle();
                bundle.putParcelable("selectedQRCode", qrCode);

                Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_qrCodeInformationFragment, bundle);
            }
        });


        //final TextView textView = binding.textLibrary;
        //libraryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}