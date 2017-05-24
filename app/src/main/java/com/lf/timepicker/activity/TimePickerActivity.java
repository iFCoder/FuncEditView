package com.lf.timepicker.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.lf.timepicker.R;
import com.lf.timepicker.view.TimePicker;
import com.lf.timepicker.view.TimePickerDialog;

import java.util.Calendar;

/**
 * Created by LiFei on 2017/5/15.
 */

public class TimePickerActivity extends AppCompatActivity{

    private TextView time;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_timepicker);
        time = (TextView) findViewById(R.id.time);
    }

    public void onTimeClick(View view){
        Calendar now = Calendar.getInstance();
        TimePickerDialog mTimePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute, int seconds) {
                time.setText(getString(R.string.time) + String.format("%02d", hourOfDay)+
                        ":" + String.format("%02d", minute) +
                        ":" + String.format("%02d", seconds));
            }
        }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), now.get(Calendar.SECOND), true);
        mTimePicker.show();
    }
}
