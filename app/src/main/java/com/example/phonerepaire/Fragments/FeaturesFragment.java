package com.example.phonerepaire.Fragments;

import android.content.Context;
import android.content.pm.PackageManager;
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


public class FeaturesFragment extends Fragment {
    TextView textViewFingerprint, textViewWifi, textViewGPS,
            textViewCameraFlash, textViewMicrophone, textViewAccelerometer,
            textViewNfc, textViewGSM, textViewCompass, textViewMultitouch;
    Context context;
    String available = "Available", notAvailable = "Not Available";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.feature_fragment, container, false);
        textViewFingerprint = view.findViewById(R.id.finger_status_id);
        textViewWifi = view.findViewById(R.id.wifi_status_id);
        textViewGPS = view.findViewById(R.id.gps_status_id);
        textViewCameraFlash = view.findViewById(R.id.camera_flash_status_id);
        textViewMicrophone = view.findViewById(R.id.microphone_status_id);
        textViewAccelerometer = view.findViewById(R.id.accelerometer_status_id);
        textViewNfc = view.findViewById(R.id.nfc_status_id);
        textViewGSM = view.findViewById(R.id.gsm_status_id);
        textViewCompass = view.findViewById(R.id.compass_status_id);
        textViewMultitouch = view.findViewById(R.id.multitouch_status_id);
        context = getActivity();
        showFeatures();
        return view;
    }

    private void showFeatures() {
        PackageManager pm = context.getPackageManager();
        boolean hasGps = pm.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        boolean hasWifi = pm.hasSystemFeature(PackageManager.FEATURE_WIFI);
        boolean hasCompass = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_COMPASS);
        boolean hasCameraFlash = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        boolean hasMicrophone = pm.hasSystemFeature(PackageManager.FEATURE_MICROPHONE);
        boolean hasAccelerometer = pm.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER);
        boolean hasNFC = pm.hasSystemFeature(PackageManager.FEATURE_NFC);
        boolean hasGSM = pm.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM);
        boolean hasMultitouch = pm.hasSystemFeature(PackageManager.FEATURE_TOUCHSCREEN_MULTITOUCH);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean hasFingerPrint = pm.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT);
            if (hasFingerPrint) {
                textViewFingerprint.setText(available);
            } else {
                textViewFingerprint.setText(notAvailable);
            }
        } else {
            textViewFingerprint.setText("Not Supported");
        }
        if (hasGps) {
            textViewGPS.setText(available);
        } else {
            textViewGPS.setText(notAvailable);
        }
        if (hasWifi) {
            textViewWifi.setText(available);
        } else {
            textViewWifi.setText(notAvailable);
        }
        if (hasCompass) {
            textViewCompass.setText(available);
        } else {
            textViewCompass.setText(notAvailable);
        }
        if (hasCameraFlash) {
            textViewCameraFlash.setText(available);
        } else {
            textViewCameraFlash.setText(notAvailable);
        }
        if (hasMicrophone) {
            textViewMicrophone.setText(available);
        } else {
            textViewMicrophone.setText(notAvailable);
        }
        if (hasAccelerometer) {
            textViewAccelerometer.setText(available);
        } else {
            textViewAccelerometer.setText(notAvailable);
        }
        if (hasNFC) {
            textViewNfc.setText(available);
        } else {
            textViewNfc.setText(notAvailable);
        }
        if (hasGSM) {
            textViewGSM.setText(available);
        } else {
            textViewGPS.setText(notAvailable);
        }
        if (hasMultitouch) {
            textViewMultitouch.setText(available);
        } else {
            textViewMultitouch.setText(notAvailable);
        }

    }


}
