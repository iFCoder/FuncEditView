package com.lf.timepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lf.timepicker.activity.CEditActivity;
import com.lf.timepicker.activity.TestActivity;
import com.lf.timepicker.activity.TimePickerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTime(View view){
        startActivity(new Intent(MainActivity.this,TimePickerActivity.class));
    }

    public void onEdit(View view){
        startActivity(new Intent(MainActivity.this,CEditActivity.class));
    }

    public void onEditTest(View view){
        startActivity(new Intent(MainActivity.this,TestActivity.class));
    }
}
