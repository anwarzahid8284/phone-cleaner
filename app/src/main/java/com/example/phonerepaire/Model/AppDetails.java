package com.example.phonerepaire.Model;

import android.graphics.drawable.Drawable;

public class AppDetails {
    String appPackage, appName, versionName, apkSize, firstTimeInstalled, lastUpdateInstalled;
    int versionCode, memoryUsed,storageUsed;
    Drawable appIcon;

    public AppDetails(String appPackage, String appName, int memoryUsed, int versionCode,
                      String versionName, String firstTimeInstalled,
                      String lastUpdateInstalled, String apkSize, Drawable appIcon, int storageUsed) {
        this.appPackage = appPackage;
        this.appName = appName;
        this.appIcon = appIcon;
        this.apkSize = apkSize;
        this.firstTimeInstalled = firstTimeInstalled;
        this.lastUpdateInstalled = lastUpdateInstalled;
        this.memoryUsed = memoryUsed;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.storageUsed = storageUsed;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public int getMemoryUsed() {
        return memoryUsed;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getFirstTimeInstalled() {
        return firstTimeInstalled;
    }

    public String getLastUpdateInstalled() {
        return lastUpdateInstalled;
    }

    public String getApkSize() {
        return apkSize;
    }

    public int getStorageUsed() {
        return storageUsed;
    }
}
