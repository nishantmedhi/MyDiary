package com.example.nmedh.mynote;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Reminder extends AppCompatActivity {

    TimePicker timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

            timePicker = (TimePicker) findViewById(R.id.timePicker);
            findViewById(R.id.buttonAlarm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    if(Build.VERSION.SDK_INT >= 23) {
                        calendar.set(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                timePicker.getHour(),
                                timePicker.getMinute(),
                                0
                        );
                    }
                    else
                    {
                        calendar.set(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                timePicker.getCurrentHour(),
                                timePicker.getCurrentMinute(),
                                0
                        );

                    }

                    setAlarm(calendar.getTimeInMillis());

                }
            });

    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);
        alarmManager.setRepeating(AlarmManager.RTC, timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent);
        Toast.makeText(getApplicationContext(),"The Alarm is Set",Toast.LENGTH_SHORT).show();
    }
}
