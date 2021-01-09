package com.example.phonerepaire.Model;

public class HardwareTesting {
    String hardwareTestingName;
    int drawable;
    public HardwareTesting(String hardwareTestingName,int drawable){
        this.hardwareTestingName=hardwareTestingName;
        this.drawable=drawable;
    }

    public String getHardwareTestingName() {
        return hardwareTestingName;
    }

    public int getDrawable() {
        return drawable;
    }
}
