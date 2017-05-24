package com.lf.timepicker.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lf.timepicker.R;


/**
 * Created by admin on 2016/8/18.
 */
public class DialogUtil extends Dialog {

    public static DialogUtil dialog;

    /**
     * 窗口位置设置
     */
    private static int gravity = Gravity.CENTER;
    /**
     * 布局视图
     */
    private static View view;

    /**
     * 点击返回是否关闭dialog
     */
    public static boolean isDismiss = true;

    /**
     * 是否全屏
     */
    public static boolean isFullScreen = false;

    /**
     *
     */
    public static boolean noBackground = false;


    /**
     * 无阴影
     */
    public static void setNoBackGround() {
        noBackground = true;
    }

    /**
     * 设置对话框是否全屏
     */
    public static void setFullScreen() {
        isFullScreen = true;
    }


    /**
     * 设置Dialog的位置
     *
     * @param youGravity Gravity.BOTTOM; Gravity.CENTER;Gravity.TOP;
     */
    public static void setGravity(int youGravity) {
        gravity = youGravity;
    }

    public static void setBackNoDismiss() {
        isDismiss = noBackground;
    }


    /**
     * 通用
     *
     * @param context
     * @param youLayout
     * @return
     */
    public static View show(Context context, @LayoutRes int youLayout) {
        if (dialog != null) dialog.dismiss();
        Window window;
        if (isFullScreen) {
            if (noBackground) {
                dialog = new DialogUtil(context, R.style.dialog);
            } else {
                dialog = new DialogUtil(context, R.style.list_dialog);
            }
            window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.FILL_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        } else {
            dialog = new DialogUtil(context, R.style.list_dialog);
            window = dialog.getWindow();
            window.getDecorView().setPadding(100,0,100,0); //根据需求进行设置，可无
        }

        view = dialog.getLayoutInflater().inflate(youLayout, null);

        window.setGravity(gravity);
        window.setWindowAnimations(R.style.dialogStyle);
        if (noBackground) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }


        dialog.show();
        return view;
    }


    /**
     * 通用
     *
     * @param context
     * @return
     */
    public static View show(Context context, View view1) {
        view = view1;
        if (dialog != null) dialog.dismiss();
        Window window;
        if (isFullScreen) {
            if (noBackground) {
                dialog = new DialogUtil(context, R.style.dialog);
            } else {
                dialog = new DialogUtil(context, R.style.list_dialog);
            }
            window = dialog.getWindow();
            window.getDecorView().setPadding(0, 0, 0, 0);
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = WindowManager.LayoutParams.FILL_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        } else {
            dialog = new DialogUtil(context);
            window = dialog.getWindow();
        }

        window.setGravity(gravity);
        window.setWindowAnimations(R.style.dialogStyle);

        dialog.show();
        return view;
    }


    /**
     * 关闭对话框
     */
    public static void dismisss() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                clear();
            }
        }
    }


    public DialogUtil(Context context) {
        super(context);
    }

    public DialogUtil(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected DialogUtil(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isDismiss) {
            clear();
            return super.onKeyDown(keyCode, event);
        } else {
            return true;
        }
    }

    static void clear() {
        gravity = Gravity.CENTER;
        isDismiss = true;
        isFullScreen = false;
        noBackground = false;
    }


    /*    *//**
     *  打电话
     * @param context 上下文
     * phoneNumber  电话号码
     *//*
    public static void showCallPhoneDialog(final Context context, final String phoneNumber ,String title){
        if(dialog!=null)dialog.dismiss();
        Window window;
        dialog  = new DialogUtil(context,R.style.list_dialog);
        window  = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.FILL_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(lp);

        view = dialog.getLayoutInflater().inflate(R.layout.item_popupwindow,null);

        window.setGravity(Gravity.BOTTOM);
        window.setWindowAnimations(R.style.dialogStyle);

        dialog.show();
        TextView phone = (TextView) view.findViewById(R.id.item_popupwindows_Photo);
        TextView call = (TextView) view.findViewById(R.id.item_popupwindows_camera);
        TextView cancel = (TextView) view.findViewById(R.id.item_popupwindows_cancel);
        call.setTextColor(Color.BLACK);
        call.setText(title);
        phone.setText(phoneNumber);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismisss();
            }
        });

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                设置要传递的数据，Uri类型，电话号码要加前缀“tel:”
                intent.setData(Uri.parse("tel:" + phoneNumber));
                intent.setAction(Intent.ACTION_CALL);
                context.startActivity(intent);
                dismisss();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setItems(new CharSequence[]{"复制"}, new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        switch (which){
                            case 0:
                                // 从API11开始android推荐使用android.content.ClipboardManager
                                // 为了兼容低版本我们这里使用旧版的android.text.ClipboardManager，虽然提示deprecated，但不影响使用。
                                ClipboardManager cm = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                                // 将文本内容放到系统剪贴板里。
                                cm.setText(phoneNumber);
                                ToastUtil.show(context, "复制成功");
                                break;
                        }
                    }
                }).create().show();
                dismisss();
            }
        });


    }*/


}
