package com.example.phonerepaire.Fragments;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
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
import java.util.List;

public class RamBoosterFragment extends Fragment implements View.OnClickListener {
    Button btnRamBooster;
    TextView textViewBP;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.rambooster_frag, container, false);
        btnRamBooster = view.findViewById(R.id.btnRamBooster);
        textViewBP = view.findViewById(R.id.textBackgroundProcess_id);
        btnRamBooster.setOnClickListener(this);

        return view;
    }

    public void freeMemory() {
        System.runFinalization();
        Runtime.getRuntime().gc();
        System.gc();
    }

    public static void ramCacheClear(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        ramCacheClear(getActivity());
        freeMemory();
        killBackgroundProcess();

    }

    protected void killBackgroundProcess() {
        new Thread(() -> {
             backgroundProcess();
            getActivity().runOnUiThread(() -> {
                Toast.makeText(getActivity(), "Killing Process ", Toast.LENGTH_SHORT).show();
            });
        }).start();
    }


    private void backgroundProcess() {
        List<PackageInfo> packages;
        PackageManager pm;
        pm = getActivity().getPackageManager();
        //get a list of installed apps.
        packages = pm.getInstalledPackages(0);

        ActivityManager mActivityManager = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);

        for (PackageInfo packageInfo : packages) {
            if((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM)==1)
            {
                continue;
            }
            if(packageInfo.packageName.equals("com.example.phonerepaire")){
                continue;
            }
            mActivityManager.killBackgroundProcesses(packageInfo.applicationInfo.processName);
        }
    }
}

