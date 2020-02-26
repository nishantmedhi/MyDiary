package com.example.nmedh.mynote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.Menu;

public class SplashScreen extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3000;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        password = settings.getString("password","");
        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                //if there is no password
                if(password.equals(""))
                {
                    Intent mainIntent = new Intent(SplashScreen.this,CreatePassword.class);
                    SplashScreen.this.startActivity(mainIntent);
                    //mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    SplashScreen.this.finish();
                }
                else{

                    Intent mainIntent = new Intent(SplashScreen.this,EnterPassword.class);
                    SplashScreen.this.startActivity(mainIntent);
                    //mainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    SplashScreen.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}


