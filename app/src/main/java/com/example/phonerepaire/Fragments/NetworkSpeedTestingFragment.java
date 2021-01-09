package com.example.phonerepaire.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.phonerepaire.R;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.text.DecimalFormat;
import java.util.Objects;

import fr.bmartel.speedtest.SpeedTestReport;
import fr.bmartel.speedtest.SpeedTestSocket;
import fr.bmartel.speedtest.inter.ISpeedTestListener;
import fr.bmartel.speedtest.model.SpeedTestError;

public class NetworkSpeedTestingFragment extends Fragment implements View.OnClickListener {
    TextView textViewDownloadSpeed, textViewUploadSpeed, textViewPingSpeed;
    DecimalFormat decimalFormat;
    Button btnTestAgain;
    SpeedTestSocket speedTestSocket;
    boolean check = false;
    TextView textViewNetworkName, textViewIpAddress;
    ConnectivityManager connectivityManager;
    long timeOfPing;
    Handler handler = new Handler(Looper.getMainLooper());
    Thread thread;
    String ip;
    int start=0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.network_speed_frag, container, false);
        textViewDownloadSpeed = view.findViewById(R.id.download_speed_text_id);
        textViewUploadSpeed = view.findViewById(R.id.upload_speed_text_id);
        textViewPingSpeed = view.findViewById(R.id.ping_speed_text_id);
        btnTestAgain = view.findViewById(R.id.button_test_again);
        textViewNetworkName = view.findViewById(R.id.newtworkNameId);
        textViewIpAddress = view.findViewById(R.id.ipAddressId);
        connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        btnTestAgain.setOnClickListener(this::onClick);
        try {
            netWorkName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        speedTestSocket = new SpeedTestSocket();
        decimalFormat = new DecimalFormat("#.##");
        if (isNetworkAvailable()) {
           thread=new Thread(){
               @Override
               public void run() {
                  test();
               }

           };
           thread.start();
        } else {
            Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

        return view;
    }

    void test() {
        if (check) {
            speedTestSocket.startUpload("http://ipv4.ikoula.testdebit.info/", 1000000);
        } else {
            speedTestSocket.startDownload("http://ipv4.ikoula.testdebit.info/100M.iso");
        }
        speedTestSocket.addSpeedTestListener(new ISpeedTestListener() {
            @Override
            public void onCompletion(SpeedTestReport report) {
                if (check) {
                    long iteration=networkPing(ip);
                    int iterate=(int)iteration;
                    new Thread(() -> {
                        while ( start<=iterate ) {

                            handler.post(() -> {
                                textViewPingSpeed.setText(String.valueOf(start));
                                if(start==iterate){
                                    btnTestAgain.setVisibility(View.VISIBLE);
                                }
                            });
                            try {
                                // Sleep for 100 milliseconds.
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            start += 1;
                        }

                    }).start();


                } else {
                    check = true;
                    test();
                }

            }

            @Override
            public void onError(SpeedTestError speedTestError, String errorMessage) {
                // called when a download/upload error occur
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onProgress(float percent, SpeedTestReport report) {
                // called to notify download/upload progress
                Log.v("Speed", "[PROGRESS] progress : " + percent + "%");
                Log.v("Speed", "[PROGRESS] rate in bit/s   : " + report.getTransferRateBit().divide(BigDecimal.valueOf(1048576)));
                handler.post(() -> {
                    if (check) {
                        textViewUploadSpeed.setText("" + decimalFormat.format(report.getTransferRateBit().divide(BigDecimal.valueOf(1048576))));
                    } else {
                        textViewDownloadSpeed.setText("" + decimalFormat.format(report.getTransferRateBit().divide(BigDecimal.valueOf(1048576))));
                    }
                });
            }

        });

    }

    @Override
    public void onClick(View v) {

        if (isNetworkAvailable()) {
            thread=new Thread(){
                @Override
                public void run() {
                    start=0;
                    check = false;
                    test();
                }

            };
            thread.start();
            btnTestAgain.setVisibility(View.GONE);
        } else {
            Toast.makeText(getActivity(), "You are not connected to Internet", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkAvailable() {

        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void netWorkName() throws UnknownHostException {
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        assert networkInfo != null;
        if (networkInfo.isConnected()) {
            WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            int ipInt = wifiInfo.getIpAddress();
             ip= InetAddress.getByAddress(
                    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(ipInt).array())
                    .getHostAddress();
            textViewNetworkName.setText(wifiInfo.getSSID());
            textViewIpAddress.setText(ip);



        }
    }
    public long networkPing(String domain){
        Runtime runtime=Runtime.getRuntime();
        try {
            long a=(System.currentTimeMillis()%100000);
            Process ipProcess=runtime.exec("/system/bin/ping -c 1 "+domain);
            ipProcess.waitFor();
            long b=(System.currentTimeMillis()%100000);
            if(b<=a){
                timeOfPing=((100000-a)+b);
            }else {
                timeOfPing=(b-a);
            }
        }catch (Exception e){

        }
        return timeOfPing;
    }


    @Override
    public void onDestroy() {
        thread.interrupt();
        super.onDestroy();
    }

}


