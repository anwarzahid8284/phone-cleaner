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

public class OperatingSystemFragment extends Fragment {
    TextView textViewVersion, textViewVersionName, textViewApiLevel, textViewBuildID, textViewBuildTime, textViewFingerPrint;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.operating_system_fragment, container, false);
        textViewVersion = view.findViewById(R.id.version_status_id);
        textViewVersionName = view.findViewById(R.id.versionName_status_id);
        textViewApiLevel = view.findViewById(R.id.status_status_id);
        textViewBuildTime = view.findViewById(R.id.buildTime_status_id);
        textViewFingerPrint = view.findViewById(R.id.fingerprint_status_id);
        textViewBuildID = view.findViewById(R.id.buildID_status_id);
        showOperatingSystemInfo();
        return view;
    }

    public void showOperatingSystemInfo() {
        textViewVersion.setText(Build.VERSION.RELEASE);
        textViewVersionName.setText(Build.VERSION.CODENAME);
        textViewApiLevel.setText(""+Build.VERSION.SDK_INT);
        textViewBuildID.setText(Build.ID);
        textViewBuildTime.setText("" + Build.TIME);
        textViewFingerPrint.setText(Build.FINGERPRINT);
    }
}
