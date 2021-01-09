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

import com.example.phonerepaire.Adpatar.ProcessorAdapter;
import com.example.phonerepaire.Model.ProcessorDetails;
import com.example.phonerepaire.R;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PorcessorFragment extends Fragment {
    RecyclerView recyclerView;
    List<ProcessorDetails> processorDetails;
    ProcessorAdapter processorAdapter;
    List<String> stringList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.processor_fragment, container, false);
        recyclerView = view.findViewById(R.id.processor_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        processorDetails = new ArrayList<>();
        stringList=new ArrayList<>();
        showProcessorDetails();
        return view;
    }

    private void showProcessorDetails() {
        try {
            // CPU api
            String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
            ProcessBuilder processBuilder = new ProcessBuilder(DATA);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            byte[] byteArry = new byte[1024];
            String output = "";
            while (inputStream.read(byteArry) != -1) {
                output = output + new String(byteArry);
            }
            String [] splitList=output.split("\n");
            stringList.addAll(Arrays.asList(splitList));
            for(int i=0; i<stringList.size();i++){
                if(!stringList.get(i).equals("")){
                    String value=stringList.get(i);
                    String [] splitValue= new String[0];
                    if(value.contains(":")){
                        splitValue=value.split(":");
                    }else {
                        splitValue=value.split(" ");
                    }
                    processorDetails.add(new ProcessorDetails(splitValue[0],splitValue[1]));
                }

            }
            processorAdapter = new ProcessorAdapter(processorDetails, getActivity());
            recyclerView.setAdapter(processorAdapter);
            inputStream.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
