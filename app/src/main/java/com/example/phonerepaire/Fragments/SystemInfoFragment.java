package com.example.phonerepaire.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Adpatar.InfoAdapter;
import com.example.phonerepaire.Model.DeviceInfo;
import com.example.phonerepaire.R;

import java.util.ArrayList;
import java.util.List;

public class SystemInfoFragment extends Fragment {
    List<DeviceInfo> deviceInfoList;
    RecyclerView recyclerView;
    InfoAdapter infoAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.system_info_frag, container, false);
        deviceInfoList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.info_recycler_view_Id);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // device info name
        String[] name = {"Battery", "Storage and RAM", "Features", "Mobile",
                "Operating System", "Display", "Processor", "Bluetooth", "Sensor"};
        // image view
        int[] drawables = {R.drawable.battery_ic, R.drawable.storage_ic,
                R.drawable.feature_ic, R.drawable.mobile_ic,
                R.drawable.android_ic, R.drawable.display_ic,
                R.drawable.processor_ic, R.drawable.bluetooth_ic,
                R.drawable.sensor_ic};
        for (int i = 0; i < name.length; i++) {
            deviceInfoList.add(new DeviceInfo(name[i], drawables[i]));
        }
        infoAdapter = new InfoAdapter(deviceInfoList, getActivity());
        recyclerView.setAdapter(infoAdapter);
        return view;
    }
}
