package com.lf.timepicker.activity;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

import com.lf.timepicker.R;

import java.io.FileNotFoundException;

/**
 * Created by LiFei on 2017/5/16.
 */

public class EditShowActivity extends AppCompatActivity {
    private static int screenWidth;
    private static int screenHeigh;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_show);
        TextView tv = (TextView)findViewById(R.id.tv_show);

        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;

        Log.e("url",getIntent().getStringExtra("info"));
        tv.setText(Html.fromHtml(getIntent().getStringExtra("info"),imageGetter,null));
    }

    private Html.ImageGetter imageGetter = new Html.ImageGetter() {
        @Override
        public Drawable getDrawable(String source) {
            Uri tempPath = Uri.parse(source);
            Drawable d = null;
            try {
                d = Drawable.createFromStream(getContentResolver().openInputStream(tempPath), null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            int width=d.getIntrinsicWidth()*3;
            int height=d.getIntrinsicHeight()*3;
            float scanleWidth = 0,scanleHeight = 0;
            if (width > height) {
                //横屏的图片
                if(width>screenWidth/2){
                    scanleWidth=(float)( ((float)screenWidth/(float)width)-0.01);
                    scanleHeight=scanleWidth;
                }else{
                    scanleWidth=(float)screenWidth/(float)2/(float)width;
                    scanleHeight=scanleWidth;
                }
            }
            if (width <= height) {//刚开始的时候是使用的int类型的来除，后来发现不精确，所以在这里全都转化成了float
                //竖屏的图片
                if (width >= screenWidth / 2) {
                    scanleWidth = (float) (((float) screenWidth / (float) width) - 0.01);
                    scanleHeight = scanleWidth;
                }
                else {
                    scanleWidth = (float) screenWidth / (float) 2 / (float) width;
                    scanleHeight = scanleWidth;
                }
            }
            ///这一行设置了显示时，图片的大小
            d.setBounds(0, 0, (int) (width*scanleWidth), (int) (height*scanleHeight));
            return d;
        }
    };
}
