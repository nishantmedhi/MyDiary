package com.example.nmedh.mynote;

import android.content.Intent;
import android.media.tv.TvContract;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class Login extends AppCompatActivity implements View.OnClickListener,GoogleApiClient.OnConnectionFailedListener{

    private TextView Name,Email;
    private ImageView imageView;
    private Button Logout;
    private SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private static final int REQ_CODE=10;
    private String name,email,imgurl;
    private GoogleSignInAccount account;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Name = (TextView) findViewById(R.id.nameperson);
        Email = (TextView) findViewById(R.id.emailperson);
        imageView = (ImageView) findViewById(R.id.imageperson);
        Logout = (Button) findViewById(R.id.logoutperson);
        signInButton = (SignInButton) findViewById(R.id.loginperson);



        signInButton.setOnClickListener(this);
        Logout.setOnClickListener(this);

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();




    }



    @Override
    public void onClick(View view) {
        switch(view.getId()){

            case R.id.loginperson:
                                    signin();
                                    break;
            case R.id.logoutperson:
                                    signout();
                                    break;

        }

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void signin(){

        Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        Log.v("step2","signin");
        startActivityForResult(intent,REQ_CODE);


    }


    private void signout(){

        Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(new ResultCallback<Status>() {
            @Override
            public void onResult(@NonNull Status status) {
                updateUI(false);
            }
        });

    }

    private void handleresult(GoogleSignInResult result){

        if(result.isSuccess())
        {
            Log.v("Success SignIn","Hurrayy");
            account = result.getSignInAccount();
            name = account.getDisplayName();
            email = account.getEmail();
            imgurl = account.getPhotoUrl().toString();
            Name.setText(name);
            Email.setText(email);
            Glide.with(this).load(imgurl).into(imageView);
            updateUI(true);
        }
        else
        {
            Log.v("Failed","Signin");
            updateUI(false);
        }

    }
    private void updateUI(boolean bool){
        if(bool)
        {
            Name.setVisibility(View.VISIBLE);
            Email.setVisibility(View.VISIBLE);
            imageView.setVisibility(View.VISIBLE);
            Logout.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.INVISIBLE);
        }
        else
        {
            Name.setVisibility(View.INVISIBLE);
            Email.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
            Logout.setVisibility(View.INVISIBLE);
            signInButton.setVisibility(View.VISIBLE);
        }

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==REQ_CODE)
        {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.v("step 1 sign","sign");
            handleresult(result);
        }
    }
}
