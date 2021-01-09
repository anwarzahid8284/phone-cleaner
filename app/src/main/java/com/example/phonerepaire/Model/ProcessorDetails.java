package com.example.phonerepaire.Model;

public class ProcessorDetails {
    String cpu, cpuName;

    public ProcessorDetails(String cpu,String cpuName) {
        this.cpu = cpu;
        this.cpuName=cpuName;
    }

    public String getCpu() {
        return cpu;
    }

    public String getCpuName() {
        return cpuName;
    }
}
