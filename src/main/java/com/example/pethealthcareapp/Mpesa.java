package com.example.pethealthcareapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Base64;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import api.MpesaApiService;
import model.OAuthTokenResponse;
import model.StkPushRequest;
import model.StkPushResponse;
import network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Mpesa extends AppCompatActivity {

    public EditText phoneNumber;
    public Button payment;
    public TextView consultationFee,textView1;
    public ImageView safaricom;

    private static final String BASE_URL = "https://sandbox.safaricom.co.ke/";
    private static final String CONSUMER_KEY = "r03OG4rRsYhAS9oXHuieJHayb7Hckpt7lYfV6d1E2edh6GjG";
    private static final String CONSUMER_SECRET = "nCJUbiVwFttQgcWjaA0gDyl7kvKJ4Up4MjmVDgVZJAAVGnYt1Gn6C39v4RI84LkW";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mpesa);


        consultationFee = findViewById(R.id.consultationFee);
        textView1 = findViewById(R.id.textView1);
        safaricom = findViewById(R.id.safaricom);
        phoneNumber = findViewById(R.id.phoneNumber);
        payment = findViewById(R.id.payment);
    }

    private void getOAuthToken() {
        String auth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            auth = "Basic " + Base64.encodeToString((CONSUMER_KEY + ":" + CONSUMER_SECRET).getBytes(), Base64.NO_WRAP);
        }

        MpesaApiService apiService = RetrofitClient.getClient(BASE_URL).create(MpesaApiService.class);
        Call<OAuthTokenResponse> call = apiService.getOAuthToken(auth);
        call.enqueue(new Callback<OAuthTokenResponse>() {
            @Override
            public void onResponse(Call<OAuthTokenResponse> call, Response<OAuthTokenResponse> response) {
                if (response.isSuccessful()) {
                    String token = response.body().getAccessToken();
                    initiateStkPush(token);
                }
            }

            @Override
            public void onFailure(Call<OAuthTokenResponse> call, Throwable t) {
                // Handle error
            }
        });
    }

    private void initiateStkPush(String token) {
        String shortCode = "9660681";
        String passKey = "bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919";
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(new Date());
        String password = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.FROYO) {
            password = Base64.encodeToString((shortCode + passKey + timestamp).getBytes(), Base64.NO_WRAP);
        }

        StkPushRequest request = new StkPushRequest(
                shortCode,
                password,
                timestamp,
                "CustomerPayBillOnline",
                "500", // Amount
                "2547XXXXXXXX", // PartyA (Customer's phone number)
                shortCode, // PartyB
                "2547XXXXXXXX", // Phone number
                "https://example.com/callback", // Callback URL
                "PetCare Health App",
                "Consultation Fee Payment"
        );

        MpesaApiService apiService = RetrofitClient.getClient(BASE_URL).create(MpesaApiService.class);
        Call<StkPushResponse> call = apiService.initiateStkPush("Bearer " + token, request);
        call.enqueue(new Callback<StkPushResponse>() {
            @Override
            public void onResponse(Call<StkPushResponse> call, Response<StkPushResponse> response) {
                if (response.isSuccessful()) {
                    StkPushResponse stkPushResponse = response.body();
                    // Handle response
                }
            }

            @Override
            public void onFailure(Call<StkPushResponse> call, Throwable t) {
                // Handle error
            }
        });
    }
}