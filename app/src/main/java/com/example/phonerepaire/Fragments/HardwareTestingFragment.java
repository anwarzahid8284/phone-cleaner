package com.example.phonerepaire.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Adpatar.HardwareTestingAdapter;
import com.example.phonerepaire.Model.HardwareTesting;
import com.example.phonerepaire.R;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class HardwareTestingFragment extends Fragment{
    RecyclerView recyclerView;
    HardwareTestingAdapter hardwareTestingAdapter;
    List<HardwareTesting> hardwareTestingList;
    SweetAlertDialog pDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hardware_testing_frag, container, false);
        recyclerView=view.findViewById(R.id.hardware_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        recyclerView.setHasFixedSize(true);
        hardwareTestingList=new ArrayList<>();
        String [] testingName={"Display","Vibration Test","SIM Card","Touch Sensor",
                "Speaker Test","Check Headphone","Check Bluetooth","Flashlight","Fingerprint Test"};
        int [] testingImage={R.drawable.ic_cell_phone,R.drawable.ic_vibration,R.drawable.ic_simcard,R.drawable.ic_touchscreen
        ,R.drawable.ic_speaker,R.drawable.ic_headphones,R.drawable.ic_bluetooth,R.drawable.ic_flashlight
        ,R.drawable.ic_fingerprint};
        for(int i=0;i<testingName.length;i++){
            hardwareTestingList.add(new HardwareTesting(testingName[i],testingImage[i]));
        }
        hardwareTestingAdapter=new HardwareTestingAdapter(hardwareTestingList,getActivity());
        recyclerView.setAdapter(hardwareTestingAdapter);
        return view;
    }

}
