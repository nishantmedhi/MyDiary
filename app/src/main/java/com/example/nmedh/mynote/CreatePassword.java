package com.example.nmedh.mynote;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class CreatePassword extends AppCompatActivity {

    EditText pass1,pass2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_password);

        pass1 = (EditText) findViewById(R.id.password1);
        pass2 = (EditText) findViewById(R.id.password2);
        button = (Button) findViewById(R.id.confirm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t1 = pass1.getText().toString();
                String t2 = pass2.getText().toString();
                if(t1.equals("")||t2.equals(""))
                {
                    //there is no password
                    Toast.makeText(getApplicationContext(),"No password entered",Toast.LENGTH_SHORT).show();
                }
                else if(t1.equals(t2))
                {
                    //Passwords match. Save the password
                    SharedPreferences settings = getSharedPreferences("PREFS",0);
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("password",t1);
                    editor.apply();

                    //Enter the app
                    Intent mainIntent = new Intent(CreatePassword.this,MainActivity.class);
                    CreatePassword.this.startActivity(mainIntent);
                    CreatePassword.this.finish();

                }
                else
                {
                    //Passwords do not match
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
