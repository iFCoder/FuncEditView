# FuncEditView
SpannableString+EditText图文混排仿为知笔记编辑框

使用SpannableString可以实现文字和图片的混排，以及文字的效果（加粗、倾斜、下划线等），在网上找了好多的实现方式，基本上都是用TextView来实现的，这里我使用了EditText来模仿了为知笔记APP的编辑文字框，先来看看效果图：
![这里写图片描述](http://img.blog.csdn.net/20170517165516860?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvbGYwODE0/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)

代码实现：
1.继承LinearLayout，添加布局

```
public class FuncEditView extends LinearLayout{
public FuncEditView(Context context) {
        this(context, null);
    }

    public FuncEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FuncEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView((Activity) context);
    }

    private void initView(Activity context) {
        mContext = context;
        inflate(context, R.layout.func_editview, this);
        funcEdit = (EditText) findViewById(R.id.func_edit);
        mBold = (ImageView) findViewById(R.id.btn_bold);
        mLean = (ImageView) findViewById(R.id.btn_lean);
        mUnderLine = (ImageView) findViewById(R.id.btn_underline);
        mImage = (ImageView) findViewById(R.id.btn_image);
        mLight = (ImageView) findViewById(R.id.btn_light);
        mDelete = (ImageView) findViewById(R.id.btn_delete);
        forEditText();
        setOnClick();
    }
}
```

布局样式：

```
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:orientation="vertical">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/func_ll">
        <EditText
            android:id="@+id/func_edit"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:gravity="top"
            android:hint="请输入"/>
    </LinearLayout>



    <LinearLayout
        android:id="@+id/func_ll"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:paddingBottom="8dp"
        android:orientation="horizontal">

        <ImageView
            android:id="@+id/btn_bold"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/bold"/>

        <ImageView
            android:id="@+id/btn_lean"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/lean"/>

        <ImageView
            android:id="@+id/btn_underline"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/underline"/>

        <ImageView
            android:id="@+id/btn_image"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/photo"/>

        <ImageView
            android:id="@+id/btn_light"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/light"/>

        <ImageView
            android:id="@+id/btn_delete"
            android:layout_width="0dp"
            android:layout_weight="1"
            android:layout_height="wrap_content"
            android:src="@mipmap/delete"/>
    </LinearLayout>

</RelativeLayout>
```
2.各个按钮点击事件

```
@Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bold:
                if (isClickBold) {
                    mBold.setImageResource(R.mipmap.bold);
                    isBold = false;
                } else {  //加粗
                    mBold.setImageResource(R.mipmap.bold_);
                    isBold = true;
                }
                isClickBold = !isClickBold;
                break;
            case R.id.btn_lean:
                if (isClickLean) {
                    mLean.setImageResource(R.mipmap.lean);
                    isLean = false;
                } else {  //倾斜
                    mLean.setImageResource(R.mipmap.lean_);
                    isLean = true;
                }
                isClickLean = !isClickLean;
                break;
            case R.id.btn_underline:
                if (isClickUnderLine) {
                    mUnderLine.setImageResource(R.mipmap.underline);
                    isUnderLine = false;
                } else {  //下划线
                    mUnderLine.setImageResource(R.mipmap.underline_);
                    isUnderLine = true;
                }
                isClickUnderLine = !isClickUnderLine;
                break;
            case R.id.btn_light:
                if (isClickLight) {
                    mLight.setImageResource(R.mipmap.light);
                    isLight = false;
                } else {  //高亮
                    mLight.setImageResource(R.mipmap.light_);
                    isLight = true;
                }
                isClickLight = !isClickLight;
                break;
            case R.id.btn_delete:
                if (isClickDelete) {
                    mDelete.setImageResource(R.mipmap.delete);
                    isDelete = false;
                } else {  //删除线
                    mDelete.setImageResource(R.mipmap.delete_);
                    isDelete = true;
                }
                isClickDelete = !isClickDelete;
                break;
            case R.id.btn_image:
                repayState();
                chooseImage();
                break;
        }
    }
```
3.对EditText的监听

```
 funcEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString().substring(start, start + count);
                if (s1.startsWith("<img src")) {  //输入的是图片
                    imgPosition.add(start);
                    String img_url = s1.substring(10, s1.length() - 4);
                    imgUrl.add(img_url);
                } else {
                    if (count > 1) {
                        for (int i = start; i < start + count; i++) {
                            onTextChanged(s, i, before, 1);
                        }
                    }
                }
                startPosition = start;
                startCount = count;
                /*if (before > 0) { //在删除时把所有的字体设置为零
                    isBold = false;
                    mBold.setTextColor(getResources().getColor(R.color.tp_black));
                    isClickBold = false;
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isBold) {  //加粗
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (isLean) {  //倾斜
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StyleSpan(Typeface.ITALIC), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (isUnderLine) { //下划线
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new UnderlineSpan(), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (isLight) { //高亮
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new ForegroundColorSpan(Color.BLUE), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                if (isDelete) {  //删除线
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StrikethroughSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                content = s;
            }
        });
```
4.添加图片操作（重要）

```
 private void chooseImage() {
        CharSequence item[] = {"手机相册", "相机拍摄"};
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle("选择图片").setItems(item, new DialogInterface.OnClickListener() {
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


    public byte IMG_CODE = 127;
    public byte CAPTURE_CODE = 126;

    private void searchImg(byte whichWay) {
        if (whichWay == IMG_CODE) {
            Intent intent = new Intent();
                /* 开启Pictures画面Type设定为image */
            intent.setType("image/*");
                /* 使用Intent.ACTION_GET_CONTENT这个Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);
                /* 取得相片后返回本画面 */
            mContext.startActivityForResult(intent, whichWay);
        } else {
            Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            mContext.startActivityForResult(takephoto, CAPTURE_CODE);
        }
    }
public static Bitmap resizeBitMapImage1(String filePath, int targetWidth,
                                            int targetHeight) {
        Bitmap bitMapImage = null;
        // First, get the dimensions of the image
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        double sampleSize = 0;
        // Only scale if we need to
        // (16384 buffer for img processing)
        Boolean scaleByHeight = Math.abs(options.outHeight - targetHeight) >= Math
                .abs(options.outWidth - targetWidth);
        if (options.outHeight * options.outWidth * 2 >= 1638) {
            // Load, scaling to smallest power of 2 that'll get it <= desired
            // dimensions
            sampleSize = scaleByHeight ? options.outHeight / targetHeight
                    : options.outWidth / targetWidth;
            sampleSize = (int) Math.pow(2d,
                    Math.floor(Math.log(sampleSize) / Math.log(2d)));
        }
        // Do the actual decoding
        options.inJustDecodeBounds = false;
        options.inTempStorage = new byte[128];
        while (true) {
            try {
                options.inSampleSize = (int) sampleSize;
                bitMapImage = BitmapFactory.decodeFile(filePath, options);
                break;
            } catch (Exception ex) {
                try {
                    sampleSize = sampleSize * 2;
                } catch (Exception ex1) {
                }
            }
        }
        return bitMapImage;
    }
```
5.最后生成的文本信息

```
public String getContent() {
        String text = toHtml(content);
        StringBuffer sb = new StringBuffer(text);
        int start = 0;
        for(int i=0;i<imgPosition.size();i++){
            if(i == 0){
                start = imgPosition.get(0);
            }else{
                start = imgPosition.get(i);
            }
            int a = sb.indexOf("null",start);
            sb.replace(a,a+4,imgUrl.get(i));
        }
        String contentHtml = sb.toString();
        return contentHtml;
    }
```
6.使用该自定义控件

```
//布局
<com.lf.timepicker.view.FuncEditView
        android:id="@+id/func_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
      
 
```
效果展示

```
tv.setText(Html.fromHtml(getIntent().getStringExtra("info"),imageGetter,null));

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
```

最后仿为知笔记的编辑框完成，这里对SpannableString有了重新的认识。大家可以在此基础上根据不同的需求来修改。
