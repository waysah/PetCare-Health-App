package com.example.pethealthcareapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class Services extends AppCompatActivity {
    public Button consult,records,back;
    public TextView petCare;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpesa);

        consult = findViewById(R.id.consult);
        records = findViewById(R.id.records);
        back = findViewById(R.id.back);
        petCare = findViewById(R.id.petCare);
    }
}
