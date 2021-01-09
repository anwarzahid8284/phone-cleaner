package com.example.phonerepaire.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.util.Objects;

import static android.content.Context.POWER_SERVICE;

public class BatterySaverFragment extends Fragment implements View.OnClickListener {
    Button btnUltraMode, btnNormalMode, btnOptimizedBattery, btnUsageBattery;
    ProgressBar progressBar;
    TextView textViewPercentage;
    int levelPer;
    Handler handler = new Handler(Looper.getMainLooper());
    private int progressStatus = 0;
    IntentFilter intentFilter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.batterysaver_frag, container, false);
        btnUltraMode = view.findViewById(R.id.btnUltra_id);
        btnNormalMode = view.findViewById(R.id.btnNormalMode_id);
        btnOptimizedBattery = view.findViewById(R.id.btnOptimizedBattery_id);
        btnUsageBattery = view.findViewById(R.id.btnBatteryUsage_id);
        progressBar = view.findViewById(R.id.progressBarId);
        textViewPercentage=view.findViewById(R.id.sizeId);
        btnUltraMode.setOnClickListener(this);
        btnNormalMode.setOnClickListener(this);
        btnOptimizedBattery.setOnClickListener(this);
        btnUsageBattery.setOnClickListener(this);
        loadBatterySection();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnNormalMode_id:
                powerSavingModeON();
                break;
            case R.id.btnUltra_id:
                ultraModeON();
                break;
            case R.id.btnOptimizedBattery_id:
                optimizedBattery();
                break;
            case R.id.btnBatteryUsage_id:
                batteryUsage();
                break;
        }
    }

    private void powerSavingModeON() {
        PowerManager powerManager = (PowerManager) getActivity().getSystemService(POWER_SERVICE);
        if (powerManager.isPowerSaveMode()) {
            Toast.makeText(getActivity(), "Power Saving Mode Already ON", Toast.LENGTH_LONG).show();
        } else {
            Intent intentBatteryUsage = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
            startActivity(intentBatteryUsage);

        }
    }

    private void ultraModeON() {
        Intent batterySaverIntent = new Intent(Settings.ACTION_BATTERY_SAVER_SETTINGS);
        startActivity(batterySaverIntent);
        Toast.makeText(getActivity(), "Enable Ultra Mode", Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("BatteryLife")
    private void optimizedBattery() {
        final Dialog alert = new Dialog(getActivity());
        alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alert.setContentView(R.layout.battery_optimization);
        alert.setCancelable(true);
        alert.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        alert.show();

    }

    private void batteryUsage() {
        Intent powerUsageIntent = new Intent(Intent.ACTION_POWER_USAGE_SUMMARY);
        ResolveInfo resolveInfo = getActivity().getPackageManager().resolveActivity(powerUsageIntent, 0);
// check that the Battery app exists on this device
        if (resolveInfo != null) {
            startActivity(powerUsageIntent);
        } else {
            Toast.makeText(getActivity(), "Battery Usage not found", Toast.LENGTH_LONG).show();
        }

    }

    private BroadcastReceiver batteryInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            levelPer= intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < levelPer) {
                        progressStatus += 1;
                        handler.post(new Runnable() {
                            public void run() {
                                textViewPercentage.setText(progressStatus + "%");
                                progressBar.setProgress(progressStatus);
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    };

    private void loadBatterySection() {
         intentFilter= new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    }
    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(batteryInfoReceiver, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(batteryInfoReceiver);
    }


}
