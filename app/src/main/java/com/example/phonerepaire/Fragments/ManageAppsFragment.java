package com.example.phonerepaire.Fragments;

import android.annotation.SuppressLint;
import android.app.AsyncNotedAppOp;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Adpatar.AppsAdapter;
import com.example.phonerepaire.Model.AppDetails;
import com.example.phonerepaire.R;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.USAGE_STATS_SERVICE;

public class ManageAppsFragment extends Fragment implements View.OnClickListener {
    Button btnInstalledApps, btnSystemApps;
    RecyclerView recyclerView;
    AppsAdapter appsAdapter;
    List<AppDetails> installedAppList, systemAppList;
    SweetAlertDialog pDialog;
    SimpleDateFormat sdf;

    @SuppressLint("SimpleDateFormat")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.manageapps_frag, container, false);
        btnInstalledApps = view.findViewById(R.id.btn_installed_app_id);
        btnSystemApps = view.findViewById(R.id.btn_system_app_id);
        recyclerView = view.findViewById(R.id.managment_app_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
       pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);

        sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
        recyclerView.setHasFixedSize(true);
        installedAppList = new ArrayList<>();
        systemAppList = new ArrayList<>();
        btnSystemApps.setOnClickListener(this);
        btnInstalledApps.setOnClickListener(this);

        new LoadApp().execute();
        return view;
    }
    public  class LoadApp extends AsyncTask<Void,Void,Void>{

        @Override
        protected void onPreExecute() {
            pDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            loadInstalledApps();
            loadSystemApps();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pDialog.dismiss();
            appsAdapter = new AppsAdapter(installedAppList, getActivity(), true);
            recyclerView.setAdapter(appsAdapter);
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_system_app_id:
                appsAdapter = new AppsAdapter(systemAppList, getActivity(), false);
                recyclerView.setAdapter(appsAdapter);
                appsAdapter.notifyDataSetChanged();
                break;
            case R.id.btn_installed_app_id:
                appsAdapter = new AppsAdapter(installedAppList, getActivity(), true);
                recyclerView.setAdapter(appsAdapter);
                appsAdapter.notifyDataSetChanged();
                break;
        }
    }

    private void loadInstalledApps() {
        UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - 1000 * 3600 * 24,
                System.currentTimeMillis());
        int memoryUsed = 0;
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo p = packageInfos.get(i);
            if ((!isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                int storageSize = p.applicationInfo.loadLabel(getActivity().getPackageManager()).length();
                Drawable icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
                String packages = p.applicationInfo.packageName;
                String versionName = p.versionName;
                int versionCode = p.versionCode;
                String appFirstTimeInstall = sdf.format(new Date(p.firstInstallTime));
                String appLastUpdate = sdf.format(new Date(p.lastUpdateTime));
                File file = new File(p.applicationInfo.publicSourceDir);
                long apkSize = file.length();
                String sizeApk = bytesIntoHumanReadable(apkSize);
                installedAppList.add(new AppDetails(packages, appName, memoryUsed, versionCode,
                        versionName, appFirstTimeInstall,
                        appLastUpdate, sizeApk, icon, storageSize));
            }
        }

    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return (pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    private void loadSystemApps() {
        UsageStatsManager usm = (UsageStatsManager) getActivity().getSystemService(USAGE_STATS_SERVICE);
        List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                System.currentTimeMillis() - 1000 * 3600 * 24,
                System.currentTimeMillis());
        int memoryUsed = appList.size();
        PackageManager packageManager = getActivity().getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (int i = 0; i < packageInfos.size(); i++) {
            PackageInfo p = packageInfos.get(i);
            if ((isSystemPackage(p))) {
                String appName = p.applicationInfo.loadLabel(getActivity().getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getActivity().getPackageManager());
                String packages = p.applicationInfo.packageName;
                String versionName = p.versionName;
                int versionCode = p.versionCode;
                int storageSize = p.applicationInfo.loadLabel(getActivity().getPackageManager()).length();
                String appFirstTimeInstall = sdf.format(new Date(p.firstInstallTime));
                String appLastUpdate = sdf.format(new Date(p.lastUpdateTime));
                File file = new File(p.applicationInfo.publicSourceDir);
                long apkSize = file.length();
                String sizeApk = bytesIntoHumanReadable(apkSize);
                systemAppList.add(new AppDetails(packages, appName, memoryUsed, versionCode,
                        versionName, appFirstTimeInstall,
                        appLastUpdate, sizeApk, icon, storageSize));
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
}
