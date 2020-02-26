package com.example.nmedh.mynote;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;


public class Settings extends AppCompatActivity {

    private ListView listView;


    String [] s = new String[]{"Preferences","Data restore and Backup","Reminders"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        listView = (ListView) findViewById(R.id.settingslist);
        ArrayAdapter adapter = new ArrayAdapter(Settings.this,android.R.layout.simple_list_item_1,s);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(i==0)
                {

                    Toast.makeText(getApplicationContext(),"Preferences",Toast.LENGTH_SHORT).show();
                }
                if(i==1)
                {
                    Intent intent = new Intent(Settings.this,Login.class);
                    startActivity(intent);
                }
                if(i==2)
                {
                    Intent reminder = new Intent(Settings.this,Reminder.class);
                    startActivity(reminder);
                    //Toast.makeText(getApplicationContext(),"Reminders",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }





}

