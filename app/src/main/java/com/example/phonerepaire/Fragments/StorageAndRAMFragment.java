package com.example.phonerepaire.Fragments;

import android.app.ActivityManager;
import android.app.usage.ExternalStorageStats;
import android.app.usage.StorageStatsManager;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

import static android.content.Context.ACTIVITY_SERVICE;

public class StorageAndRAMFragment extends Fragment {
    TextView totalStorageTextView, usedStorageTextView, freeStorageTextView, totalRAMTextView, usedRAMTextView, freeRAMTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storage_ram_fragment, container, false);
        totalRAMTextView = view.findViewById(R.id.total_ram_size_id);
        usedRAMTextView = view.findViewById(R.id.used_RAM_size_id);
        freeRAMTextView = view.findViewById(R.id.free_RAM_size_id);
        totalStorageTextView = view.findViewById(R.id.total_storage_size_id);
        usedStorageTextView = view.findViewById(R.id.used_storage_size_id);
        freeStorageTextView = view.findViewById(R.id.free_storage_size_id);
        storageInfo(getActivity());
        ramInfo();
        return view;
    }

    private void storageInfo(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            StorageStatsManager storageStatsManager = (StorageStatsManager) getActivity().getSystemService(Context.STORAGE_STATS_SERVICE);
            StorageManager storageManager = (StorageManager) getActivity().getSystemService(Context.STORAGE_SERVICE);
            if (storageManager == null || storageStatsManager == null) {
                return;
            }
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            for (StorageVolume storageVolume : storageVolumes) {
                final String uuidStr = storageVolume.getUuid();
                final UUID uuid = uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
                try {
                    Log.d("AppLog", "storage:" + uuid + " : " + storageVolume.getDescription(getActivity()) + " : " + storageVolume.getState());
                    Log.d("AppLog", "getFreeBytes:" + Formatter.formatShortFileSize(getActivity(), storageStatsManager.getFreeBytes(uuid)));
                    Log.d("AppLog", "getTotalBytes:" + Formatter.formatShortFileSize(getActivity(), storageStatsManager.getTotalBytes(uuid)));
                    totalStorageTextView.setText(bytesIntoHumanReadable(storageStatsManager.getTotalBytes(uuid)));
                    usedStorageTextView.setText(bytesIntoHumanReadable(storageStatsManager.getTotalBytes(uuid)-storageStatsManager.getFreeBytes(uuid)));
                    freeStorageTextView.setText(bytesIntoHumanReadable(storageStatsManager.getFreeBytes(uuid)));


                } catch (Exception e) {
                    // IGNORED
                }
            }
        } else {
            // available external storage
            StatFs statFree = new StatFs(Environment.getExternalStorageDirectory().getPath());
            double availableBytes = (double) statFree.getFreeBlocksLong() * (double) statFree.getBlockSizeLong();
            String availableInternalStorage = bytesIntoHumanReadable(availableBytes);
            freeStorageTextView.setText(availableInternalStorage);


            // total external storage
            StatFs statTotal = new StatFs(Environment.getExternalStorageDirectory().getPath());
            double totalBytes = (long) statTotal.getBlockSizeLong() * (double) statTotal.getBlockCountLong();
            String totalInternalStorage = bytesIntoHumanReadable(totalBytes);
            totalStorageTextView.setText(totalInternalStorage);

            // free external storage
            double usedStorage = totalBytes - availableBytes;
            String usedInternalStorage = bytesIntoHumanReadable(usedStorage);
            usedStorageTextView.setText(usedInternalStorage);
        }


    }

    private void ramInfo() {
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        activityManager.getMemoryInfo(memoryInfo);
        double availableRAMBytes = memoryInfo.availMem;
        double totalRAMBytes = memoryInfo.totalMem;
        totalRAMTextView.setText(bytesIntoHumanReadable(totalRAMBytes));
        usedRAMTextView.setText(bytesIntoHumanReadable(totalRAMBytes-availableRAMBytes));
        freeRAMTextView.setText(bytesIntoHumanReadable(availableRAMBytes));
        /*activityManager.getMemoryInfo(memoryInfo);
        Runtime runtime = Runtime.getRuntime();
        String totalMemory = bytesIntoHumanReadable(runtime.totalMemory());
        String freeMemory = bytesIntoHumanReadable(runtime.freeMemory());
        String usedMemory = bytesIntoHumanReadable(runtime.totalMemory() - runtime.freeMemory());*/
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
