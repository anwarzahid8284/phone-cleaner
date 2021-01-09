package com.example.phonerepaire.Fragments;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

public class MobileFragment extends Fragment {
    TextView textViewDName, textViewRVersion, textViewModel, textViewManufacture,
            textViewBoard, textViewAndroidID, textViewDAPILevel, textViewDBrand, textViewHardware;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mobile_fragment, container, false);
        textViewDName = view.findViewById(R.id.device_name_status_id);
        textViewAndroidID = view.findViewById(R.id.androidID_status_id);
        textViewRVersion = view.findViewById(R.id.release_version_status_id);
        textViewModel = view.findViewById(R.id.model_status_id);
        textViewManufacture = view.findViewById(R.id.manufacture_status_id);
        textViewBoard = view.findViewById(R.id.board_status_id);
        textViewDBrand = view.findViewById(R.id.brand_status_id);
        textViewDAPILevel = view.findViewById(R.id.apiLevel_status_id);
        textViewHardware = view.findViewById(R.id.hardware_status_id);
        showDeviceInfo();
        return view;
    }

    public void showDeviceInfo() {
        textViewDName.setText(Build.DEVICE);
        textViewAndroidID.setText(Build.ID);
        textViewRVersion.setText(Build.VERSION.RELEASE);
        textViewModel.setText(Build.MODEL);
        textViewManufacture.setText(Build.MANUFACTURER);
        textViewBoard.setText(Build.BOARD);
        textViewDBrand.setText(Build.BRAND);
        textViewDAPILevel.setText(""+Build.VERSION.SDK_INT);
        textViewHardware.setText(Build.HARDWARE);
    }
}
