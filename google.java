package com.example.smifortest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.UrlConnectionDownloader;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import retrofit2.http.Url;

public class google extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    ImageView imageView;
    Button logout;
    GoogleApiClient googleApiClient;
    GoogleSignInOptions gso;
    TextView textName, textEmail;
    FirebaseAuth mAuth;
    FirebaseUser user;
    GoogleSignInAccount account;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google);
        mAuth = FirebaseAuth.getInstance();
        imageView = findViewById(R.drawable.google);
        textName = findViewById(R.id.textViewName);
        textEmail = findViewById(R.id.textViewEmail);
        logout = findViewById(R.id.logout);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso).build();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        if (status.isSuccess())
                            gotoMainActivity();
                        else
                            Toast.makeText(google.this, "Log Out Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void gotoMainActivity() {
        startActivity(new Intent(google.this, MainActivity.class));
        finish();
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
                account = result.getSignInAccount();
            textName.setText(account.getDisplayName());
            textEmail.setText(account.getEmail());
        }
    }

   @Override
   protected void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> imp = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (imp.isDone()) {
            GoogleSignInResult result = imp.get();
            handleSignInResult(result);
        } else {
            imp.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult result) {
                    handleSignInResult(result);
                }
            });
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}