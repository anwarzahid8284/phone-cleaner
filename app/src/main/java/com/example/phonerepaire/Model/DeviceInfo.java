package com.example.phonerepaire.Model;

public class DeviceInfo {
    String infoName;
    int drawable;
    public DeviceInfo(String infoName,int drawable){
        this.infoName=infoName;
        this.drawable=drawable;
    }
    public String getInfoName() {
        return infoName;
    }

    public int getDrawable() {
        return drawable;
    }

}
