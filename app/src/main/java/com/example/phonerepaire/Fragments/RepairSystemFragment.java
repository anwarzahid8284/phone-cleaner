package com.example.phonerepaire.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Objects;

public class RepairSystemFragment extends Fragment implements View.OnClickListener {
    TextView textViewJunkFSize, textViewBackgroundProcessSize, textViewTotalSize;
    long fileSize = 0, folderSize = 0, processSize = 0, fileFolderSize = 0, totalSize = 0;
    File file;
    Button buttonClear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repairsystem_frag, container, false);
        textViewJunkFSize = view.findViewById(R.id.junkFileSizeID);
        textViewBackgroundProcessSize = view.findViewById(R.id.backProcessSizeID);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data");
        textViewTotalSize = view.findViewById(R.id.totalSizeId);
        buttonClear = view.findViewById(R.id.btnClear_id);
        buttonClear.setOnClickListener(this::onClick);
        cacheSize(getActivity());
        loadEmptyFolder(file);
        totalSize = processSize + fileFolderSize;
        textViewTotalSize.setText(bytesIntoHumanReadable(totalSize));
        return view;
    }

    public void cacheSize(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                processSize = dir.length();
                String cacheSize = bytesIntoHumanReadable(dir.length());
                textViewBackgroundProcessSize.setText(cacheSize);
            }
        } catch (Exception e) {

        }
    }

    public boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        }
        return false;
    }

    private void loadEmptyFolder(File file) {
        if (file.length() > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    fileSize += file1.length();
                } else {
                    folderSize += file1.length();
                    loadEmptyFolder(file1);
                }
            }
            fileFolderSize = fileSize + folderSize;
            String size = bytesIntoHumanReadable(fileFolderSize);
            textViewJunkFSize.setText(size);
        }
    }

    private void deleteEmptyFolder(File file) {
        if (file.length() > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    file1.delete();
                } else {
                    deleteEmptyFolder(file1);
                    Toast.makeText(getActivity(), "Clear All data", Toast.LENGTH_LONG).show();
                }
            }
        }
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
        File dir = requireActivity().getCacheDir();
        if (dir != null & dir.isDirectory()) {
            if (deleteDir(dir)) {
                if (file.isDirectory() & file != null) {
                    deleteEmptyFolder(file);
                }
            }

        }
        textViewJunkFSize.setText("0 B");
        textViewTotalSize.setText("0 B");

    }
}
