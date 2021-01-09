package com.example.phonerepaire.Fragments;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

public class DisplayFragment extends Fragment {
    TextView textViewSSize, textViewPSize, textViewSWidth, textViewSHeight, textViewRRate,
            textViewName, textViewXdpi, textViewYdpi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.display_fragment, container, false);
        textViewSSize = view.findViewById(R.id.screen_size_status_id);
        textViewPSize = view.findViewById(R.id.physical_status_id);
        textViewSWidth = view.findViewById(R.id.screen_width_status_id);
        textViewSHeight = view.findViewById(R.id.screen_height_status_id);
        textViewName = view.findViewById(R.id.name_status_id);
        textViewXdpi = view.findViewById(R.id.xdpi_status_id);
        textViewYdpi = view.findViewById(R.id.ydpi_status_id);
        textViewRRate = view.findViewById(R.id.refresh_status_id);
        showScreenResoultion();
        return view;
    }

    private void showScreenResoultion() {
        int screenSize = getResources().getConfiguration().screenLayout &
                Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                textViewSSize.setText("Large screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                textViewSSize.setText("Normal screen");
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                textViewSSize.setText("Small screen");
                break;
        }

        Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        display.getRealMetrics(displaymetrics);
        textViewSWidth.setText(""+displaymetrics.widthPixels);
        textViewSHeight.setText(""+displaymetrics.heightPixels);
        textViewXdpi.setText("" + displaymetrics.xdpi);
        textViewYdpi.setText("" + displaymetrics.ydpi);
        textViewRRate.setText(""+display.getRefreshRate());
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        double x = Math.pow(displaymetrics.widthPixels/displaymetrics.xdpi,2);
        double y = Math.pow(displaymetrics.heightPixels/displaymetrics.ydpi,2);
        double screenInches = Math.sqrt(x+y);
        textViewPSize.setText(""+screenInches);
        textViewName.setText(display.getName());

    }
}
