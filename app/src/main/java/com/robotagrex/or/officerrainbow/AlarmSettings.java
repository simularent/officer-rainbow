package com.robotagrex.or.officerrainbow;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class AlarmSettings extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    ToggleButton toggleButton5, toggleButton6, toggleButton7;
    private JobScheduler mJobScheduler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarmsettings);
        toggleButton5 = (ToggleButton) findViewById(R.id.toggleButton5);
        toggleButton6 = (ToggleButton) findViewById(R.id.toggleButton6);
        toggleButton7 = (ToggleButton) findViewById(R.id.toggleButton7);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        mJobScheduler = (JobScheduler) getSystemService( Context.JOB_SCHEDULER_SERVICE );

        boolean toggle1state = sharedpreferences.getBoolean("droptest_email_state", false);
        toggleButton5.setChecked(toggle1state);

        boolean toggle2state = sharedpreferences.getBoolean("droptest_sms_state", false);
        toggleButton6.setChecked(toggle2state);

        boolean toggle3state = sharedpreferences.getBoolean("droptest_alarm_state", false);
        toggleButton7.setChecked(toggle3state);

        //String txtdate = sharedpreferences.getString("probation_meeting_date", "No date chosen yet");

        Toolbar myToolbar = (Toolbar)findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        String app_title = sharedpreferences.getString("app_title", "Officer Rainbow");
        if(getSupportActionBar() != null){
            System.out.println(app_title);
            getSupportActionBar().setTitle(app_title);
        }

        Button buttonnext = (Button)findViewById(R.id.buttonnext);
        assert buttonnext != null;

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent qoneintent = new Intent(AlarmSettings.this, UI.class);
                startActivity(qoneintent);
            }
        });

        toggleButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (toggleButton5.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_email_state", true);
                    editor.apply();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_email_state", false);
                    editor.apply();
                }
            }
        });

        toggleButton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (toggleButton6.isChecked())
                {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_sms_state", true);
                    editor.apply();
                }
                else
                {
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_sms_state", false);
                    editor.apply();
                }
            }
        });

        toggleButton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (toggleButton7.isChecked())
                {
                    JobInfo.Builder builder = new JobInfo.Builder( 1,
                            new ComponentName( getPackageName(), JobSchedulerService.class.getName() ) );

                    builder.setPeriodic(1000 * 20);
                    builder.setPersisted(true);
                    Toast toast1= Toast.makeText(getApplicationContext(),
                            "JobService Set", Toast.LENGTH_SHORT);
                    toast1.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast1.show();


                    if( mJobScheduler.schedule( builder.build() ) <= 0 ) {
                        Toast toast2= Toast.makeText(getApplicationContext(),
                                "JobService task is broken", Toast.LENGTH_SHORT);
                        toast2.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                        toast2.show();
                    }

                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_alarm_state", true);
                    editor.apply();
                }
                else
                {
                    mJobScheduler.cancelAll();
                    Toast toast3= Toast.makeText(getApplicationContext(),
                            "JobService Cancelled", Toast.LENGTH_SHORT);
                    toast3.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
                    toast3.show();
                    SharedPreferences.Editor editor = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE).edit();
                    editor.putBoolean("droptest_alarm_state", false);
                    editor.apply();
                }
            }
        });
    }
}
