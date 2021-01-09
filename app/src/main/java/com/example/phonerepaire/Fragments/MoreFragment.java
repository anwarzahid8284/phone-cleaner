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

import com.example.phonerepaire.Adpatar.MoreAdapter;
import com.example.phonerepaire.Model.MoreInfo;
import com.example.phonerepaire.R;

import java.util.ArrayList;
import java.util.List;

public class MoreFragment extends Fragment {
    RecyclerView recyclerView;
    List<MoreInfo> moreInfoList;
    MoreAdapter moreAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.more_frag, container, false);
        moreInfoList = new ArrayList<>();
        recyclerView=view.findViewById(R.id.more_recycler_view_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        String[] name = {"Battery", "Battery Usage", "Display Setting", "Save Battery",
                "Storage", "Storage Setting", "Phone", "About Phone",
                "Bluetooth Setting", "Manage Network Usage","Network Operators",
                "Wireless Setting","All Wifi Networks","Wifi Advanced Setting","Data Roaming Setting"};
        for(int i=0; i<name.length; i++){
            moreInfoList.add(new MoreInfo(name[i]));
        }
        moreAdapter=new MoreAdapter(moreInfoList,getActivity());
        recyclerView.setAdapter(moreAdapter);
        return view;
    }

}
