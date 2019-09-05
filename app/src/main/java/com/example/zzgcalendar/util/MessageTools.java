package com.example.zzgcalendar.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Looper;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * MessageTools
 * <p>
 * zhouzhiguo 2015年11月2日 下午8:42:46
 *
 * @version 1.0.0
 */
public class MessageTools {

    /**
     * 弹出框，只能选择一个
     *
     * @param context
     * @param mItems   显示数据
     * @param callback 回调函数
     * @throws
     * @since 1.0.0
     */
    public static void showOneChoiceDialog(Context context, String title, final String[] mItems,
                                           final OneChoiceImpl callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(mItems, new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                callback.oneChoiceConfig(mItems[which], which);
            }
        });

        builder.create().show();

//        if (mItems.length <= 4) {
//        } else {
//            WindowManager m = ((Activity) context).getWindowManager();
//            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//            WindowManager.LayoutParams p = ((Activity) context).getWindow().getAttributes(); // 获取对话框当前的参数值
//            p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.5
//            p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.8
//            builder.show().getWindow().setAttributes(p);
//        }
    }


    /**
     * 弹出框，只能选择一个
     *
     * @param context
     * @param lists    显示数据
     * @param callback 回调函数
     * @throws
     * @since 1.0.0
     */
    public static void showOneChoiceDialog(Context context, String title, final List<String> lists,
                                           final OneChoiceImpl callback) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        String[] items = new String[lists.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = lists.get(i);
        }
        builder.setItems(items, new OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                callback.oneChoiceConfig(lists.get(which), which);
            }
        });

//        builder.create();

//        if (lists.size() <= 4) {
        builder.show();
//        } else {
//            WindowManager m = ((Activity) context).getWindowManager();
//            Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
//            WindowManager.LayoutParams p = ((Activity) context).getWindow().getAttributes(); // 获取对话框当前的参数值
//            p.height = (int) (d.getHeight() * 0.6); // 高度设置为屏幕的0.5
//            p.width = (int) (d.getWidth() * 0.6); // 宽度设置为屏幕的0.8
//            builder.show().getWindow().setAttributes(p);
//            builder.show();
//        }

    }


    /**
     * 显示dialog,只有一个确定按钮
     */
    public static void showDialogOk(Context ctx, String message) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(message).setPositiveButton("确定",
                new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        builder.setTitle("提示");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * 显示dialog,只有一个确定按钮
     */
    public static void showDialogOk(Context ctx, String message, OnClickListener onClickListener) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(message).setPositiveButton("确定", onClickListener);
        builder.setCancelable(false);
        builder.setTitle("提示");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * 显示dialog,只有一个确定按钮
     */
    public static void showDialogOk(Context ctx, int resId) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(ctx.getResources().getString(resId))
                .setPositiveButton("确定", new OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
        builder.setTitle("提示");
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    /**
     * 显示dialog,有确定取消按钮。
     *
     * @param ctx
     * @param message     显示提示文字
     * @param defineClick 确定监听事件 void
     * @throws
     * @since 1.0.0
     */
    public static void showDialogf(final Activity ctx, String message, OnClickListener defineClick) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(message)
                .setPositiveButton("确定", defineClick).setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                        ctx.finish();
                    }
                });
        builder.setTitle("提示");
        builder.setCancelable(false);
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    /**
     * 显示dialog,有确定取消按钮。
     *
     * @param ctx
     * @param message     显示提示文字
     * @param defineClick 确定监听事件 void
     * @throws
     * @since 1.0.0
     */
    public static void showDialog(Activity ctx, String message, OnClickListener defineClick) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(message)
                .setPositiveButton("确定", defineClick).setNegativeButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
        builder.setTitle("提示");
        AlertDialog dialog = builder.create();
        dialog.show();

    }


    /**
     * 显示dialog,
     *
     * @param ctx
     * @param message
     * @throws
     * @since 1.0.0
     */
    public static void showDialogFinish(final Context ctx, String message) {

        if (ctx == null) {
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx).setMessage(message)
                .setNegativeButton("确定", new OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity) ctx).finish();
                        dialog.dismiss();
                    }
                }).setPositiveButton("取消", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                });
        builder.setTitle("提示");
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    /**
     * 弹出toast
     *
     * @param context
     * @param message void
     * @throws
     * @since 1.0.0
     */
    public static void showToast(final Context context, final String message) {

        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }


    }

    /**
     * 弹出toast
     *
     * @param context
     * @param reId    void
     * @throws
     * @since 1.0.0
     */
    public static void showToast(final Context context, final int reId) {

        if (Thread.currentThread().getName().equals("main")) {
            Toast.makeText(context, context.getResources().getString(reId), Toast.LENGTH_SHORT).show();
        } else {
            Looper.prepare();
            Toast.makeText(context, context.getResources().getString(reId), Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
