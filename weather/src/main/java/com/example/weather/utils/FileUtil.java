package com.example.weather.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Administrator on 2016/6/3.
 */
public class FileUtil {
    public static String readFile(Context context, String text) {
        StringBuffer sb = sb = new StringBuffer("");
        ;
        BufferedReader br = null;
        InputStream is = null;
        try {
            is = context.getAssets().open(text);
            br = new BufferedReader(new InputStreamReader(is));
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {

                if (br != null)
                    br.close();
                if (is != null)
                    is.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
