package com.example.phonerepaire.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.phonerepaire.R;

public class TermAndConditionScreen extends AppCompatActivity implements View.OnClickListener {
    Button buttonAccept;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    boolean isAccept = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_and_condition_screen);
        buttonAccept = findViewById(R.id.acceptButon);
        sharedPreferences = getSharedPreferences("Accept", MODE_PRIVATE);
        buttonAccept.setOnClickListener(this);
        if (isAccept()) {
            Intent intent = new Intent(TermAndConditionScreen.this, SplashScreen.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void onClick(View v) {
        editor = sharedPreferences.edit();
        editor.putBoolean("accepted", true);
        editor.apply();
        Intent intent = new Intent(TermAndConditionScreen.this, SplashScreen.class);
        startActivity(intent);
        finish();
    }

    public boolean isAccept() {
        isAccept = sharedPreferences.getBoolean("accepted", false);
        return isAccept;
    }
}