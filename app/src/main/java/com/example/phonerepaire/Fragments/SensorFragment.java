package com.example.phonerepaire.Fragments;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonerepaire.Adpatar.SensorAdapter;
import com.example.phonerepaire.Model.SensorDetails;
import com.example.phonerepaire.R;

import java.util.ArrayList;
import java.util.List;

public class SensorFragment extends Fragment {
    RecyclerView recyclerView;
    List<SensorDetails> sensorDetailsList;
    SensorAdapter sensorAdapter;
    List<Sensor> sensorList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sensor_fragment, container, false);
        recyclerView = view.findViewById(R.id.sensor_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        sensorDetailsList = new ArrayList<>();
        showSensor(requireActivity());
        return view;
    }

    private void showSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        for (int i = 0; i < sensorList.size(); i++) {
            sensorDetailsList.add(new SensorDetails(String.valueOf(sensorList.get(i))));
        }
        sensorAdapter=new SensorAdapter(sensorDetailsList,getActivity());
        recyclerView.setAdapter(sensorAdapter);
    }
}
