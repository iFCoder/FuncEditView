package com.lf.timepicker.activity;

import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.lf.timepicker.R;
import com.lf.timepicker.util.BitmapUtil;
import com.lf.timepicker.util.UpLoadPicSaveUtil;
import com.lf.timepicker.util.UriUtil;
import com.lf.timepicker.view.FuncEditView;

import java.io.IOException;

/**
 * Created by LiFei on 2017/5/15.
 */

public class CEditActivity extends AppCompatActivity {
    private static int screenWidth;
    private static int screenHeigh;
    private FuncEditView mFuncView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cedit);
        mFuncView = (FuncEditView) findViewById(R.id.func_view);
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String path = "";
            if (requestCode == mFuncView.CAPTURE_CODE) {  //拍照
                path = getCapturePath(data);
                Log.e("IMG", path + "!!!!!");
            } else {                            //相册
                // path = getPathFromResult((byte) requestCode, data);
                path = UriUtil.getAbsoluteFilePath(this, data.getData());
                Log.e("IMG", path + "@@@@");
            }
            mFuncView.handleResult(path, screenWidth, screenHeigh);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save:
                if (mFuncView.isEmpty()) {
                    Toast.makeText(this, "请输入文字", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this, EditShowActivity.class);
                    intent.putExtra("info", mFuncView.getContent());
                    startActivity(intent);
                    finish();
                }
                break;
        }
        return true;
    }

    protected String getCapturePath(Intent data) {
        Bitmap bitmap = null;
        if (!data.hasExtra("data")) {
            Uri uri = data.getData();
            try {
                bitmap = BitmapUtil.getBitmapFormUri(this, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bundle bundle = data.getExtras();
            Bitmap b = (Bitmap) bundle.get("data");
            bitmap = BitmapUtil.compressImage(b);
        }
        return UpLoadPicSaveUtil.saveFile(this, bitmap);
    }

    protected String getPathFromResult(byte whichWay, Intent data) {
        String path = "";
        if (whichWay == mFuncView.IMG_CODE) {
            path = data.getData().toString();
        } else {
            String[] projection = {MediaStore.Images.Media.DATA};
            CursorLoader cl = new CursorLoader(this, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    projection, null, null, null);
            Cursor cursor = cl.loadInBackground();
            int column_index_data = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToLast();
            path = cursor.getString(column_index_data);
            cursor.close();
        }
        return path;
    }
}
