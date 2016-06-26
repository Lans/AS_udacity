package com.example.weather.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.View;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2016/6/11.
 */
public class ScreenShot {
    private static final String TAG = "ScreenShot.class";

    private static final int SAVE_AUTHORITY = Context.MODE_PRIVATE;

    // 获取指定Activity的截屏，保存到png文件
    private static Bitmap takeScreenShot(Activity activity) {

        //截取需要的view
        View view = activity.getWindow().getDecorView();
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        Bitmap b1 = view.getDrawingCache();

        //获取状态栏的高度
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int statusBarHeight = frame.top;
        int width = activity.getWindowManager().getDefaultDisplay().getWidth();
        int height = activity.getWindowManager().getDefaultDisplay().getHeight();

        //去掉标题栏
        Bitmap b = Bitmap.createBitmap(b1, 0, statusBarHeight, width, height - statusBarHeight);
        view.destroyDrawingCache();
        return b;
    }

    //保存到sdcard
    private static void savePic(Activity activity, Bitmap b, String fileName) {
        FileOutputStream fos = null;

        try {
            fos = activity.openFileOutput(fileName, SAVE_AUTHORITY);
            if (fos != null) {
                b.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void shareAct(Activity activity, String fileName) throws IOException {
        Uri uri = null;

        try {
            FileInputStream inputStream = activity.openFileInput(fileName);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
           uri = Uri.parse(MediaStore.Images.Media.insertImage(activity.getContentResolver(), bitmap,"title","描述"));
           // uri = Uri.fromFile(activity.getFileStreamPath(fileName));
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/jpeg");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "好友推荐");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "分享到");
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(Intent.createChooser(shareIntent,"分享到"));
    }

    public static void share(Activity activity) {
        String saveFileNmae = "Share.jpg";
        savePic(activity, ScreenShot.takeScreenShot(activity), saveFileNmae);
        try {
            shareAct(activity, saveFileNmae);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}