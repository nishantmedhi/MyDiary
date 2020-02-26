package com.example.nmedh.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



public class EnterPassword extends AppCompatActivity {

    EditText password;
    Button enter;
    String mypassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_password);

        SharedPreferences settings = getSharedPreferences("PREFS",0);
        mypassword = settings.getString("password","");

        password = (EditText) findViewById(R.id.enterpass);
        enter = (Button) findViewById(R.id.enter);

        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = password.getText().toString();
                if(text.equals(mypassword))
                {
                    //enter the app
                    Intent mainIntent = new Intent(EnterPassword.this,MainActivity.class);
                    EnterPassword.this.startActivity(mainIntent);
                    EnterPassword.this.finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Wrong password entered!!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
