package com.example.phonerepaire.Fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CleanCacheFragment extends Fragment implements View.OnClickListener {
    TextView textViewTotalSize, textViewProcesses, textViewMemory, textViewUnInstalled,
            textViewTemporary, textViewCallLogs, fileSize;
    List<String> fileList;
    Button buttonClear;
    File file, fileDirectory;
    long length = 0, directorySize = 0, totalLength = 0, emptyFolderSize = 0, callHistorySize = 0, totalProcessSize = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.clean_cache_frag, container, false);
        textViewTotalSize = view.findViewById(R.id.totalSizeId);
        textViewProcesses = view.findViewById(R.id.process_size_id);
        textViewMemory = view.findViewById(R.id.clear_memory_size_id);
        textViewUnInstalled = view.findViewById(R.id.uninstalledApp_size_id);
        textViewTemporary = view.findViewById(R.id.clear_temporary_size_id);
        textViewCallLogs = view.findViewById(R.id.log_size_id);
        fileSize = view.findViewById(R.id.sizeId);
        buttonClear = view.findViewById(R.id.clear_cache_btn_id);
        fileList = new ArrayList<>();
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data");
        fileDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        callSize();
        processesSize();
        clearTempFiles(file);
        emptyFolderSize(fileDirectory);
        Objects.requireNonNull(getActivity()).getContentResolver().delete(CallLog.Calls.CONTENT_URI, null, null);

        long totalSize = totalLength + emptyFolderSize + callHistorySize + totalProcessSize;
        textViewTotalSize.setText(bytesIntoHumanReadable(totalSize));
        fileSize.setText(bytesIntoHumanReadable(totalSize));
        buttonClear.setOnClickListener(this::onClick);
        return view;
    }

    public void clearMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public void processesSize() {
        ActivityManager am = (ActivityManager) getActivity().getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo pid : am.getRunningAppProcesses()) {
            totalProcessSize += pid.processName.length();
            am.killBackgroundProcesses(pid.processName);
        }
        textViewProcesses.setText(bytesIntoHumanReadable(totalProcessSize));
    }

    public void emptyFolderSize(File file) {
        if (file.isDirectory() & file.exists()) {
            try {
                for (File file1 : file.listFiles()) {
                    if (file1 != null) {
                        if (file1.isDirectory() & file1.listFiles().length > 0) {
                            emptyFolderSize(file1);
                        } else if (file1.listFiles().length == 0) {
                            fileList.add(file1.getAbsolutePath());
                            emptyFolderSize += file1.length();
                        }
                    }
                }
            } catch (NullPointerException e) {
                e.getMessage();
            }

        }
        String emptySize = bytesIntoHumanReadable(emptyFolderSize);
        textViewUnInstalled.setText(emptySize);

    }

    public void callSize() {
        callHistorySize = CallLog.AUTHORITY.length();
        textViewCallLogs.setText(bytesIntoHumanReadable(callHistorySize));

    }

    public void clearTempFiles(File file) {
        if (file.length() > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    length += file1.length();
                    fileList.add(file1.getAbsolutePath());
                } else {
                    directorySize += file1.length();
                    clearTempFiles(file1);
                }
            }
            totalLength = length + directorySize;
        }
        textViewTemporary.setText(bytesIntoHumanReadable(totalLength));
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
        clearCache();
        textViewTemporary.setText("0 B");
        textViewCallLogs.setText("0 B");
        textViewUnInstalled.setText("0 B");
        textViewProcesses.setText("0 B");
        textViewTotalSize.setText("0 B");
        fileSize.setText("0 B");
        textViewMemory.setText("0 B");



    }

    private void clearCache() {
        File fileDelete;
        for (int i = 0; i < fileList.size(); i++) {
            fileDelete = new File(fileList.get(i));
            if (fileDelete.exists() & fileDelete.isFile()) {
                fileList.remove(i);
                fileDelete.delete();
            } else {
                fileDelete.delete();
            }
        }
        clearMemory();
        processesSize();
        if (fileList.isEmpty()) {
            Toast.makeText(getActivity(), "Cache Cleared", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "fileList not deleted", Toast.LENGTH_SHORT).show();

        }
    }
}
