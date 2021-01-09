package com.example.phonerepaire.Fragments;

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

public class EmptyFolderFragment extends Fragment implements View.OnClickListener {

    Button buttonClear;
    TextView textViewNoFileMessage, clearTextFile, sizeTextView;
    File file;
    long length = 0, directorySize = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.emptyfolder_frag, container, false);
        buttonClear = view.findViewById(R.id.btn_emp_folder_clear_id);
        textViewNoFileMessage = view.findViewById(R.id.noFileMessage_id);
        clearTextFile = view.findViewById(R.id.clear_data_id);
        sizeTextView = view.findViewById(R.id.sizeId);
        buttonClear.setOnClickListener(this);
        file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data");
        loadEmptyFolder(file);
        return view;
    }

    @Override
    public void onClick(View v) {
        clearCache(file);
    }

    private void clearCache(File file) {
        if (file.length() > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    file1.delete();
                } else {
                    clearCache(file1);
                }
            }
        }
        sizeTextView.setText("0 B");
        clearTextFile.setText("Cache Cleared");
        Toast.makeText(getActivity(), "Junk Files Clear", Toast.LENGTH_SHORT).show();

    }

    private void loadEmptyFolder(File file) {
        if (file.length() > 0) {
            for (File file1 : file.listFiles()) {
                if (file1.isFile()) {
                    length += file1.length();
                } else {
                    directorySize += file1.length();
                    loadEmptyFolder(file1);
                }
            }
            long totalLength = length + directorySize;
            if (totalLength > 1048576) {
                String size = bytesIntoHumanReadable(totalLength);
                sizeTextView.setText(size);
                clearTextFile.setText("Cache Data:  " + size);
            } else {
                textViewNoFileMessage.setVisibility(View.VISIBLE);
                sizeTextView.setText("0 B");
                clearTextFile.setText("Cache Cleared");
            }

        } else {
            textViewNoFileMessage.setVisibility(View.VISIBLE);
            sizeTextView.setText("0 B");
            clearTextFile.setText("Cache Cleared");

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
}

