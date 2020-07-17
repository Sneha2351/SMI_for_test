package com.example.smifortest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

public class Profile extends AppCompatActivity {
    ImageView imageView;
    TextView textName, textId;
    FirebaseAuth mAuth;
    FirebaseUser user;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        mAuth = FirebaseAuth.getInstance();
        String username = getIntent().getStringExtra("username");
        imageView = findViewById(R.drawable.twitter);
        textName = findViewById(R.id.textViewName);
        textName.setText(username);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(Profile.this, MainActivity.class));
        }
    }
}


