package com.lf.timepicker.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.lf.timepicker.R;
import com.lf.timepicker.util.BitmapUtil;
import com.lf.timepicker.util.DialogUtil;
import com.lf.timepicker.util.UriUtil;
import com.lf.timepicker.view.FuncEditView;

import java.io.IOException;

import jp.wasabeef.richeditor.RichEditor;

import static com.lf.timepicker.util.UpLoadPicSaveUtil.saveFile;

/**
 * Created by LiFei on 2017/5/23.
 */

public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private static int screenWidth;
    private static int screenHeigh;
    private Integer color[] = {R.color.tp_black, R.color.tp_red, R.color.tp_orange,
            R.color.tp_green, R.color.tp_blues, R.color.tp_purple};
    private Integer color_[] = {0xFF000000, 0xFFed2e2e, 0xFFed9a2e,
            0xFF42d153, 0xFF2da4e8, 0xFFd02de8};
    private RichEditor mEditor;
    private ImageView tBold, tLean, tUnderline, tImage, tLight, tDelete, tListUl, tListOl,tSize;
    private boolean isClickBold = false;
    private boolean isClickLean = false;
    private boolean isClickUnderLine = false;
    private boolean isClickDelete = false;
    private boolean isClickListUl = false;
    private boolean isClickListOl = false;
    private boolean isClickSize = false;

    public byte IMG_CODE = 127;
    public byte CAPTURE_CODE = 126;
    private String content = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeigh = dm.heightPixels;
        setContentView(R.layout.activity_test);
        mEditor = (RichEditor) findViewById(R.id.editor);


        mEditor.setEditorHeight(200);
        mEditor.setEditorFontSize(18);
        mEditor.setEditorFontColor(Color.BLACK);
        mEditor.setPadding(10, 10, 10, 10);
        mEditor.setPlaceholder("请输入...");
        initID();
        initClick();
    }

    private void initID() {
        tBold = (ImageView) findViewById(R.id.test_bold);
        tLean = (ImageView) findViewById(R.id.test_lean);
        tUnderline = (ImageView) findViewById(R.id.test_underline);
        tImage = (ImageView) findViewById(R.id.test_image);
        tLight = (ImageView) findViewById(R.id.test_light);
        tDelete = (ImageView) findViewById(R.id.test_delete);
        tListUl = (ImageView) findViewById(R.id.test_list_ul);
        tListOl = (ImageView) findViewById(R.id.test_list_ol);
        tSize = (ImageView) findViewById(R.id.test_font_size);
    }

    private void initClick() {
        tBold.setOnClickListener(this);
        tLean.setOnClickListener(this);
        tUnderline.setOnClickListener(this);
        tImage.setOnClickListener(this);
        tLight.setOnClickListener(this);
        tDelete.setOnClickListener(this);
        tListUl.setOnClickListener(this);
        tListOl.setOnClickListener(this);
        tSize.setOnClickListener(this);
        mEditor.setOnTextChangeListener(new RichEditor.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if (TextUtils.isEmpty(text)) {
                    restoreState();
                }
                content = text;
            }
        });
    }
    
    private void restoreState() {
        if(isClickBold){
            isClickBold = false;
            tBold.setImageResource(R.mipmap.bold);
        }
        if(isClickLean){
            isClickLean = false;
            tLean.setImageResource(R.mipmap.lean);
        }
        if(isClickUnderLine){
            isClickUnderLine = false;
            tUnderline.setImageResource(R.mipmap.underline);
        }
        if(isClickDelete){
            isClickDelete = false;
            tDelete.setImageResource(R.mipmap.delete);
        }
        if(isClickListUl){
            isClickListUl = false;
            tListUl.setImageResource(R.mipmap.list_ul);
        }
        if(isClickListOl){
            isClickListOl = false;
            tListOl.setImageResource(R.mipmap.list_ol);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.test_bold:
                if (isClickBold) {
                    tBold.setImageResource(R.mipmap.bold);
                } else {  //加粗
                    tBold.setImageResource(R.mipmap.bold_);
                }
                isClickBold = !isClickBold;
                mEditor.setBold();
                break;
            case R.id.test_lean:
                if (isClickLean) {
                    tLean.setImageResource(R.mipmap.lean);
                } else {  //倾斜
                    tLean.setImageResource(R.mipmap.lean_);
                }
                isClickLean = !isClickLean;
                mEditor.setItalic();
                break;
            case R.id.test_underline:
                if (isClickUnderLine) {
                    tUnderline.setImageResource(R.mipmap.underline);
                } else {  //下划线
                    tUnderline.setImageResource(R.mipmap.underline_);
                }
                isClickUnderLine = !isClickUnderLine;
                mEditor.setUnderline();
                break;
            case R.id.test_image:
                if(null == content){
                    Toast.makeText(this, "请先输入文字", Toast.LENGTH_SHORT).show();
                    return;
                }
                choosePhoto();
                break;
            case R.id.test_light:
                DialogUtil.setGravity(Gravity.CENTER);
                View view = DialogUtil.show(this, R.layout.edit_color);
                GridView mGridView = (GridView) view.findViewById(R.id.gv_color);
                mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
                mGridView.setAdapter(new TColorAdapter());
                mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        mEditor.setTextColor(color_[position]);
                        DialogUtil.dismisss();
                    }
                });
                break;
            case R.id.test_font_size:
                CharSequence item[] = {"ABC-12", "ABC-14","ABC-16","ABC-18（默认）","ABC-20","ABC-24"};
                AlertDialog dialog = new AlertDialog.Builder(this).setTitle("选择字体").setItems(item, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                mEditor.setFontSize(1);
                                break;
                            case 1:
                                mEditor.setFontSize(2);
                                break;
                            case 2:
                                mEditor.setFontSize(3);
                                break;
                            case 3:
                                mEditor.setFontSize(4);
                                break;
                            case 4:
                                mEditor.setFontSize(5);
                                break;
                            case 5:
                                mEditor.setFontSize(6);
                                break;
                        }
                    }
                }).create();
                dialog.show();
                break;
            case R.id.test_delete:
                if (isClickDelete) {
                    tDelete.setImageResource(R.mipmap.delete);
                } else {  //删除线
                    tDelete.setImageResource(R.mipmap.delete_);
                }
                isClickDelete = !isClickDelete;
                mEditor.setStrikeThrough();
                break;
            case R.id.test_list_ul:
                if (isClickListUl) {
                    tListUl.setImageResource(R.mipmap.list_ul);
                } else {
                    tListUl.setImageResource(R.mipmap.list_ul_);
                }
                isClickListUl = !isClickListUl;
                mEditor.setBullets();
                break;
            case R.id.test_list_ol:
                if (isClickListOl) {
                    tListOl.setImageResource(R.mipmap.list_ol);
                } else {
                    tListOl.setImageResource(R.mipmap.list_ol_);
                }
                isClickListOl = !isClickListOl;
                mEditor.setNumbers();
                break;
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
                if (null == content) {
                    Toast.makeText(this, "请输入文字", Toast.LENGTH_SHORT).show();
                } else {
                    /*Intent intent = new Intent(this, EditShowActivity.class);
                    intent.putExtra("info", content);
                    startActivity(intent);
                    finish();*/
                    Log.e("text", content);
                }
                break;
            case R.id.repeal:
                if (null == content) {
                    Toast.makeText(this, "请输入文字", Toast.LENGTH_SHORT).show();
                } else {
                    mEditor.redo();
                }
                break;
            case R.id.close:
                if (null == content) {
                    Toast.makeText(this, "请输入文字", Toast.LENGTH_SHORT).show();
                } else {
                    mEditor.undo();
                }
                break;
        }
        return true;
    }

    private void choosePhoto() {
        CharSequence item[] = {"手机相册", "相机拍摄"};
        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("选择图片").setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:  //手机相册
                        searchImg(IMG_CODE);
                        break;
                    case 1:  //相机拍摄
                        searchImg(CAPTURE_CODE);
                        break;
                }
            }
        }).create();
        dialog.show();
    }


    private class TColorAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return color.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(TestActivity.this).inflate(R.layout.gv_item_color, parent, false);
            ImageView gvItem = (ImageView) view.findViewById(R.id.iv_item_color);
            gvItem.setImageResource(color[position]);
            return view;
        }
    }

    private void searchImg(byte whichWay) {
        if (whichWay == IMG_CODE) {
            Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
            intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
            startActivityForResult(intent, whichWay);
        } else {
            Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takephoto, CAPTURE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String path = "";
            if (requestCode == CAPTURE_CODE) {  //拍照
                path = getCapturePath(data);
                Log.e("IMG", path + "!!!!!");
            } else {                            //相册
                Bitmap b = FuncEditView.resizeBitMapImage1(UriUtil.getAbsoluteFilePath(this, data.getData()),screenWidth/10, screenHeigh / 15);
                path = saveFile(this, b);
                Log.e("IMG", path + "@@@@");
            }
            mEditor.insertImage(path, "dachshund");
        }
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
        return saveFile(this, bitmap);
    }


}
