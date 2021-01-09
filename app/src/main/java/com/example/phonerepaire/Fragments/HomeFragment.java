package com.example.phonerepaire.Fragments;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.Activity.ToolActivity;
import com.example.phonerepaire.R;

import java.text.DecimalFormat;
import java.util.Objects;

public class HomeFragment extends Fragment implements View.OnClickListener {
    TextView textViewSystemUsage, textViewAvailableRAMID, textViewTotalRAM,
            textViewBatteryPer, textViewStoragePer, textViewTotalStorage,
            textViewFreeStorage, textViewRamPer, cleanTextView, manageTextView, repairTextView,
            ramTextView, emptyFTextView, batteryTextView,textViewWifi,
            textViewHardTesting,textViewMemory;
    ProgressBar ramProgressBar, batteryProgressBar, storageProgressBar;
     int progressStatus = 0,batteryStatus=0;
    Handler handler = new Handler(Looper.getMainLooper());
    ImageView imageViewClean, imageViewManageApp, imageViewRepair, imageViewRam,
            imageViewEFolder, imageViewBatteryS,imageViewWifi,
            imageViewHardTesting,imageViewMemory;
    Button buttonStorage, buttonBattery;
    IntentFilter intentFilter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_frag, container, false);
        imageViewClean = view.findViewById(R.id.videoImageViewId);
        imageViewManageApp = view.findViewById(R.id.applicationImageViewId);
        imageViewRepair = view.findViewById(R.id.repairImageViewId);
        imageViewRam = view.findViewById(R.id.audioImageViewId);
        imageViewEFolder = view.findViewById(R.id.imageImageViewId);
        imageViewBatteryS = view.findViewById(R.id.batterySaverImageViewId);
        imageViewWifi=view.findViewById(R.id.wifiSpeedImageViewId);
        imageViewHardTesting=view.findViewById(R.id.hardwareTestImageViewId);
        imageViewMemory=view.findViewById(R.id.memoryImageViewId);

        buttonStorage = view.findViewById(R.id.storageSettingId);
        buttonBattery = view.findViewById(R.id.batteryInformationId);
        cleanTextView = view.findViewById(R.id.videoSizeId);
        manageTextView = view.findViewById(R.id.appSizeId);
        repairTextView = view.findViewById(R.id.repair_system_id);
        ramTextView = view.findViewById(R.id.audioSizeId);
        emptyFTextView = view.findViewById(R.id.imageSizeId);
        batteryTextView = view.findViewById(R.id.battery_saver_id);
        textViewWifi=view.findViewById(R.id.wifi_text_id);
        textViewHardTesting=view.findViewById(R.id.hardware_text_id);
        textViewMemory=view.findViewById(R.id.memory_text_id);



        textViewSystemUsage = view.findViewById(R.id.systemUsageId);
        textViewAvailableRAMID = view.findViewById(R.id.availableRamID);
        textViewTotalRAM = view.findViewById(R.id.totalRamId);
        textViewBatteryPer = view.findViewById(R.id.batteryPercentageTextId);
        textViewStoragePer = view.findViewById(R.id.storagePercentageTextId);
        textViewTotalStorage = view.findViewById(R.id.totalStorageValueId);
        textViewFreeStorage = view.findViewById(R.id.freeStorageValueId);
        ramProgressBar = view.findViewById(R.id.ramProgressBarId);
        batteryProgressBar = view.findViewById(R.id.batteryProgressBarId);
        storageProgressBar = view.findViewById(R.id.storageProgressBarId);
        textViewRamPer = view.findViewById(R.id.ramPerTextId);

        buttonStorage.setOnClickListener(this);
        buttonBattery.setOnClickListener(this);
        // click listener
        imageViewClean.setOnClickListener(this);
        imageViewManageApp.setOnClickListener(this);
        imageViewRepair.setOnClickListener(this);
        imageViewRam.setOnClickListener(this);
        imageViewEFolder.setOnClickListener(this);
        imageViewBatteryS.setOnClickListener(this);
        imageViewWifi.setOnClickListener(this);
        imageViewHardTesting.setOnClickListener(this);
        imageViewMemory.setOnClickListener(this);

        cleanTextView.setOnClickListener(this);
        manageTextView.setOnClickListener(this);
        repairTextView.setOnClickListener(this);
        ramTextView.setOnClickListener(this);
        emptyFTextView.setOnClickListener(this);
        batteryTextView.setOnClickListener(this);
        textViewWifi.setOnClickListener(this);
        textViewHardTesting.setOnClickListener(this);
        textViewMemory.setOnClickListener(this);
        Intent intent=new Intent(BatteryManager.EXTRA_LEVEL);
        batteryCalculate();
        try {
            calculateRAM();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        calculateStorage();
        return view;
    }

    private void calculateStorage() {
        // available external storage
        StatFs statFree = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double availableBytes = (double) statFree.getFreeBlocksLong() * (double) statFree.getBlockSizeLong();
        String availableExternalStorage = bytesIntoHumanReadable(availableBytes);
        textViewFreeStorage.setText(availableExternalStorage);

        // total external storage
        StatFs statTotal = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double totalBytes = (long) statTotal.getBlockSizeLong() * (double) statTotal.getBlockCountLong();
        String totalExternalStorage = bytesIntoHumanReadable(totalBytes);
        textViewTotalStorage.setText(totalExternalStorage);
        double availableStoragePer = availableBytes / (double) totalBytes * 100.0;
        final int usageStoragePer = 100 - (int) availableStoragePer;
        new Thread(() -> {
            while (progressStatus < usageStoragePer) {
                progressStatus += 1;
                // Update the progress bar and display the
                //current value in the text view
                handler.post(new Runnable() {
                    public void run() {
                        textViewStoragePer.setText(progressStatus + "%");
                        storageProgressBar.setProgress(progressStatus);
                    }
                });
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private void calculateRAM() throws PackageManager.NameNotFoundException {

        MemoryInfo memoryInfo = new MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        double availableRAMBytes = memoryInfo.availMem;
        double totalRAMBytes = memoryInfo.totalMem;
        double systemRAMBytes = totalRAMBytes - availableRAMBytes;
        double percentAvail = availableRAMBytes / (double) totalRAMBytes * 100.0;
        int availableRAMPer = (int) percentAvail;
        final int usageRAMPer = 100 - availableRAMPer;

        if (availableRAMBytes <= 0) {
            textViewAvailableRAMID.setText("0.0 B");
        } else {
            String availableRAM = bytesIntoHumanReadable(availableRAMBytes);
            textViewAvailableRAMID.setText(availableRAM);
        }
        if (memoryInfo.totalMem <= 0) {
            textViewTotalRAM.setText("0.0 B");
        } else {
            String totalRAM = bytesIntoHumanReadable(totalRAMBytes);
            textViewTotalRAM.setText(totalRAM);
        }
        new Thread(() -> {
            while (progressStatus < usageRAMPer) {
                progressStatus += 1;
                // Update the progress bar and display the
                //current value in the text view
                handler.post(() -> {
                    ramProgressBar.setProgress(progressStatus);
                    textViewRamPer.setText(progressStatus + "%");
                });
                try {
                    // Sleep for 200 milliseconds.
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // system app size
        String systemRAM = bytesIntoHumanReadable(systemRAMBytes);
        textViewSystemUsage.setText(systemRAM);

    }

    // receiver to receive the battery percentage
    BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int levelPer= intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            new Thread(() -> {
                while (batteryStatus < levelPer) {
                    batteryStatus += 1;
                    handler.post(() -> {
                        textViewBatteryPer.setText(batteryStatus + "%");
                        batteryProgressBar.setProgress(batteryStatus);
                    });
                    try {
                        // Sleep for 200 milliseconds.
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };
    public void batteryCalculate(){
       intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
    }

    @Override
    public void onResume() {
        super.onResume();
        requireActivity().registerReceiver(this.mBatInfoReceiver,intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        requireActivity().unregisterReceiver(mBatInfoReceiver);

    }


    private String bytesIntoHumanReadable(double bytes) {
        DecimalFormat precision = new DecimalFormat("#.#");
        double kilobyte = 1024;
        double megabyte = kilobyte * 1024;
        double gigabyte = megabyte * 1024;
        double terabyte = gigabyte * 1024;
        double result = 0L;

        if ((bytes >= 0) && (bytes < kilobyte)) {
            result = bytes;
            return precision.format(result) + " B";

        } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
            result = bytes / kilobyte;
            return precision.format(result) + " KB";

        } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
            result = bytes / megabyte;
            return precision.format(result) + " MB";

        } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
            result = bytes / gigabyte;
            return precision.format(result) + " GB";

        } else if (bytes >= terabyte) {
            result = (bytes / terabyte);
            return precision.format(result) + " TB";

        } else {
            result = bytes;
            return precision.format(result) + " Bytes";
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ToolActivity.class);
        switch (v.getId()) {
            case R.id.videoSizeId:
            case R.id.videoImageViewId:
                intent.putExtra("selectOption", "CleanCache");
                break;
            case R.id.appSizeId:
            case R.id.applicationImageViewId:
                intent.putExtra("selectOption", "ManageApps");
                break;
            case R.id.repair_system_id:
            case R.id.repairImageViewId:
                intent.putExtra("selectOption", "RepairSystem");
                break;
            case R.id.audioSizeId:
            case R.id.audioImageViewId:
                intent.putExtra("selectOption", "BoosterRAM");
                break;
            case R.id.imageSizeId:
            case R.id.imageImageViewId:
                intent.putExtra("selectOption", "EmptyFolder");

                break;
            case R.id.battery_saver_id:
            case R.id.batterySaverImageViewId:
                intent.putExtra("selectOption", "BatterySaver");
                break;
            case R.id.wifi_text_id:
            case R.id.wifiSpeedImageViewId:
                intent.putExtra("selectOption", "WifiSpeed");
                break;
            case R.id.hardware_text_id:
            case R.id.hardwareTestImageViewId:
                intent.putExtra("selectOption", "HardwareTesting");
                break;
            case R.id.memoryImageViewId:
            case R.id.memory_text_id:
                intent.putExtra("selectOption", "memory");
                break;
            case R.id.storageSettingId:
                 intent = new Intent(android.provider.Settings.ACTION_INTERNAL_STORAGE_SETTINGS);
                break;
            case R.id.batteryInformationId:
                intent.putExtra("selectOption", "BatteryFragment");
                break;
        }
        startActivity(intent);
    }
}
