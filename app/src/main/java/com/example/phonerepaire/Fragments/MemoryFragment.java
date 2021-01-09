package com.example.phonerepaire.Fragments;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.UserHandle;
import android.os.storage.StorageManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;

public class MemoryFragment extends Fragment {
    TextView totalRAM, usedRAM, totalInternal, usedInternal, totalExternal, usedExternal, usedAudio, usedVideo, usedImage, usedApps;
    ProgressBar progressBarRAM, progressBarInternal, progressBarExternal;
    int loadProgressValue = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.memory_fragment, container, false);
        totalRAM = view.findViewById(R.id.totalMemoryId);
        usedRAM = view.findViewById(R.id.usedMemoryId);
        usedExternal = view.findViewById(R.id.usedExternalId);
        totalExternal = view.findViewById(R.id.totalExternalMemoryId);
        usedInternal = view.findViewById(R.id.usedInternalId);
        totalInternal = view.findViewById(R.id.totalInternalMemoryId);
        usedAudio = view.findViewById(R.id.audioSizeId);
        usedVideo = view.findViewById(R.id.videoSizeId);
        usedImage = view.findViewById(R.id.imageSizeId);
        usedApps = view.findViewById(R.id.appSizeId);
        progressBarRAM = view.findViewById(R.id.ramProgressBarId);
        progressBarInternal = view.findViewById(R.id.internalProgressBarId);
        progressBarExternal = view.findViewById(R.id.externalProgressBarId);
        calculateRAM();
        externalMemory();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            internalMemory();
        } else {
            internalMemoryBelowOreo();
        }

        return view;
    }

    private void externalMemory() {
        if(externalMemoryAvailable(getActivity()))
        {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            long availableBlocks = stat.getAvailableBlocksLong();
            long totalExternalStorage=blockSize*totalBlocks;
            long usedExternalStorage=blockSize*availableBlocks;
            double percentage = usedExternalStorage / (double) totalExternalStorage * 100.0;
            int usedPercentageExternal = (int) percentage;
            progressBarExternal.setProgress(usedPercentageExternal);
            totalExternal.setText(bytesIntoHumanReadable(totalExternalStorage));
            usedExternal.setText(bytesIntoHumanReadable(usedExternalStorage));


        }
        else
        {
            progressBarExternal.setProgress(0);
            totalExternal.setText(String.valueOf(0));
            usedExternal.setText(String.valueOf(0));
            Toast.makeText(getActivity(),"First Insert SIM Card",Toast.LENGTH_LONG).show();
        }
    }
    public static boolean externalMemoryAvailable(Activity context) {
        File[] storages = ContextCompat.getExternalFilesDirs(context, null);
        if (storages.length > 1 && storages[0] != null && storages[1] != null)
            return true;
        else
            return false;

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void internalMemory() {
        StorageStatsManager storageStatsManager = (StorageStatsManager) getActivity().getSystemService(Context.STORAGE_STATS_SERVICE);
        ExternalStorageStats externalStorageStats = null;
        UserHandle user = android.os.Process.myUserHandle();
        try {
            externalStorageStats = storageStatsManager.queryExternalStatsForUser(StorageManager.UUID_DEFAULT, user);
            StatFs statTotal = new StatFs(Environment.getExternalStorageDirectory().getPath());
            long totalBytes = (long) statTotal.getBlockSizeLong() * (long) statTotal.getBlockCountLong();
            long imageBytes = externalStorageStats.getImageBytes();
            long audioBytes = externalStorageStats.getAudioBytes();
            long videoBytes = externalStorageStats.getVideoBytes();
            long appBytes = externalStorageStats.getAppBytes();
            long usedInternalStorage = imageBytes + audioBytes + videoBytes + appBytes;
            usedInternal.setText(bytesIntoHumanReadable(usedInternalStorage));
            usedImage.setText("Size: " + bytesIntoHumanReadable(imageBytes));
            usedAudio.setText("Size: " + bytesIntoHumanReadable(audioBytes));
            usedVideo.setText("Size: " + bytesIntoHumanReadable(videoBytes));
            usedApps.setText("Size: " + bytesIntoHumanReadable(appBytes));
            double percentAvail = usedInternalStorage / (double) totalBytes * 100.0;
            int usedPercentage = (int) percentAvail;
            totalInternal.setText(bytesIntoHumanReadable(totalBytes));
            progressBarInternal.setProgress(usedPercentage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void calculateRAM() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        double availableRAMBytes = memoryInfo.availMem;
        double totalRAMBytes = memoryInfo.totalMem;
        double usedRAMBytes=totalRAMBytes-availableRAMBytes;
        totalRAM.setText(bytesIntoHumanReadable(totalRAMBytes));
        usedRAM.setText(bytesIntoHumanReadable(totalRAMBytes - availableRAMBytes));
        double percentAvail = usedRAMBytes / (double) totalRAMBytes * 100.0;
        int usedMemory = (int) percentAvail;
        progressBarRAM.setProgress(usedMemory);
    }

    private void internalMemoryBelowOreo() {
        loadProgressValue = 0;
        // available external storage
        StatFs statFree = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double availableBytes = (double) statFree.getFreeBlocksLong() * (double) statFree.getBlockSizeLong();
        // total external storage
        StatFs statTotal = new StatFs(Environment.getExternalStorageDirectory().getPath());
        double totalBytes = (long) statTotal.getBlockSizeLong() * (double) statTotal.getBlockCountLong();
        String totalExternalStorage = bytesIntoHumanReadable(totalBytes);
        totalInternal.setText(totalExternalStorage);
        totalInternal.setText(bytesIntoHumanReadable(totalBytes - availableBytes));
        double availableStoragePer = availableBytes / (double) totalBytes * 100.0;
        int usageStoragePer = 100 - (int) availableStoragePer;
        progressBarInternal.setProgress(usageStoragePer);

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
}
