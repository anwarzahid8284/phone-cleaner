package com.example.phonerepaire.Fragments;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

public class BluetoothFragment extends Fragment {
    TextView textViewBluetooth, textViewName, textViewAddress, textViewScanMode, textViewBluetoothD;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bluetooth_fragment, container, false);
        textViewBluetooth = view.findViewById(R.id.bluetooth_status_id);
        textViewName = view.findViewById(R.id.name_status_id);
        textViewAddress = view.findViewById(R.id.address_status_id);
        textViewScanMode = view.findViewById(R.id.scan_mode_status_id);
        textViewBluetoothD = view.findViewById(R.id.bluetooth_discovery_status_id);
        showBluetoothInfo(getActivity());
        return view;
    }

    @SuppressLint("HardwareIds")
    private void showBluetoothInfo(Context context) {
        BluetoothAdapter bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        boolean bluetoothStatus=bluetoothAdapter.isEnabled();
        textViewName.setText(bluetoothAdapter.getName());
        textViewScanMode.setText(""+bluetoothAdapter.getScanMode());
        textViewAddress.setText(""+bluetoothAdapter.getAddress());
        boolean discovery=bluetoothAdapter.startDiscovery();
        if (discovery){
            textViewBluetoothD.setText("On");
        }else {
            textViewBluetoothD.setText("Off");
        }

        if(bluetoothStatus){
            textViewBluetooth.setText("ON");
        }else {
            textViewBluetooth.setText("Off");
        }
    }


}
